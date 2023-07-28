package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class BotPlayer extends Player {
    private PaintNode node;
    private Rectangle rect;

    BotPlayer(PaintNode node,int id,int size,BotLogic logic){
        super(id,size,logic);
        setAlive(true);
        this.node=node;
        super.setAlive(true);
        this.rect= new Rectangle(size,size,super.getColor());
        Label label = new Label(String.format("%d", super.getNum()));
        getChildren().add(rect);
        getChildren().add(label);
        setX(node.getRow());
        setY(node.getColumn());
    }
    public void setNode(PaintNode n){
        if(isAlive()){
            if(n.getColor()==getColor())
                System.out.println();
            //    getLogic().conquest(this);
            else {
                tail.add(node);
                node.setColor(super.getTailColor());
            }
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
}
