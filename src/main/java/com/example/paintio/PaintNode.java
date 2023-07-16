package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PaintNode extends StackPane {
    private Color color;

    private int column;
    private int row;

    PaintNode(double size,Color color,int row,int column) {

        this.color = color;
        this.row=row;
        this.column=column;

        Rectangle rectangle = new Rectangle(size,size);
        rectangle.setFill(color);
   //     Label label = new Label(String.format("(%d,%d)", row , column));
        getChildren().add(rectangle);
    //    getChildren().add(label);
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + row;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaintNode other = (PaintNode) obj;
        if (column != other.column)
            return false;
        if (row != other.row)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "["+ row + "," +column+ "]";
    }


}