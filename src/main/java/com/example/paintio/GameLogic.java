package com.example.paintio;

import javafx.scene.paint.Color;
import java.util.*;

public abstract class GameLogic {
    private static Boolean running;
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    public static ArrayList<Player> players=new ArrayList<>();
    public static ArrayList<Integer> rows = new ArrayList<>();
    public static ArrayList<Integer> columns = new ArrayList<>();
    ArrayList<PaintNode> vertex=new ArrayList<>();
    private int maxR;
    private int maxC;
    private int minR;
    private int minC;
    boolean lastM;
    boolean recentM;
    int gridSize;
    int cellSize;
    GameLogic(int gridSize,int cellSize){
        this.gridSize=gridSize;
        this.cellSize=cellSize;
        setRunning(true);
    }
    void defult(Player p,Color color,int r,int c){
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
        if(p.territory.size()<4){
            p.setAlive(false);
            p.getLogic().die();
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
    public void setRunning(Boolean running) {
        this.running = running;
        System.out.println(this.running);
    }
    public Boolean getRunning() {
        return running;
    }
    public abstract void kill();
    public abstract void die();
    private boolean setBoundaries(ArrayList<PaintNode> line){
        maxR=0;
        maxC=0;
        minR = rows.size()*2;
        minC = columns.size()*2;

    //    System.out.println("\n$$$$$$$$ \n tail");
        for(PaintNode p : line){
    //        System.out.println(p.toString());
            if(p.getRow()>maxR)
                maxR=p.getRow();

            if(p.getRow()<minR)
                minR=p.getRow();

            if(p.getColumn()>maxC)
                maxC=p.getColumn();

            if(p.getColumn()<minC)
                minC=p.getColumn();
        }
/*
        System.out.println("\n****************");
        System.out.println("maxR"+maxR+"\tminR"+minR+"\nmaxC"+maxC+"\tminC"+minC);
        System.out.println("****************");

 */
        if (minR>maxR || minC>maxC)
            return false;
        else
            return true;
    }
    private void floodFill(int index, Color newClr, Color tailClr,Player player){
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
            //    System.out.println("outsider:"+temp.toString()); ;
            else if( temp.getColor()==newClr || temp.getColor()==tailClr || temp.isTaken)
                continue;
            //    System.out.println("Visited:"+temp.toString());
            else {
                temp.setColor(newClr);
                temp.setOwner(player);
                player.territory.add(temp);
                index=nodeExist(i+1,j);
                queue.offer(factory.get(index));
                index=nodeExist(i-1,j);
                queue.offer(factory.get(index));
                index=nodeExist(i,j+1);
                queue.offer(factory.get(index));
                index=nodeExist(i,j-1);
                queue.offer(factory.get(index));
            }
        }
    }
    public void conquest(Player player,boolean right){
/*
        System.out.println("vertex");
        for (PaintNode p : player.getLogic().vertex)
            System.out.println(p.toString());
        System.out.println("*************");

 */
        boolean b=setBoundaries(player.tail);

            int index=0;
            while (index != -1){
                index=findBase(player,right);
                if(index>=0)
                    floodFill(index,player.getColor(),player.getTailColor(),player);
            }
            for (PaintNode p:player.tail){
                p.setColor(player.getColor());
                p.isTaken=false;
                p.setOwner(player);
            }
            player.territory.addAll(player.tail);
            player.tail.clear();
            vertex.clear();
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
    public void addVertex(int r,int c){
        int index =nodeExist(r,c);
        if (recentM != lastM)
            vertex.add(factory.get(index));
    }
    public boolean rayCasting(int y,int x,boolean right){
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
        int index;
        for (int i=minR ; i<=maxR ; i++)
            for (int j=minC ; j<=maxC ; j++){
                index=nodeExist(i,j);

                if( factory.get(index).getColor()==player.getTailColor()
                        || factory.get(index).getColor()==player.getColor() || factory.get(index).isTaken)
                    //     System.out.println("tail or colored");
                    continue;
                else if(rayCasting(i,j,right)){
                //    System.out.println("Base:"+factory.get(index));
                    return index;
                }
            }
        return -1;
    }

}
