package com.example.paintio;

import javafx.animation.AnimationTimer;
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

    int direction;
    // Define the GridPane
    private GridPane gridPane = new GridPane();
    private Rectangle player = new Rectangle(GRID_SIZE / 2 * CELL_SIZE, GRID_SIZE / 2 * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    PlayerLogic nodes =PlayerLogic.getInstance(GRID_SIZE,CELL_SIZE);
    Weapons weapons=new Weapons(nodes);
    public void start(ActionEvent event){
        Stage primaryStage= new Stage();
        nodes.fillGridPane(gridPane,0,0);
        player.setFill(nodes.getMainPlayer().getColor());
       //thread
/*        BotLogic bN= new BotLogic(GRID_SIZE,CELL_SIZE,Level.EASY);
        Thread thread = new Thread(bN);
        thread.start();

        BotLogic bN1= new BotLogic(GRID_SIZE,CELL_SIZE,Level.EASY);
        Thread thread1 = new Thread(bN1);
        thread1.start();

 */
        Pane root = new Pane(gridPane,player);
        Scene scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        scene.setOnKeyPressed(kEvent -> {
            KeyCode keyCode = kEvent.getCode();
            switch (keyCode) {
                case W:
                    direction=1;
                    currentX--;
                    nodes.generateRow(currentX, false);
                    break;
                case S:
                    direction=3;
                    currentX++;
                    nodes.generateRow(currentX, true);
                    break;
                case D:
                    direction=0;
                    currentY++;
                    nodes.generateColumn(currentY, true);
                    break;
                case A:
                    direction=2;
                    currentY--;
                    nodes.generateColumn(currentY, false);
                    break;
                case ENTER:
                    weapons.weaponA(direction);
                case SPACE:
                    weapons.weaponB(direction);
            }
            nodes.fillGridPane(gridPane,currentX,currentY);
        });

        // Show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
