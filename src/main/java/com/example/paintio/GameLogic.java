package com.example.paintio;

import javafx.scene.paint.Color;

import java.util.*;

public abstract class GameLogic {
    private static Boolean running;
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    public  static ArrayList<Player> players=new ArrayList<>();
    ArrayList<Integer> rows = new ArrayList<>();
    ArrayList<Integer> columns = new ArrayList<>();
    private int maxR;
    private int maxC;
    private int minR;
    private int minC;
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

    //color closed area
    private void setBoundaries(ArrayList<PaintNode> line){
        maxR=0;
        maxC=0;
        minR = rows.size();
        minC = columns.size();

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
        /*
        for (Integer r: rows){
            if(r>maxR)
                maxR=r;
            if (r<minR)
                minR=r;
        }
        for (Integer c:columns){
            if (c>maxC)
                maxC=c;
            if (c<minC)
                minC=c;
        }

         */
        System.out.println("****************");
        System.out.println("maxR"+maxR+"\tminR"+minR+"\nmaxC"+maxC+"\tminC"+minC);
        System.out.println("****************");
    }
    public void floodFill(int i,int j, Color newClr,Color tailClr,ArrayList<PaintNode> line){
        setBoundaries(line);
        int index=nodeExist(i,j);
        Color currentColor=factory.get(index).getColor();
        if(currentColor == newClr)
            return;
        Queue<PaintNode> nodeLinkedList = new LinkedList<>();
        nodeLinkedList.offer(factory.get(index));
        while (!nodeLinkedList.isEmpty()){
            PaintNode temp= nodeLinkedList.poll();
            i=temp.getRow();
            j=temp.getColumn();
            if(i<minR || i>maxR || j<minC || j>maxC )
                System.out.println("outsider:"+temp.toString());
            else if( temp.getColor()==newClr || temp.getColor()==tailClr)
                System.out.println("Visited:"+temp.toString());
            else {
                temp.setColor(newClr);
                index=nodeExist(i+1,j);
                nodeLinkedList.offer(factory.get(index));
                index=nodeExist(i-1,j);
                nodeLinkedList.offer(factory.get(index));
                index=nodeExist(i,j+1);
                nodeLinkedList.offer(factory.get(index));
                index=nodeExist(i,j-1);
                nodeLinkedList.offer(factory.get(index));
            }
        }
        for (PaintNode p:line)
            p.setColor(newClr);
    }
    private void floodFill(int index, Color newClr, Color tailClr){
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
                System.out.println("outsider:"+temp.toString());
            else if( temp.getColor()==newClr || temp.getColor()==tailClr)
                System.out.println("Visited:"+temp.toString());
            else {
                temp.setColor(newClr);
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
    public void conquest(Player player){
        setBoundaries(player.tail);
        int r=maxR-1;
        int c=maxC-1;
        int index=nodeExist(r,c);
        while (player.tail.contains(factory.get(index))){
            r--;
            c--;
            index=nodeExist(r,c);
        }
        System.out.println(factory.get(index).toString());
        floodFill(index,Color.RED,Color.ORANGE);
        for (PaintNode p:player.tail)
            p.setColor(Color.RED);
       // player.tail.clear();
    }


}
