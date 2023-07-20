package com.example.paintio;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Random;

public class BotLogic extends GameLogic implements Runnable{
    public BotPlayer bot ;
    Level level;
    BotLogic(int gridSize, int cellSize,Level level) {
        super(gridSize, cellSize);
        this.level = level;
        int r=randomPlace();
        setBot(r);
    }
    private void  setBot(int index){
        int id = botPlayers.size();
        bot=new BotPlayer(factory.get(index),id,cellSize,Color.CHARTREUSE);
        botPlayers.add(bot);
    //    defult(bot.getColor(),bot.getNode().getRow(),bot.getNode().getColumn());
    }
    private int randomPlace(){
        System.out.println("place");
        int r=-1;
        while(r<0){
            Random rand = new Random();
            r = rand.nextInt(factory.size()-1);
            System.out.println("size "+factory.size());
            int t=factory.get(r).getRow()-(gridSize/2);

            if(Math.abs(t)<7){
                System.out.println("R"+r);
                r=-1;
            } else {
                for(BotPlayer b : botPlayers){
                    t=factory.get(r).getRow()-b.getNode().getRow();

                    if(Math.abs(t)<3){
                        //      System.out.println("R"+r);
                        r=-1;
                    }
                }

            }
        }
        System.out.println("r"+r);
        return r;
    }
    public int move(int direction){
        int i=-1;
        while(i<0){
            int r=bot.getNode().getRow();
            int c=bot.getNode().getColumn();

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
            //
            i =nodeExist(r,c);

            if(i<0){
                direction++;
                direction %=4;
            //    System.out.println("direction"+direction);
            } else if (bot.tail.contains(factory.get(i))){
              //  System.out.println(factory.get(i).toString());
//                i=-1;
                direction ++;
                direction %=4;
            }
        }
        //System.out.println("i"+i);
        return i;
    }

    @Override
    public void run() {
        for(int i =0 ; i<50 ; i++){
            Random rand = new Random();
            int d = rand.nextInt(4);
            Platform.runLater(() -> {
                // Code that modifies the JavaFX scene graph goes here
                int index=move(d);
                bot.setNode(factory.get(index));
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
