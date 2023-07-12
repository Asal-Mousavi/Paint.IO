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
    // Define the center position of the grid
    private static final int CENTER_X = GRID_SIZE / 2;
    private static final int CENTER_Y = GRID_SIZE / 2;

    // Define the current position of the grid
    private int currentX = CENTER_X;
    private int currentY = CENTER_Y;

    // Define the GridPane
    private GridPane gridPane = new GridPane();
    private Rectangle player = new Rectangle(CENTER_X * CELL_SIZE, CENTER_Y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
 //   private Player player=new Player(40,Color.BLUE,CENTER_X * CELL_SIZE, CENTER_Y * CELL_SIZE);
    private ArrayList<ArrayList<PaintNode>> nodes = new ArrayList<ArrayList<PaintNode>>();
    public void start(ActionEvent event){
        System.out.println("started");
        Stage primaryStage= new Stage();
        // Create the initial grid
     //   createGridPane(GRID_SIZE,GRID_SIZE,gridPane);
        createNodes(0,0);
        fillGrid();

    // add the node to the grid pane
   //     gridPane.getChildren().add(player);
        player.setFill(Color.RED);
        // Create the scene
        Pane root = new Pane(gridPane,player);
   //     root.getChildren().add(player);
        Scene scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        scene.setOnKeyPressed(kEvent -> {
            KeyCode keyCode = kEvent.getCode();
            switch (keyCode) {
                case W:
                    moveGrid(0, -1);
                    break;
                case S:
                    moveGrid(0, 1);
                    break;
                case A:
                    moveGrid(-1, 0);
                    break;
                case D:
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
    private void colored(PaintNode n){
        if( n.x==10 && n.y==10){
            System.out.println(n.getRow()+"\t"+n.getColumn());
            n.setColor(Color.ORANGE);
        }

    }
    private void createNodes(int rS , int cS){
        for (int i = rS ; i < GRID_SIZE; i++) {
            nodes.add(new ArrayList<PaintNode>());
        }

        for (int i = rS; i < GRID_SIZE; i++) {
            for (int j = cS; j < GRID_SIZE; j++) {
                PaintNode node;
                if((i+j)%2==0){
                    node=new PaintNode(CELL_SIZE,Color.WHITE,i,j);
                } else {
                    node=new PaintNode(CELL_SIZE,Color.GRAY,i,j);
                }
                nodes.get(i).add(node);
            }
        }
    }
    private void fillGrid(){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridPane.add(nodes.get(i).get(j), j,i);
            }
        }
    }

    private void moveGrid(int dx , int dy){
        nodes.get(currentY).get(currentX).setColor(Color.ORANGE);

        currentX += dx;
        currentY += dy;

        gridPane.setLayoutX(gridPane.getLayoutX() - dx * CELL_SIZE);
        gridPane.setLayoutY(gridPane.getLayoutY() - dy * CELL_SIZE);

       /*
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                colored(nodes.get(i).get(j));
            }
        }
        */

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
