package com.example.paintio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PaintNode extends StackPane {
    private Color color;
    private int column;
    private int row;
    public boolean isTaken;
    public boolean seated;
    private Player owner;
    private Color defualtColor;

    PaintNode(double size,int row,int column) {
        this.color = color;
        this.row=row;
        this.column=column;
        isTaken=false;
        seated=false;
        setDefaultColor();
        Rectangle rectangle = new Rectangle(size,size);
        rectangle.setFill(defualtColor);
        getChildren().add(rectangle);
    }
    private void setDefaultColor(){
        if((row+column)%2==0)
            defualtColor=Color.ALICEBLUE;
        else
            defualtColor=Color.LIGHTGRAY;
    }
    public Color getDefualtColor(){
        return defualtColor;
    }
    public synchronized void setColor(Color color) {
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
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    // For botPlayers
    public void seat(BotPlayer b){
        getChildren().add(b);
        seated=true;
    }
    public void removePlayer(BotPlayer b){
        getChildren().remove(b);
        seated=false;
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