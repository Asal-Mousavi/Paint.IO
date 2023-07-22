package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BotPlayer extends Player {
   // private BotLogic logic;
    private PaintNode node;
    private Rectangle rect;

    BotPlayer(PaintNode node,int id,int size,BotLogic logic){
        super(id,size,logic);
        setAlive(true);
        this.node=node;
        super.setAlive(true);
        this.rect= new Rectangle(size,size,Color.GREEN);
        Label label = new Label(String.format("%d", super.getNum()));
        getChildren().add(rect);
        getChildren().add(label);
        setX(node.getRow());
        setY(node.getColumn());
    }
    public void setNode(PaintNode n){
        if(isAlive()){
            tail.add(node);
            node.setColor(super.getColor());
        }
        node.removePlayer(this);
        node=n;
        setX(n.getRow());
        setY(n.getColumn());
        n.seat(this);
    }
    public PaintNode getNode(){
        return node;
    }
    private void setColor(){
        switch (getNum()){
            case 1:

        }
    }
}
