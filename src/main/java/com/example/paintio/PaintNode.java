package com.example.paintio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PaintNode extends StackPane {
    private Color color;
    private double size;
    private int row;
    private int column;

    public PaintNode(double size,Color color,int row,int column) {
        this.color = color;
        this.size=size;
        this.row=row;
        this.column=column;

        Rectangle rectangle = new Rectangle(size,size);
        rectangle.setFill(color);

        getChildren().add(rectangle);
    }

    public void setColor(Color color) {
        this.color = color;
        ((Rectangle) getChildren().get(0)).setFill(color);
    }

    public Color getColor() {
        return color;
    }

}