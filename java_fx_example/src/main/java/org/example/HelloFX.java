package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ScrollPane scrollPane = new ScrollPane();

        Text helloText = new Text("Hello, world!");
        helloText.setId("welcome-text");

        Label label = new Label();
        label.setTextFill(Color.FIREBRICK);
        label.setVisible(false);

        AtomicInteger counter = new AtomicInteger(0);

        Button button = new Button();
        button.setText("Press me!");
        button.setOnAction(handler -> {
            counter.set(counter.get() + 1);
            label.setText(String.format("Button have been pressed %d times!", counter.get()));
            label.setVisible(true);
        });

        VBox vBox = new VBox(helloText, button, label);
        vBox.setId("base");
        vBox.setAlignment(Pos.CENTER);

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 600, 480);

        stage.setTitle("Hello FX!");
        stage.setScene(scene);
        scene.getStylesheets()
                .add(HelloFX.class.getResource("/css/HelloFX.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}