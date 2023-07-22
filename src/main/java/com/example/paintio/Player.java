package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends StackPane {
    private int x;
    private int y;
    public ArrayList<PaintNode> tail = new ArrayList<>();
    public ArrayList<PaintNode> territory = new ArrayList<>();
    private GameLogic logic;
    private Color color;
    private Color tailColor;
    private int num;
    private boolean isAlive;

    Player(int id,int size,GameLogic logic) {
        x=size/2;
        y=size/2;
        this.num = id;
        this.logic=logic;
        isAlive = true;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Color getColor(){
        return color;}
    public void setAlive(boolean a){
        isAlive=a;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public int getNum(){
        return num;
    }
    public GameLogic getLogic() {
        return logic;
    }
    public Color getTailColor() {
        return tailColor;
    }
    public void setTailColor(Color tailColor) {
        this.tailColor = tailColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
