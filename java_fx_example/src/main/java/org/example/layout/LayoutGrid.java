package org.example.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LayoutGrid extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();
        root.setGridLinesVisible(true);

        root.add(new Label("0x0"), 0, 0);
        root.add(new Label("0x1"), 0, 1);
        root.add(new Label("1x1"), 1, 1);
        root.add(new Label("1x2"), 1, 2);
        root.add(new Label("2x3"), 2, 3);
        root.add(new Label("3x1"), 3, 1);


        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
