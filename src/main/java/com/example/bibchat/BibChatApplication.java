package com.example.bibchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;

public class BibChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BibChatApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("BIBDA Chat");
        stage.setScene(scene);
        stage.show();
        ClientKafka Kafka = ClientKafka.getInstance();
        TextArea outputTextArea = (TextArea) scene.lookup("#outputTextArea");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ConsumerRecords<String, String> enregistrements = Kafka.consumer_message();
                    for (ConsumerRecord<String, String> enregistrement : enregistrements) {
                        //System.out.println("enregistrements" + " " + enregistrement);
                        String cle = enregistrement.key();
                        String valeur = enregistrement.value();
                        outputTextArea.appendText(cle + "> " + valeur + "\n");
                    }
                    Kafka.commitAsynch();

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        t.start();

    }

    public static void main(String[] args) {
        launch();
    }
}