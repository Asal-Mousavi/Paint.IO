package com.example.paintio;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartController {
    @FXML
    private AnchorPane pane;
    KeyCode keyCode;
    @FXML
    private Rectangle rect;
    public int speed;
    public int enemies;
    public Level level;
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
        System.out.println(speed);
        System.out.println(enemies);
        System.out.println(level);
        nodes.fillGridPane(gridPane,0,0);
        player.setFill(nodes.getMainPlayer().getColor());
        //speed=200;
       //thread
        for (int n=0;n<enemies;n++){
            BotLogic bN= new BotLogic(GRID_SIZE,CELL_SIZE,level);
            Thread thread = new Thread(bN);
            thread.start();
        }
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
               handleKeyPress(kEvent);
        });

     /*   scene.setOnKeyPressed(kEvent -> {
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

      */

        // Show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    AnimationTimer timer=new AnimationTimer() {
        @Override
        public void handle(long now) {
       //     boolean action=false;
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
        //            action=true;
                case SPACE:
                    weapons.weaponB(direction);
        //            action=true;
            }
            nodes.fillGridPane(gridPane,currentX,currentY);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            /*if(action){
                switch (direction){
                    case 0:
                        keyCode=KeyCode.RIGHT;
                        break;
                    case 1:
                        keyCode=KeyCode.UP;
                        break;
                    case 2:
                        keyCode=KeyCode.LEFT;
                        break;
                    case 3:
                        keyCode=KeyCode.DOWN;
                        break;
                }
            }

             */
        }
    };
    void handleKeyPress(KeyEvent e){
        keyCode=e.getCode();
        timer.start();
    }

}
