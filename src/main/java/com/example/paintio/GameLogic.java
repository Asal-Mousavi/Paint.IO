package com.example.paintio;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class GameLogic {
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    ArrayList<Integer> rows = new ArrayList<>();
    ArrayList<Integer> columns = new ArrayList<>();
    int gridSize;
    int cellSize;

    GameLogic(int gridSize,int cellSize){

        this.gridSize=gridSize;
        this.cellSize=cellSize;

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

    void defult(Color color , int s){
        System.out.println(s);
        for (int i = s+1 ; i < s+5 ; i++) {
            for (int j = s-3 ; j < s+1; j++) {
                int index=nodeExist(i,j);
                if(index>0){
                    factory.get(index).setColor(color);
                }
            }
        }
    }
    public int nodeExist(int r,int c){
        for(int i=0 ; i< factory.size() ; i++){
            if((factory.get(i).getColumn() == c) && (factory.get(i).getRow()==r)){
                return i;
            }
        }
        return -1;
    }
    public void deduplication(){
        ArrayList<PaintNode> unique = new ArrayList<PaintNode>(factory);
        for(PaintNode p: factory){
            if( !unique.contains(p) ){
                unique.add(p);
            }
        }
        factory.clear();
        factory.addAll(unique);
        unique.clear();
    }

    @Override
    public String toString() {
        String str = "factory :";
        for(PaintNode p: factory){
            str += p.toString() ;
        }
        return " horizontalMove="   + "\t verticalMove="
                +   "\n\n"+str;
    }
    void color(int r, int c){
        int index =nodeExist(r,c);
        factory.get(index).setColor(Color.ORANGE);
    }

}
