package org.example.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LayoutBorder extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setLeft(new Button("Left"));
        root.setRight(new Button("Right"));
        root.setCenter(new Button("Center"));

        Pane pane = new Pane();
        Button bottomButton = new Button("bottom");
        bottomButton.setLayoutX(20);
        bottomButton.setLayoutY(20);
        pane.getChildren().add(bottomButton);
        root.setBottom(pane);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
