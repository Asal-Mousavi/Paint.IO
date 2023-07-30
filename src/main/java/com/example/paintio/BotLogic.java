package com.example.paintio;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;

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
        setBot(r);
    }
    private void  setBot(int index){
        int id = botPlayers.size()+1;
        bot=new BotPlayer(factory.get(index),id,cellSize,this);
        botPlayers.add(bot);
        players.add(bot);
        defult(bot,bot.getColor(),bot.getX(),bot.getY());
    }
    private int randomPlace(){
        System.out.println("place");
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
                //bot.tail.contains(factory.get(i))
                direction++;
                direction %=4;
            }else if( bot.territory.contains(factory.get(i)) ){
                i=-1;
                direction++;
                direction %=4;
            }
        }
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
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void die() {
    //    bot.setAlive(false);
        bot.getNode().removePlayer(bot);
        bot.territory.addAll(bot.tail);
        for(PaintNode p: bot.territory){
            int r=p.getRow();
            int c=p.getColumn();
            int index=nodeExist(r,c);
            if(factory.get(index).getOwner()==bot)
                factory.get(index).setColor(factory.get(index).getDefualtColor());
        }
        bot.territory.clear();
        bot.tail.clear();
    }
    @Override
    public void run() {
        while(getRunning()){
            int d;
            if(time%3==0){
                if(level==Level.EASY){
                    Random rand = new Random();
                    d = rand.nextInt(4);
                } else {
                    d=lastMove;
                }
            } else {
                d = lastMove;
            }
            if(bot.isAlive()){
                Platform.runLater(() -> {
                        kill();
                        int index=move(d);
                        bot.setNode(factory.get(index));
                        factory.get(index).setOwner(bot);
                        lastMove=d;
                        time++;
                });
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    reborn();

                });
            }
            try {
                Thread.sleep(botSpeed);
                System.out.println(botSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
