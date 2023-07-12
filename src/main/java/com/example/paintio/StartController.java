package com.example.paintio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import static javafx.scene.input.KeyCode.UP;

public class StartController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Rectangle rect;
    // Define the size of the grid and cell
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 40;
    // Define the center position of the grid
    private static final int CENTER_X = GRID_SIZE / 2;
    private static final int CENTER_Y = GRID_SIZE / 2;

    // Define the current position of the grid
    private int currentX = CENTER_X;
    private int currentY = CENTER_Y;

    // Define the GridPane
    private GridPane gridPane = new GridPane();
    private Rectangle player = new Rectangle(CENTER_X * CELL_SIZE, CENTER_Y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

    public void start(ActionEvent event){
        System.out.println("started");
        Stage primaryStage= new Stage();
        // Create the initial grid
        createGridPane(GRID_SIZE,GRID_SIZE,gridPane);
        player.setFill(Color.RED);
        // Create the scene
        Pane root = new Pane(gridPane,player);
        Scene scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        scene.setOnKeyPressed(kEvent -> {
            KeyCode keyCode = kEvent.getCode();
            switch (keyCode) {
                case UP:
                    moveGrid(0, -1);
                    break;
                case DOWN:
                    moveGrid(0, 1);
                    break;
                case LEFT:
                    moveGrid(-1, 0);
                    break;
                case RIGHT:
                    moveGrid(1, 0);
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

    private void moveGrid(int dx , int dy){
        currentX += dx;
        currentY += dy;

        gridPane.setLayoutX(gridPane.getLayoutX() - dx * CELL_SIZE);
        gridPane.setLayoutY(gridPane.getLayoutY() - dy * CELL_SIZE);
        //dx
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle rectangle;
                if((i+j)%2==0){
                    rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
                } else {
                    rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.GRAY);
                }

                Label label = new Label(String.format("(%d,%d)", i, j));
                StackPane cellPane = new StackPane(rectangle, label);
                //      gP.add(cellPane, j, i);
                gridPane.add(cellPane, j, i);
            }
        }
    }
    private void createGridPane(int r , int c,GridPane gP){

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Rectangle rectangle;
                if((i+j)%2==0){
                    rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
                } else {
                    rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.GRAY);
                }

                Label label = new Label(String.format("(%d,%d)", i, j));
                StackPane cellPane = new StackPane(rectangle, label);
                gP.add(cellPane, j, i);
                //      gridPane.add(cellPane, j, i);
            }
        }
    }


}
