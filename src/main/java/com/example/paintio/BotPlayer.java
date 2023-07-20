package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BotPlayer extends Player {
    private PaintNode node;
    public ArrayList<PaintNode> tail = new ArrayList<>();
    private Color color;
    private int num;
    private Rectangle rect;
    public boolean isAlive;

    BotPlayer(PaintNode node,int id,int size,Color clr){
        super(id,clr);
        this.num=id;
        this.node=node;
        color=clr;
      //  this.color=Color.CADETBLUE;
        isAlive=true;
        this.rect= new Rectangle(size,size,Color.GREEN);
        Label label = new Label(String.format("%d", num));
        getChildren().add(rect);
        getChildren().add(label);
    }
    public void setNode(PaintNode n){
        tail.add(node);
        node.setColor(color);
        node.removePlayer(this);
        node=n;
        n.seat(this);
    }
    public int getNum(){
        return num;
    }

    public PaintNode getNode(){
        return node;
    }
    public Color getColor(){
        return color;}
}
