package com.example.paintio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Node extends StackPane {
    public int x;
    public int y;
    public int row;
    public int column;

    Node(){
        Rectangle rectangle=new Rectangle(40,40, Color.GREEN);
    }
}
