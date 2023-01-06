package org.example.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FlowPaneExample extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FlowPane root = new FlowPane();
        root.getChildren().add(new Button("Button #1"));
        root.getChildren().add(new Button("Button #2"));
        root.getChildren().add(new Button("Button #3"));
        root.getChildren().add(new Button("Button #4"));
        root.getChildren().add(new Button("Button #5"));
        root.getChildren().add(new Button("Button #6"));


        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
