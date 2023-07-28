package com.example.paintio;

import javafx.scene.paint.Color;
import java.util.*;

public abstract class GameLogic {
    private static Boolean running;
    public static ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    public static ArrayList<BotPlayer> botPlayers= new ArrayList<>();
    public static ArrayList<Player> players=new ArrayList<>();
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
    private boolean setBoundaries(ArrayList<PaintNode> line){
        System.out.println("Boundaries");
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

        System.out.println("****************");
        System.out.println("maxR"+maxR+"\tminR"+minR+"\nmaxC"+maxC+"\tminC"+minC);
        System.out.println("****************");

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
                System.out.println("outsider:"+temp.toString());
            else if( temp.getColor()==newClr || temp.getColor()==tailClr)
                System.out.println("Visited:"+temp.toString());
                //!isInside(i,j,player)
            else {
                temp.setColor(newClr);
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
    public boolean isInside(int r, int c,Player player){
        boolean result;
        int count=0;
        for (int j=c ; j<columns.size() ; j++){
            int index= nodeExist(r,j);
            if(index>0){
                PaintNode n=factory.get(index);
                if(player.tail.contains(n))
                    count++;
                if(count==0 && n.getColor()==player.getColor())
                    count++;
            }
        }
        //    System.out.println(count);
        if(count%2==0)
            result=false;
        else
            result=true;

        return result;
    }
    public void conquest(Player player){
        boolean b=setBoundaries(player.tail);
        //    System.out.println(b);
        int count=-1;
        if(b){
            count++;
            Random rand = new Random();
            int r;
            int c;
            int index;
            do{
                if(minC==maxC || minR==maxR){
                    // || count>50
                    r=minR;
                    c=minC;
                    break;
                }
                r = rand.nextInt(maxR-minR);
                r +=minR;
                c = rand.nextInt(maxC-minC);
                c +=minC;
                index=nodeExist(r,c);
                //        System.out.println(factory.get(index).toString());
                //    System.out.println("~inside :"+!isInside(r,c,player)+"\t tail :"+player.tail.contains(factory.get(index)));
                //    System.out.println("result:"+( !isInside(r,c,player) || player.tail.contains(factory.get(index) ) ) );
                //isInside(r,c,player)==false || player.tail.contains(factory.get(index))==true
            }while (!isInside(r,c,player)|| player.tail.contains(factory.get(index)));
            index=nodeExist(r,c);
            System.out.println("finished first part");

            floodFill(index,player.getColor(),player.getTailColor(),player);
            for (PaintNode p:player.tail)
                p.setColor(player.getColor());
            player.tail.clear();
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
