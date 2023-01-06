package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloFXmlController implements Initializable {
    @FXML private Label buttonLabel;
    @FXML private Text helloText;

    private Integer counter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloText.setText("Hello, World!");
    }

    @FXML private void buttonPressed(ActionEvent actionEvent) {
        buttonLabel.setText(String.format("Button have been pressed %d times!", ++counter));
        buttonLabel.setVisible(true);
    }
}
