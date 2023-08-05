package com.example.paintio;

import javafx.scene.paint.Color;
import java.util.*;

public abstract class GameLogic {
    private static Boolean running;
    public static ArrayList<PaintNode> factory= new ArrayList<>();
    // MainPlayer + BotPlayers
    public static ArrayList<Player> players=new ArrayList<>();
    // Only Bots
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    // Requirements for generating nodes
    int gridSize;
    int cellSize;
    public static ArrayList<Integer> rows = new ArrayList<>();
    public static ArrayList<Integer> columns = new ArrayList<>();
    // Requirements for flood fill
    boolean lastM;
    boolean recentM;
    ArrayList<PaintNode> vertex=new ArrayList<>();
    private int maxR;
    private int maxC;
    private int minR;
    private int minC;

    GameLogic(int gridSize,int cellSize){
        this.gridSize=gridSize;
        this.cellSize=cellSize;
        setRunning(true);
    }
    void setRunning(Boolean running) {
        this.running = running;
    }
    public Boolean getRunning() {
        return running;
    }
    void defaultArea(Player p,Color color,int r,int c){
        for (int i = r ; i < r+4 ; i++) {
            for (int j = c-3 ; j < c+1; j++) {
                int index=nodeExist(i,j);
                if(index>0){
                    factory.get(index).setColor(color);
                    p.territory.add(factory.get(index));
                    factory.get(index).setOwner(p);
                }
            }
        }
        // If there is not enough space for the player place it somewhere else
        if(p.territory.size()<4){
            p.setAlive(false);
            p.getLogic().die();
        }
    }
    public void addVertex(int r,int c){
        int index =nodeExist(r,c);
        // Change direction from vertical move to horizontal move or vice versa
        if (recentM != lastM)
            vertex.add(factory.get(index));
    }
    public int nodeExist(int r,int c){
        for(int i=0 ; i< factory.size() ; i++){
            if((factory.get(i).getColumn() == c) && (factory.get(i).getRow()==r)){
                return i;
            }
        }
        return -1;
    }
    public abstract void kill();
    public abstract void die();

    // Filling a closed area
    private void setBoundaries(ArrayList<PaintNode> line){
        maxR=0;
        maxC=0;
        minR = rows.size()*2;
        minC = columns.size()*2;
        for(PaintNode p : line){

            if(p.getRow()>maxR)
                maxR=p.getRow();

            if(p.getRow()<minR)
                minR=p.getRow();

            if(p.getColumn()>maxC)
                maxC=p.getColumn();

            if(p.getColumn()<minC)
                minC=p.getColumn();
        }
    }
    private boolean rayCasting(int y,int x,boolean right){
        // Determine whether the node is inside the polygon or not
        int count=0;
        for (int i=0 ; i<vertex.size();i++){
            PaintNode v1;
            PaintNode v2;
            if(!right && i== vertex.size()-1)
                break;
            else if(i== vertex.size()-1){
                // right==true
                v1=vertex.get(0);
                v2=vertex.get(i);
            }else {
                v1=vertex.get(i);
                v2=vertex.get(i+1);
            }
            if( v2.getRow() != 0){
                if((y<v1.getRow()) != (y< v2.getRow()) &&
                        x<( v1.getColumn() +((y- v1.getRow())/(v2.getRow())-v1.getRow()) * ((v2.getColumn())- v1.getColumn()) ))
                    count++;
            }
        }
        return count%2==1;
    }
    private int findBase(Player player,boolean right){
        // Find a starting point for flood fill that is inside polygon and not on its border
        int index;
        for (int i=minR ; i<=maxR ; i++)
            for (int j=minC ; j<=maxC ; j++){
                index=nodeExist(i,j);

                if( factory.get(index).getColor()==player.getTailColor()
                        || factory.get(index).getColor()==player.getColor() )
                    // Tail or colored
                    continue;
                else if(rayCasting(i,j,right))
                    return index;
            }
        return -1;
    }
    private void floodFill(int index, Color newClr, Color tailClr,Player player){
        // Color all the inside nodes using BFS algorithm
        Color currentColor=factory.get(index).getColor();
        if(currentColor == newClr)
            return;
        Queue<PaintNode> queue = new LinkedList<>();
        queue.offer(factory.get(index));
        while (!queue.isEmpty()){
            PaintNode temp= queue.poll();
            int i=temp.getRow();
            int j=temp.getColumn();
            if(i<minR || i>maxR || j<minC || j>maxC )
                continue;
            // Outside the border
            else if( temp.getColor()==newClr || temp.getColor()==tailClr)
                continue;
            // Visited
            else {
                temp.setColor(newClr);
                temp.setOwner(player);
                player.territory.add(temp);

                // Add neighbors to the queue
                index=nodeExist(i+1,j);
                if(index>-1)
                    queue.offer(factory.get(index));
                index=nodeExist(i-1,j);
                if(index>-1)
                    queue.offer(factory.get(index));
                index=nodeExist(i,j+1);
                if(index>-1)
                    queue.offer(factory.get(index));
                index=nodeExist(i,j-1);
                if(index>-1)
                    queue.offer(factory.get(index));
            }
        }

    }
    public void conquest(Player player,boolean right){
        setBoundaries(player.tail);
        int index=0;
        while (index != -1){
            index=findBase(player,right);
            if(index>=0)
                floodFill(index,player.getColor(),player.getTailColor(),player);
        }
        // Color the border
        for (PaintNode p:player.tail){
            int t=nodeExist(p.getRow(),p.getColumn());
            factory.get(t).setColor(player.getColor());
            factory.get(t).isTaken=false;
            factory.get(t).setOwner(player);
        }
        player.territory.addAll(player.tail);
        player.tail.clear();
        vertex.clear();
    }
    public void deduplication(ArrayList<PaintNode> arr){
        ArrayList<PaintNode> unique = new ArrayList<PaintNode>(arr);
        for(PaintNode p: arr){
            if( !unique.contains(p) ){
                unique.add(p);
            }
        }
        arr.clear();
        arr.addAll(unique);
        unique.clear();
    }

}
