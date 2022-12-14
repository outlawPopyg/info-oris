package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class SpaceInvadersApp extends Application {

    private BufferedReader in;
    private PrintWriter out;
    private final String HOST = "localhost";
    private final int PORT = 4444;

    private final Pane root = new Pane();

    private double t = 0;

    private int enemiesCount;

    private final Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);

    private Parent createContent() {
        root.setPrefSize(600, 800);

        Image image = new Image("gamer.png");
        player.setFill(new ImagePattern(image));

        root.getChildren().add(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        nextLevel();

        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i*100, 150, 30, 30, "enemy", Color.RED);
            Image image = new Image("enemy.png");
            s.setFill(new ImagePattern(image));

            enemiesCount ++;

            root.getChildren().add(s);
        }
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }

    private void update() {

        if (enemiesCount == 0) {
            nextLevel();
        }

        t += 0.016;

        sprites().forEach(s -> {
            switch (s.type) {

                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;

                            enemiesCount--;
                        }
                    });

                    break;

                case "enemy":

                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (t > 2) {
            t = 0;
        }
    }

    private void shoot(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 12, 20, who.type + "bullet", Color.BLACK);
        s.setFill(new ImagePattern(new Image("fireball.png")));

        root.getChildren().add(s);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(createContent());
        scene.setFill(new ImagePattern(new Image("space.jpg")));

        Socket socket = new Socket(HOST, PORT);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        ServerConnection serverConn = new ServerConnection(socket);

        new Thread(serverConn).start();

        scene.setOnKeyPressed(e -> {

            KeyCode keyCode = e.getCode();
            if (keyCode.equals(KeyCode.A)) {
                player.moveLeft();
                out.println("left");
            } else if (keyCode.equals(KeyCode.D)) {
                player.moveRight();
                out.println("right");
            } else if (keyCode.equals(KeyCode.SPACE)) {
                shoot(player);
                out.println("shoot");
            } else if (keyCode.equals(KeyCode.R)) {
                out.println("broadcast");
            } else if (keyCode.equals(KeyCode.ESCAPE)) {
                try {
                    socket.close();
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        stage.setScene(scene);
        stage.show();
    }

    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;

        Sprite(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 5);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 5);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - 5);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + 5);
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}