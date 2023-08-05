package com.example.paintio;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;

public class BotLogic extends GameLogic implements Runnable{
    public BotPlayer bot;
    Level level;
    public int botSpeed;
    private int time=0;
    private int lastMove;
    BotLogic(int gridSize, int cellSize,Level level) {
        super(gridSize, cellSize);
        this.level = level;
        int r=randomPlace();
      //  int r= nodeExist(0,23);
        setBot(r);
    //    recentM=true;
    }
    private void  setBot(int index){
        int id = botPlayers.size()+1;
        bot=new BotPlayer(factory.get(index),id,cellSize,this);
        botPlayers.add(bot);
        players.add(bot);
        defult(bot,bot.getColor(),bot.getX(),bot.getY());
    }
    private int randomPlace(){
    //    System.out.println("place");
        int r=-1;
        while(r<0){
            Random rand = new Random();
            r = rand.nextInt(factory.size());
            for(Player b : players){
                int t=factory.get(r).getRow()-b.getX();
                if(Math.abs(t)<5 || factory.get(r).isTaken ){
                    r=-1;
                    break;
                }
            }
        }
        return r;
    }
    private int easyDirection(){
    //    System.out.println("lastMove : "+lastMove);
        int dr=-1;
        while (dr<0){
            Random rand = new Random();
            dr = rand.nextInt(4);
            // Avoid turning back
            if (dr!=lastMove && dr%2==lastMove%2)
                dr=-1;
    //        System.out.println("dr : "+dr);
        }
    //    System.out.println("Direction : "+dr);
        return dr;
    }
    public int move(int direction){
        int i=-1;
        while(i<0){
            int r=bot.getX();
            int c=bot.getY();

            if(direction==0){
                // Right
                c++;
            }
            if(direction==1){
                // Up
                r--;
            }
            if(direction==2){
                // Left
                c--;
            }
            if(direction==3){
                // Down
                r++;
            }
            i =nodeExist(r,c);
            if(i<0){
                direction++;
                direction %=4;
            //    System.out.println("i: "+i+"  d :"+direction);
            }
        }
        lastM=recentM;
        recentM=(direction%2==1);
        return i;
    }
    private void reborn(){
        int place=randomPlace();
        bot.setNode(factory.get(place));
        bot.setAlive(true);
        defult(bot,bot.getColor(),bot.getNode().getRow(),bot.getNode().getColumn());
    }
    @Override
    public void kill() {
        int r= bot.getX();
        int c= bot.getY();
        for (Player b: players){
            if(!b.equals(bot)){
                for (PaintNode p: b.tail){
                    if(p.getRow()==r && p.getColumn()==c){
                        b.getLogic().die();
                        b.setAlive(false);
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void die() {
        System.out.println("DIE "+bot.getNode().toString() );
        bot.getNode().removePlayer(bot);
        bot.territory.addAll(bot.tail);
        /*
        System.out.println("\n^^^^^^^^^ territory ");
        for(PaintNode p: bot.territory)
            System.out.print(p.toString());
        System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
         */
        for(PaintNode p: bot.territory){
            int r=p.getRow();
            int c=p.getColumn();
            int index=nodeExist(r,c);
            //factory.get(index).getOwner()==bot
            if( factory.get(index).getColor()==bot.getColor() || factory.get(index).getOwner()==null){
            //    System.out.println(factory.get(index).getOwner().getNum());
                factory.get(index).setColor(factory.get(index).getDefualtColor());
                factory.get(index).setOwner(null);
            } else if(factory.get(index).getColor()==bot.getTailColor()){
                System.out.println("node : "+ factory.get(index) +"  Owner : "+factory.get(index).getOwner().getNum());
                factory.get(index).setColor(factory.get(index).getOwner().getColor());
                factory.get(index).isTaken=false;
            }
        }
        bot.territory.clear();
        bot.tail.clear();
    }
    @Override
    public void run() {
        ArrayList<PaintNode> neighbor = new ArrayList<>();
        PaintNode startPoint = null;
        while(getRunning()){
            int d;
            if(bot.isAlive()){
            //    System.out.println("time :"+time);
                if(time%3==0){
                    if(level==Level.EASY){
                        d=easyDirection();
                    } else {
                        if(time%9==0){
                            vertex.add(bot.getNode());
                   //         System.out.println();
                            startPoint=bot.getNode();
                            neighbor=neighbor(bot.getX(),bot.getY());
                        }
                        d=hardDirection(neighbor,startPoint,time%9);
                        lastMove=d;
                    }
                    time++;
                } else {
               //     System.out.println("LM"+lastMove);
                    d = lastMove;
                    time++;
                }
                Platform.runLater(() -> {
                        kill();
                        int index=move(d);
                        bot.setNode(factory.get(index));
                        lastMove=d;
                });
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    reborn();
                    time=0;
                });
            }
            try {
                Thread.sleep(botSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<PaintNode> neighbor(int r, int c){
        ArrayList<PaintNode> neighbors = new ArrayList<>();
        int index;
        // Right
        index=nodeExist(r,c+1);
        if(index>0)
            neighbors.add(factory.get(index));
        // Up
        index=nodeExist(r-1,c);
        if(index>0)
            neighbors.add(factory.get(index));
        // Left
        index=nodeExist(r,c-1);
        if(index>0)
            neighbors.add(factory.get(index));
        // Down
        index=nodeExist(r+1,c);
        if(index>0)
            neighbors.add(factory.get(index));
/*
        System.out.println(r+"  "+c);
        for (PaintNode n:neighbors)
            System.out.print(n.toString()+"\t");
        System.out.println();
 */
        return neighbors;
    }
    private int hardDirection(ArrayList<PaintNode> neighbors, PaintNode startPoint,int t){
        int dr = -2;

        if(t==0)
            dr=firstMove(neighbors,startPoint);
        else if (t==3)
            dr=secondMove(neighbors,startPoint);
        else if (t==6 || dr==-1){
        //    System.out.println("thirdMove");
            if(lastMove%2==0){ // move up or down
                if(bot.getX()<startPoint.getRow())
                    dr=3;
                else if(bot.getX()>startPoint.getRow())
                    dr=1;
            }else { // move right or left
                if (bot.getY()<startPoint.getColumn())
                    dr=0;
                else if(bot.getY()>startPoint.getColumn())
                    dr=2;
            }
        }

        //System.out.println("dr :"+dr);
        return dr;
    }
    private double distance(PaintNode p1,PaintNode p2){
        double rowDiff = p1.getRow() - p2.getRow();
        rowDiff *= rowDiff;
        double colDiff = p1.getColumn() - p2.getColumn();
        colDiff *= colDiff;
        double distance = Math.sqrt(rowDiff+colDiff);
        return distance;
    }
    private int firstMove(ArrayList<PaintNode> neighbors,PaintNode startPoint){
    //    System.out.println("firstMove");
        int i=0;
        int[] possiblePath=new int[4];
        double[] distance=new double[4];
        for (int j=0; j<neighbors.size() ;j++){
            if(neighbors.get(j).getColor() != bot.getColor()){
                possiblePath[i]=j;
                int index=nodeExist(players.get(0).getX(),players.get(0).getY());
                distance[i]=distance(neighbors.get(j),factory.get(index));
                i++;
            }
        }
        //compare
        int min=0;
        if(i==0)
            return -1;
        else         if(i>1)
            if(distance[1]<distance[0])
                min=1;
        PaintNode closest=neighbors.get(possiblePath[min]);

        return diraction(closest,startPoint);
    }
    private int secondMove(ArrayList<PaintNode> neighbors, PaintNode startPoint) {
    //    System.out.println("secondMove");
        PaintNode closest = null;
        int i = 0;
        int dr = 0;
        int[] possiblePath = new int[4];
        double[] distance = new double[4];
        ArrayList<PaintNode> colored = new ArrayList<>();

        for (int j = 0; j < neighbors.size(); j++) {
            dr=diraction(neighbors.get(j),startPoint);
            if (dr % 2 != lastMove % 2)
                if (neighbors.get(j).getColor() == bot.getColor()) {
                    colored.add(neighbors.get(j));
                    possiblePath[i] = j;
                    i++;
                }
        }
        int min = 0;
        if(colored.size()==0)
            return -1;
        else if (colored.size() > 1) {
            int index = nodeExist(players.get(0).getX(), players.get(0).getY());
            distance[0] = distance(colored.get(0), factory.get(index));
            distance[1] = distance(colored.get(1), factory.get(index));
            //compare
            if(i>1)
                if (distance[1] < distance[0])
                    min = 1;
        }

        closest = colored.get(min);
        dr=diraction(closest,startPoint);
        return dr;
    }
    private int diraction(PaintNode p1,PaintNode p2){
        int dr=0;
        if(p1.getColumn()>p2.getColumn())
            dr=0;
        else if(p1.getColumn()<p2.getColumn())
            dr=2;
        else if(p1.getRow()<p2.getRow())
            dr=1;
        else if(p1.getRow()>p2.getRow())
            dr=3;
        return  dr;
    }
    
}
