package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloFXml extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloFXml.class.getResource("/fxml/HelloFXml.fxml"));

        Scene scene = new Scene(loader.load(), 600, 480);
        stage.setScene(scene);
        stage.setTitle("Hello FXml!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
