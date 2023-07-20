package com.example.paintio;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class GameLogic {
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    ArrayList<Integer> rows = new ArrayList<>();
    ArrayList<Integer> columns = new ArrayList<>();
    int gridSize;
    int cellSize;

    GameLogic(int gridSize,int cellSize){

        this.gridSize=gridSize;
        this.cellSize=cellSize;
    }

    void defult(Color color,int r,int c){

        for (int i = r+1 ; i < r+4 ; i++) {
            for (int j = c-3 ; j < c+1; j++) {
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
    void color(int r, int c){
        int index =nodeExist(r,c);
        factory.get(index).setColor(Color.ORANGE);
    }
    public void kill(int r , int c){
        System.out.println("\n"+ r+" "+c);
        System.out.println("*************************");
        for (BotPlayer b: botPlayers){
   //         System.out.println("\nbut "+b);
            for (PaintNode p: b.tail){
                System.out.print(p.toString());
                if(p.getRow()==r && p.getColumn()==c){
                    b.isAlive=false;
                    System.out.print(false);
           //         System.out.println(b.getNode().toString());
                }
            }
  //          System.out.println("*************************");
        }
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

}
