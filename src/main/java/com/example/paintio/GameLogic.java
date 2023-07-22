package com.example.paintio;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GameLogic {
    private static Boolean running;
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    public  static ArrayList<Player> players=new ArrayList<>();
    ArrayList<Integer> rows = new ArrayList<>();
    ArrayList<Integer> columns = new ArrayList<>();
    int gridSize;
    int cellSize;
    GameLogic(int gridSize,int cellSize){
        this.gridSize=gridSize;
        this.cellSize=cellSize;
        setRunning(true);
    }
    void defult(Player p,Color color,int r,int c){
        for (int i = r+1 ; i < r+4 ; i++) {
            for (int j = c-3 ; j < c+1; j++) {
                int index=nodeExist(i,j);
                if(index>0){
                    factory.get(index).setOwner(p);
                    factory.get(index).setColor(color);
                    p.territory.add(factory.get(index));
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

    public void setRunning(Boolean running) {
        this.running = running;
        System.out.println(this.running);
    }

    public Boolean getRunning() {
        return running;
    }

    public abstract void kill();
    public abstract void die();
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
