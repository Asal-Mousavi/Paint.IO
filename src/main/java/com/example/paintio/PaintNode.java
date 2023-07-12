package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PaintNode extends StackPane {
    private Color color;
    private double size;

    private int column;
    private int row;
    public int x;
    public int y;
    public PaintNode(double size,Color color,int row,int column) {
        this.color = color;
        this.size=size;
        this.row=row;
        this.column=column;
        x=row;
        y=column;

        Rectangle rectangle = new Rectangle(size,size);
        rectangle.setFill(color);
        Label label = new Label(String.format("(%d,%d)", row , column));
        getChildren().add(rectangle);
        getChildren().add(label);
    }

    public void setColor(Color color) {
        this.color = color;
        ((Rectangle) getChildren().get(0)).setFill(color);
    }

    public Color getColor() {
        return color;
    }
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}