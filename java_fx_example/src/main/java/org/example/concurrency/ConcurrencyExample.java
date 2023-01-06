package org.example.concurrency;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConcurrencyExample extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Concurrency example");

        ProgressBar progressBar = new ProgressBar(0);

        VBox box = new VBox(progressBar);
        Scene scene = new Scene(box, 960, 600);

        stage.setScene(scene);
        stage.show();

        new Thread(() -> {
            double progress = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new IllegalArgumentException(e);
                }

                progress += 0.1;
                final double finalProgress = progress;

                Platform.runLater(() -> progressBar.setProgress(finalProgress));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
