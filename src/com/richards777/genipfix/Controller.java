package com.richards777.genipfix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
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
        Path file = Paths.get(filename.getText());
        try {
            Files.write(file, GlobalHeader.getBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
