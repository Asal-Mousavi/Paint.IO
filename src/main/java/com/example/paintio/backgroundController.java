package com.example.paintio;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.net.URL;
import java.util.ResourceBundle;

public class backgroundController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private Rectangle player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  player.setFill(Color.RED);
     //   player.setX(player.getX()+210);
    }
}
