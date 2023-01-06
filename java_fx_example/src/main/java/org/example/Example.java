package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Example extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("It's stage. It's include Scene");
        Group group = new Group();
        group.getChildren().add(new Label("Этот текст - корень сцены"));

        Button button = new Button("Нажать");
        group.getChildren().add(button);

        button.setOnAction(e -> {
            if (button.getText().equals("Нажать")) {
                button.setText("Вы нажали");
            } else {
                button.setText("Нажать");
            }
        });

        Scene scene = new Scene(group, 400, 40, Color.GREEN);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {launch(args);}

}
