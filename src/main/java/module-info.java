module com.example.bibchat {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.bibchat to javafx.fxml;
    exports com.example.bibchat;

    requires kafka.clients;
}