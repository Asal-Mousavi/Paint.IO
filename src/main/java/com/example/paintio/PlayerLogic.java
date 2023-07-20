package com.example.paintio;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlayerLogic extends GameLogic{
    private static PlayerLogic instance=null;
    private PlayerLogic(int gS, int cS) {
        super(gS, cS);
        initialize();
        defult(Color.RED,gridSize/2,gridSize/2);
    }
    public static synchronized PlayerLogic getInstance(int gS, int cS){
        if (instance==null){
            instance=new PlayerLogic(gS,cS);
        }
        return instance;
    }
    private void initialize(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                PaintNode node;
                if((i+j)%2==0){
                    node=new PaintNode(cellSize, Color.WHITE,i,j);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,i,j);
                }
                factory.add(node);
                if(i==0 && !columns.contains(j))
                    columns.add(j);
            }
            if(!rows.contains(i))
                rows.add(i);
        }
    }
    public void generateColumn(int c,boolean direction){
        if(direction){
            //Right move
            c += gridSize-1;
        }
        if(!columns.contains(c)){
            for(int i=0 ; i< rows.size() ; i++){
                int row = rows.get(i);
                PaintNode node;
                if((row+c)%2==0){
                    node=new PaintNode(cellSize, Color.WHITE,row,c);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,row,c);
                }
                factory.add(node);
            }
            columns.add(c);
        }
    }
    public void generateRow(int r,boolean direction){
        if(direction){
            //Down move
            r += gridSize-1;
        }
        if(!rows.contains(r)){
            for(int j=0 ; j< columns.size() ; j++){
                int column=columns.get(j);
                PaintNode node;
                if((r+column)%2==0){
                    node=new PaintNode(cellSize,Color.WHITE,r,column);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,r,column);
                }
                factory.add(node);
            }
            rows.add(r);
        }
    }
    public void fillGridPane(GridPane g, int r , int c ){
        kill(r,c);
        deduplication();
        g.getChildren().clear();
        for(int k=0 ; k< gridSize ; k++){
            int i=r+k;
            for(int z=0 ; z<gridSize ;z++){
                int j=z+c;
                int index=nodeExist(i,j);
                g.add(factory.get(index),z, k);

                if(k==gridSize/2 && z==gridSize/2){
                    color(i,j);
                }
            }
        }
    }
}
