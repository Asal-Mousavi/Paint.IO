package com.example.paintio;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Player extends StackPane {
    ColorCollection clr=ColorCollection.getInstance();
    private int x;
    private int y;
    public ArrayList<PaintNode> tail = new ArrayList<>();
    public ArrayList<PaintNode> territory = new ArrayList<>();
    private final GameLogic logic;
    private final Color color;
    private final Color tailColor;
    private final int num;
    private boolean isAlive;

    Player(int id,int size,GameLogic logic) {
        // Default location of mainPlayer is in the center of screen
        x=size/2;
        y=size/2;
        this.num = id;
        this.logic=logic;
        setAlive(true);
        tailColor=clr.getTailColor(num);
        color=clr.getTerritoryColor(num);
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
        return color;
    }
    public Color getTailColor() {
        return tailColor;
    }
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

}
