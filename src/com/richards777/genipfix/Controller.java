package com.richards777.genipfix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class Controller implements Initializable {

    @FXML
    private CheckBox checkBox1;

    @FXML
    private ComboBox<String> elementName1;

    @FXML
    private TextField elementSize1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private ComboBox<String> elementName2;

    @FXML
    private TextField elementSize2;

    @FXML
    private CheckBox checkBox3;

    @FXML
    private ComboBox<String> elementName3;

    @FXML
    private TextField elementSize3;

    @FXML
    private CheckBox checkBox4;

    @FXML
    private ComboBox<String> elementName4;

    @FXML
    private TextField elementSize4;

    @FXML
    private TextField filename;

    @FXML
    private Button genButton;

    @FXML
    private void generateFile(ActionEvent event) {
        IPFIXTemplateSet templateSet = new IPFIXTemplateSet();
        List<String> elementNames = new ArrayList<>(4);
        elementNames.add(elementName1.getValue());
        elementNames.add(elementName2.getValue());
        elementNames.add(elementName3.getValue());
        elementNames.add(elementName4.getValue());
        templateSet.addTemplateRecord((short) 777, elementNames);

        IPFIXMessage templateMessage = new IPFIXMessage();
        templateMessage.addSet(templateSet);

        UDPDatagram templateUDPDatagram = new UDPDatagram();
        templateUDPDatagram.setBuffer(templateMessage.getBuffer());

        IPv4Datagram templateIPv4Datagram = new IPv4Datagram();
        templateIPv4Datagram.setBuffer(templateUDPDatagram.getBuffer());

        EthernetFrame templateEthernetFrame = new EthernetFrame();
        templateEthernetFrame.setPayload(templateIPv4Datagram);

        PacketHeader templatePacketHeader = new PacketHeader(1451499300, 0, templateEthernetFrame.getLengthInBytes(), templateEthernetFrame.getLengthInBytes());


        IPFIXDataSet dataSet = new IPFIXDataSet(templateSet.getTemplateRecord());

        IPFIXMessage dataMessage = new IPFIXMessage();
        dataMessage.addSet(dataSet);

        UDPDatagram dataUDPDatagram = new UDPDatagram();
        dataUDPDatagram.setBuffer(dataMessage.getBuffer());

        IPv4Datagram dataIPv4Datagram = new IPv4Datagram();
        dataIPv4Datagram.setBuffer(dataUDPDatagram.getBuffer());

        EthernetFrame dataEthernetFrame = new EthernetFrame();
        dataEthernetFrame.setPayload(dataIPv4Datagram);

        PacketHeader dataPacketHeader = new PacketHeader(1451499301, 0, dataEthernetFrame.getLengthInBytes(), dataEthernetFrame.getLengthInBytes());




        Path file = Paths.get(filename.getText());
        try (OutputStream out = Files.newOutputStream(file)) {
            out.write(GlobalHeader.getBuffer());
            out.write(templatePacketHeader.getBuffer());
            out.write(templateEthernetFrame.getBuffer());
            out.write(dataPacketHeader.getBuffer());
            out.write(dataEthernetFrame.getBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(IPFIXInformationElements.get().getKeys());

        elementName1.getItems().setAll(treeSet);
        elementName2.getItems().setAll(treeSet);
        elementName3.getItems().setAll(treeSet);
        elementName4.getItems().setAll(treeSet);
    }
}
