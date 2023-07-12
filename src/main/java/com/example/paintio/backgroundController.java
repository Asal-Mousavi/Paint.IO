package com.example.paintio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class backgroundController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Rectangle player;
    private double y=140;
    private  double x=140;
    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    private GridPane grid;
    public void move(){

        player.setX(0);
        player.setY(0);
    }
}