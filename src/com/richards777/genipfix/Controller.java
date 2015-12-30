package com.richards777.genipfix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {

    @FXML
    private CheckBox checkBox1;

    @FXML
    private TextField elementName1;

    @FXML
    private TextField elementSize1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private TextField elementName2;

    @FXML
    private TextField elementSize2;

    @FXML
    private CheckBox checkBox3;

    @FXML
    private TextField elementName3;

    @FXML
    private TextField elementSize3;

    @FXML
    private CheckBox checkBox4;

    @FXML
    private TextField elementName4;

    @FXML
    private TextField elementSize4;

    @FXML
    private TextField filename;

    @FXML
    private Button genButton;

    @FXML
    private void generateFile(ActionEvent event) {
        IPFIXTemplateSet templateSet = new IPFIXTemplateSet();
        IPFIXMessage message = new IPFIXMessage();
        message.addSet(templateSet);

        UDPDatagram udpDatagram = new UDPDatagram();
        udpDatagram.setBuffer(message.getBuffer());

        IPv4Datagram iPv4Datagram = new IPv4Datagram();
        iPv4Datagram.setBuffer(udpDatagram.getBuffer());

        EthernetFrame ethernetFrame = new EthernetFrame();
        ethernetFrame.setPayload(iPv4Datagram);

        PacketHeader packetHeader = new PacketHeader(1451499300, 0, ethernetFrame.getLengthInBytes(), ethernetFrame.getLengthInBytes());

        Path file = Paths.get(filename.getText());
        try (OutputStream out = Files.newOutputStream(file)) {
            out.write(GlobalHeader.getBuffer());
            out.write(packetHeader.getBuffer());
            out.write(ethernetFrame.getBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
