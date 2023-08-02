package com.example.paintio;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartController {
   // Pane root;
    Stage primaryStage;
    Scene scene;
    @FXML
    private AnchorPane pane;
    KeyCode keyCode;
    private AnimationTimer timer;
    @FXML
    private Rectangle rect;
    public int speed;
    public int enemies;
    public Level level;
    // Define the size of the grid and cell
    private static final int GRID_SIZE = 25;
    private static final int CELL_SIZE = 32;

    private int currentX ;
    private int currentY ;

    int direction;
    // Define the GridPane
    private GridPane gridPane = new GridPane();
    private Rectangle player = new Rectangle(GRID_SIZE / 2 * CELL_SIZE, GRID_SIZE / 2 * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    PlayerLogic nodes =PlayerLogic.getInstance(GRID_SIZE,CELL_SIZE);
    Weapons weapons=new Weapons(nodes);
    public void start(ActionEvent event){
        primaryStage= new Stage();
        nodes.fillGridPane(gridPane,0,0);
        player.setFill(nodes.getMainPlayer().getColor());
       //thread
        for (int n=0;n<enemies;n++){
            BotLogic bN= new BotLogic(GRID_SIZE,CELL_SIZE,level);
            bN.level=level;
            bN.botSpeed=speed*2;
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
        scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        // Show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
        timer =new AnimationTimer() {
            KeyCode lastPress;
            @Override
            public void handle(long now) {
                        //=KeyCode.W;
                if(nodes.getRunning()){
                    switch (keyCode) {
                        case W:
                            direction=1;
                            currentX--;
                            nodes.generateRow(currentX, false);
                            lastPress=keyCode;
                            break;
                        case S:
                            direction=3;
                            currentX++;
                            nodes.generateRow(currentX, true);
                            lastPress=keyCode;
                            break;
                        case D:
                            direction=0;
                            currentY++;
                            nodes.generateColumn(currentY, true);
                            lastPress=keyCode;
                            break;
                        case A:
                            direction=2;
                            currentY--;
                            nodes.generateColumn(currentY, false);
                            lastPress=keyCode;
                            break;
                        case ENTER:
                            weapons.weaponA(direction);
                            keyCode=lastPress;
                            break;
                        case SPACE:
                            weapons.weaponB(direction);
                            keyCode=lastPress;
                            break;
                    }
                    nodes.fillGridPane(gridPane,currentX,currentY);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    timer.stop();
                    gameOver();
                }
            }

        };
        scene.setOnKeyPressed(kEvent -> {
            if(nodes.getRunning()){
                keyCode=kEvent.getCode();
                timer.start();
            }
        });
    }
    public void gameOver() {
        primaryStage.close();
        primaryStage= new Stage();

        Label bigLabel = new Label("    GAME OVER !");
        bigLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 50));
        bigLabel.setTextFill(Color.PURPLE);
        bigLabel.setAlignment(Pos.CENTER);
        bigLabel.setPrefWidth(GRID_SIZE * CELL_SIZE);

        Label smallLabel = new Label("   score :"+nodes.getMainPlayer().territory.size());
        smallLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        smallLabel.setTextFill(Color.GOLD);
        smallLabel.setAlignment(Pos.CENTER);
        smallLabel.setPrefWidth(GRID_SIZE * CELL_SIZE);

        // Center the labels in the pane
        bigLabel.layoutXProperty().bind(pane.widthProperty().subtract(bigLabel.widthProperty()).divide(2));
        bigLabel.layoutYProperty().bind(pane.heightProperty().subtract(bigLabel.heightProperty()).divide(2).subtract(30));
        smallLabel.layoutXProperty().bind(pane.widthProperty().subtract(smallLabel.widthProperty()).divide(2));
        smallLabel.layoutYProperty().bind(pane.heightProperty().subtract(smallLabel.heightProperty()).divide(2).add(30));

        Image image = new Image("C:\\Users\\user\\IdeaProjects\\New folder\\Paint.IO\\src\\main\\resources\\com\\example\\paintio\\image.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(GRID_SIZE * CELL_SIZE);
        imageView.setFitHeight(GRID_SIZE * CELL_SIZE);
        Pane root = new Pane(imageView,bigLabel,smallLabel);
        scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        // Show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
        /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("gameOver.fxml"));
        Pane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene2 = new Scene(root);
        Stage primaryStage2 = (Stage) pane.getScene().getWindow();
        primaryStage2.setScene(scene2);
        primaryStage2.show();

         */
    }
}
