package com.example.paintio;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.io.IOException;

public class HelloApplication extends Application {

    private Parent root;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        //start
        //background
        root=loader.load();
        //"hello-view.fxml"
        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("Paint.IO");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    System.out.println("U");
                    break;
                case DOWN:
                    System.out.println("D");
                    break;
                case RIGHT:
                    System.out.println("R");
                    break;
                case LEFT:
                    System.out.println("L");
                    break;
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}