package com.example.paintio;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Random;

public class BotLogic extends GameLogic implements Runnable{
    private BotPlayer bot ;
    BotLogic(int gridSize, int cellSize,BotPlayer bot ) {
        super(gridSize, cellSize);
        this.bot=bot;
        System.out.println(bot.getNum());
        defult(bot.getColor(),18);
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
                System.out.println(factory.get(i).toString());
                i=-1;
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
