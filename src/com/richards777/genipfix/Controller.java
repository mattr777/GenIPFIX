package com.richards777.genipfix;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class Controller implements Initializable {

    @FXML
    private TextField nDataRecords;

    @FXML
    private TextField destPort;

    @FXML
    private TextField templateID;

    @FXML
    private TextField filename;

    @FXML
    private VBox vBox;

    @FXML
    private void addIANAField(ActionEvent event) {
        createIANAField(null);
    }

    private void createIANAField(String selectedName) {
        ComboBox<String> stringComboBox = new ComboBox<>();
        stringComboBox.setPromptText("element name");
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(IPFIXInformationElements.get().getNames());

        stringComboBox.getItems().setAll(treeSet);
        if (selectedName != null) {
            stringComboBox.setValue(selectedName);
        }

        vBox.getChildren().add(stringComboBox);
    }

    @FXML
    private void addEntField(ActionEvent event) {
        createEntField("Visual Networks");
    }

    private void createEntField(String enterpriseName) {
        createEntField(enterpriseName, null, null);
    }

    private void createEntField(String enterpriseName, Integer elementID, Integer fieldLength) {
        HBox hBox = new HBox(10.0);
        ComboBox<String> stringComboBox = new ComboBox<>();
        stringComboBox.setPromptText("enterprise");
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(PrivateEnterpriseNumbers.get().getNames());
        stringComboBox.getItems().setAll(treeSet);
        if (enterpriseName != null) {
            stringComboBox.setValue(enterpriseName);
        }
        hBox.getChildren().add(stringComboBox);

        TextField elementIDField = new TextField();
        elementIDField.setPromptText("element ID");
        if (elementID != null) {
            elementIDField.setText(elementID.toString());
        }
        hBox.getChildren().add(elementIDField);

        TextField fieldLengthField = new TextField();
        fieldLengthField.setPromptText("field length");
        if (fieldLength != null) {
            fieldLengthField.setText(fieldLength.toString());
        }
        hBox.getChildren().add(fieldLengthField);

        vBox.getChildren().add(hBox);
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void  closeTemplateFile(ActionEvent event) {
        vBox.getChildren().clear();
    }

    @FXML
    private void openTemplateFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Template File");
        fileChooser.setInitialFileName("IPFIXTemplate.json");
        fileChooser.setInitialDirectory(new File("").getAbsoluteFile());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Template Files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(vBox.getScene().getWindow());
        if (selectedFile == null) {
            return;
        }
        vBox.getChildren().clear();
        Path path = Paths.get(selectedFile.getPath());
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            advanceToKeyword(reader, "templateSet");
            Integer templateID = getInt(reader, "templateID");
            this.templateID.setText(templateID.toString());
            int fieldCount = getInt(reader, "fieldCount");
            advanceToKeyword(reader, "fields");
            for (int i = 0; i < fieldCount; i++) {
                boolean isEnterprise = getBoolean(reader, "isEnterprise");
                int informationElementID = getInt(reader, "informationElementID");
                int fieldLength = getInt(reader, "fieldLength");
                int enterpriseNumber = 0;
                if (isEnterprise) {
                    enterpriseNumber = getInt(reader, "enterpriseNumber");
                    String enterpriseName = PrivateEnterpriseNumbers.get().getName(enterpriseNumber);
                    createEntField(enterpriseName, informationElementID, fieldLength);
                } else {
                    String fieldName = IPFIXInformationElements.get().getName(informationElementID);
                    createIANAField(fieldName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void advanceToKeyword(BufferedReader reader, String keyword) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            if (line.contains(keyword)) {
                break;
            }
            line = reader.readLine();
        }
    }

    private int getInt(BufferedReader reader, String keyword) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            if (line.contains(keyword)) {
                String delims = "[ \":,]+";
                String[] tokens = line.split(delims);
                return Integer.parseInt(tokens[2]);
            }
            line = reader.readLine();
        }
        return 0;
    }

    private boolean getBoolean(BufferedReader reader, String keyword) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            if (line.contains(keyword)) {
                String delims = "[ \":,]+";
                String[] tokens = line.split(delims);
                return Boolean.parseBoolean(tokens[2]);
            }
            line = reader.readLine();
        }
        return false;
    }

    @FXML
    private void saveTemplateFile(ActionEvent event) {
        IPFIXTemplateSet templateSet = createIpfixTemplateSet();
        Path path = Paths.get("IPFIXTemplate.json");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("{\n");
            writer.write("  \"templateSet\": ");
            writer.write(templateSet.getAsJsonString());
            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateFile(ActionEvent event) {
        IPFIXTemplateSet templateSet = createIpfixTemplateSet();
        List<IPFIXTemplateSet> ipfixTemplateSets = new ArrayList<>();
        ipfixTemplateSets.add(templateSet);

        short udpPort = Short.parseShort(destPort.getText());

        EthernetFrame templateEthernetFrame = createEthernetFrame(ipfixTemplateSets, 1, udpPort);

        int timeInSeconds = 1451499300;
        PacketHeader templatePacketHeader = createPacketHeader(timeInSeconds, templateEthernetFrame.getLengthInBytes());

        Path path = Paths.get(filename.getText());
        int nRecords = Integer.parseInt(nDataRecords.getText());
        int scenario = 1;
        try (OutputStream out = Files.newOutputStream(path)) {
            out.write(GlobalHeader.getBuffer());
            out.write(templatePacketHeader.getBuffer());
            out.write(templateEthernetFrame.getBuffer());

            for (int i = 0; i < nRecords; i++) {
                timeInSeconds++;
                EthernetFrame ethernetDataFrame = createEthernetDataFrame(templateSet, i+1, scenario, timeInSeconds, udpPort);
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
        List<IPFIXFieldSpecifier> elements = new ArrayList<>(4);
        ObservableList<Node> vBoxChildren = vBox.getChildren();
        for (Node vboxChild : vBoxChildren) {
            if (vboxChild instanceof ComboBox) {
                ComboBox<String> ianaComboBox = (ComboBox<String>) vboxChild;
                int elementID = IPFIXInformationElements.get().getElementID(ianaComboBox.getValue());
                elements.add(new IPFIXFieldSpecifier((short)elementID));
            } else if (vboxChild instanceof HBox) {
                HBox hBox = (HBox) vboxChild;
                int enterpriseNumber = 0;
                int elementID = 0;
                int fieldLength = 0;
                ObservableList<Node> hBoxChildren = hBox.getChildren();
                for (Node hBoxChild : hBoxChildren) {
                    if (hBoxChild instanceof ComboBox) {
                        ComboBox<String> entComboBox = (ComboBox<String>) hBoxChild;
                        enterpriseNumber = PrivateEnterpriseNumbers.get().getNumber(entComboBox.getValue());
                    } else {
                        TextField textField = (TextField) hBoxChild;
                        if (textField.getPromptText().equals("element ID")) {
                            elementID = Integer.parseInt(textField.getText());
                        } else {
                            fieldLength = Integer.parseInt(textField.getText());
                        }
                    }
                }
                elements.add(new IPFIXFieldSpecifier((short)elementID, (short)fieldLength, enterpriseNumber));
            }
        }
        int templateID = Integer.parseInt(this.templateID.getText());
        templateSet.addTemplateRecord((short) templateID, elements);
        return templateSet;
    }

    private PacketHeader createPacketHeader(int tsSec, int lengthInBytes) {
        return new PacketHeader(tsSec, 0, lengthInBytes, lengthInBytes);
    }

    private EthernetFrame createEthernetDataFrame(IPFIXTemplateSet templateSet, int sequenceNumber, int scenario, int timeStamp, short udpPort) {
        IPFIXDataSet dataSet = new IPFIXDataSet(templateSet.getTemplateRecord(), scenario, timeStamp);
        List<IPFIXDataSet> ipfixDataSets = new ArrayList<>();
        ipfixDataSets.add(dataSet);
        return createEthernetFrame(ipfixDataSets, sequenceNumber, udpPort);
    }

    private EthernetFrame createEthernetFrame(List<? extends IPFIXSet> ipfixSets, int sequenceNumber, short udpPort) {
        IPFIXMessage ipfixMessage = new IPFIXMessage(sequenceNumber);
        ipfixSets.forEach(ipfixMessage::addSet);

        UDPDatagram udpDatagram = new UDPDatagram(udpPort);
        udpDatagram.setPayload(ipfixMessage.getBuffer());

        IPv4Datagram iPv4Datagram = new IPv4Datagram();
        iPv4Datagram.setBuffer(udpDatagram.getBuffer());

        EthernetFrame ethernetFrame = new EthernetFrame();
        ethernetFrame.setPayload(iPv4Datagram);
        return ethernetFrame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createIANAField("sourceIPv4Address");
        createIANAField("destinationIPv4Address");
        createIANAField("protocolIdentifier");
        createIANAField("vlanId");
    }
}
