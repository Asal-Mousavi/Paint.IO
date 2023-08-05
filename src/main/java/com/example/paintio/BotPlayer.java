package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class BotPlayer extends Player {
    private PaintNode node;
    BotPlayer(PaintNode node,int id,int size,BotLogic logic){
        super(id,size,logic);
        this.node=node;
        Rectangle rect= new Rectangle(size,size,super.getColor());
        Label label = new Label(String.format("%d", super.getNum()));
        getChildren().add(rect);
        getChildren().add(label);
        setX(node.getRow());
        setY(node.getColumn());
    }
    public void setNode(PaintNode n){
        if(isAlive()){
            // Add previous node to tail
            tail.add(node);
            node.isTaken=true;
            node.setColor(super.getTailColor());
            getLogic().addVertex(node.getRow(),node.getColumn());
            // Fill in a closed area
            if(n.getColor()==getColor()){
                getLogic().vertex.add(n);
                int i=0;
                boolean right=false;
                if(getLogic().vertex.size()>1){
                    if(getLogic().vertex.size()>2 && getLogic().vertex.get(0)==getLogic().vertex.get(1))
                        i++;
                    if(getLogic().vertex.get(i).getColumn()>getLogic().vertex.get(i+1).getColumn() )
                        right=true;
                }
                getLogic().conquest(this,right);
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
