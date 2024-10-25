package com.example.bibchat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class  BibChatController {

    @FXML
    private TextField inputTextField;

    @FXML
    private TextArea outputTextArea;

    private ClientKafka Kafka = ClientKafka.getInstance();

    @FXML
    private void on_click() {
        //String message = inputTextField.getText();
        Kafka.envoyer_Message(inputTextField.getText());
        inputTextField.clear();
    }
}
