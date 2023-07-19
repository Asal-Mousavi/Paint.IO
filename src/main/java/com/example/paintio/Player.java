package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends StackPane {
    private int x;
    private int y;
    private Color color;
    private double size;
    public Player(double size, Color color ,int x ,int y) {
        this.color = color;
        this.size=size;
        this.x=x;
        this.y=y;

        Rectangle rect = new Rectangle( x, y, size,size);
        rect.setFill(color);
        Label label = new Label(String.format("P1"));
        getChildren().add(rect);
        getChildren().add(label);

    }

}
