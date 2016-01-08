package com.richards777.genipfix;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private ComboBox<String> elementName1;

    @FXML
    private ComboBox<String> elementName2;

    @FXML
    private ComboBox<String> elementName3;

    @FXML
    private ComboBox<String> elementName4;

    @FXML
    private TextField filename;

    @FXML
    private VBox vBox;

    @FXML
    private void addField(ActionEvent event) {
        ComboBox<String> stringComboBox = new ComboBox<>();
        stringComboBox.setPromptText("element name");
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(IPFIXInformationElements.get().getKeys());

        stringComboBox.getItems().setAll(treeSet);

        vBox.getChildren().add(stringComboBox);
    }

    @FXML
    private void generateFile(ActionEvent event) {
        IPFIXTemplateSet templateSet = createIpfixTemplateSet();

        EthernetFrame templateEthernetFrame = createEthernetFrame(templateSet, 1);

        int timeInSeconds = 1451499300;
        PacketHeader templatePacketHeader = createPacketHeader(timeInSeconds, templateEthernetFrame.getLengthInBytes());

        Path file = Paths.get(filename.getText());
        try (OutputStream out = Files.newOutputStream(file)) {
            out.write(GlobalHeader.getBuffer());
            out.write(templatePacketHeader.getBuffer());
            out.write(templateEthernetFrame.getBuffer());

            for (int i = 0; i < 10; i++) {
                EthernetFrame ethernetDataFrame = createEthernetDataFrame(templateSet, i+1);
                timeInSeconds++;
                PacketHeader dataPacketHeader = createPacketHeader(timeInSeconds, ethernetDataFrame.getLengthInBytes());
                out.write(dataPacketHeader.getBuffer());
                out.write(ethernetDataFrame.getBuffer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IPFIXTemplateSet createIpfixTemplateSet() {
        IPFIXTemplateSet templateSet = new IPFIXTemplateSet();
        List<String> elementNames = new ArrayList<>(4);
        ObservableList<Node> nodeObservableList = vBox.getChildren();
        for (Node node : nodeObservableList) {
            ComboBox<String> stringComboBox = (ComboBox<String>)node;
            elementNames.add(stringComboBox.getValue());
        }
        templateSet.addTemplateRecord((short) 777, elementNames);
        return templateSet;
    }

    private PacketHeader createPacketHeader(int tsSec, int lengthInBytes) {
        return new PacketHeader(tsSec, 0, lengthInBytes, lengthInBytes);
    }

    private EthernetFrame createEthernetDataFrame(IPFIXTemplateSet templateSet, int sequenceNumber) {
        IPFIXDataSet dataSet = new IPFIXDataSet(templateSet.getTemplateRecord());
        return createEthernetFrame(dataSet, sequenceNumber);
    }

    private EthernetFrame createEthernetFrame(IPFIXSet ipfixSet, int sequenceNumber) {
        IPFIXMessage dataMessage = new IPFIXMessage(sequenceNumber);
        dataMessage.addSet(ipfixSet);

        UDPDatagram dataUDPDatagram = new UDPDatagram();
        dataUDPDatagram.setBuffer(dataMessage.getBuffer());

        IPv4Datagram dataIPv4Datagram = new IPv4Datagram();
        dataIPv4Datagram.setBuffer(dataUDPDatagram.getBuffer());

        EthernetFrame dataEthernetFrame = new EthernetFrame();
        dataEthernetFrame.setPayload(dataIPv4Datagram);
        return dataEthernetFrame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(IPFIXInformationElements.get().getKeys());

        elementName1.getItems().setAll(treeSet);
        elementName1.setValue("sourceIPv4Address");

        elementName2.getItems().setAll(treeSet);
        elementName2.setValue("destinationIPv4Address");

        elementName3.getItems().setAll(treeSet);
        elementName3.setValue("protocolIdentifier");

        elementName4.getItems().setAll(treeSet);
        elementName4.setValue("vlanId");

    }
}
