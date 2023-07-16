package com.example.paintio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.ArrayList;

public class StartController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Rectangle rect;
    // Define the size of the grid and cell
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 40;

    private int currentX ;
    private int currentY ;

    // Define the GridPane
    private GridPane gridPane = new GridPane();
    private Rectangle player = new Rectangle(GRID_SIZE / 2 * CELL_SIZE, GRID_SIZE / 2 * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    NodeFactory nodes=new NodeFactory(GRID_SIZE , CELL_SIZE);

    public void start(ActionEvent event){
        System.out.println("started");
        Stage primaryStage= new Stage();nodes.fillGridPane(gridPane,0,0);

        player.setFill(Color.RED);
        Pane root = new Pane(gridPane,player);

        Scene scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        scene.setOnKeyPressed(kEvent -> {
            KeyCode keyCode = kEvent.getCode();
            switch (keyCode) {
                case W:
                    currentX--;
                    nodes.generateRow(currentX, false);
                    nodes.fillGridPane(gridPane,currentX,currentY);
                    break;
                case S:
                    currentX++;
                    nodes.generateRow(currentX, true);
                    nodes.fillGridPane(gridPane,currentX,currentY);
                    break;
                case D:
                    currentY++;
                    nodes.generateColumn(currentY, true);
                    nodes.fillGridPane(gridPane,currentX,currentY);
                    break;
                case A:
                    currentY--;
                    nodes.generateColumn(currentY, false);
                    nodes.fillGridPane(gridPane,currentX,currentY);
                    break;
                default:
                    // do nothing
                    break;
            }
        });


        // Show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
