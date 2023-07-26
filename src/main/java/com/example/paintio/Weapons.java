package com.example.paintio;

import java.util.Timer;
import java.util.TimerTask;

public class Weapons {
    private int bullet=15;
    private boolean shoot=true;
    private PlayerLogic logic ;
    private Player mainPlayer;
    Weapons(PlayerLogic logic){
        this.logic=logic;
        mainPlayer=logic.getMainPlayer();
    }
    public void weaponA(int direction){
        bullet--;
        if(bullet>0){
            int r= mainPlayer.getX();
            int c= mainPlayer.getY();

            switch (direction){
                case 0:
                    c +=7;
                    area(r,c);
                    break;
                case 1:
                    r -=7;
                    area(r,c);
                    break;
                case 2:
                    c -=7;
                    area(r,c);
                    break;
                case 3:
                    r +=7;
                    area(r,c);
                    break;
            }
        }
    }
    public void weaponB(int direction) {
        if(shoot){
            int r = mainPlayer.getX();
            int c = mainPlayer.getY();

            switch (direction) {
                case 0:
                    c++;
                    break;
                case 1:
                    r--;
                    break;
                case 2:
                    c--;
                    break;
                case 3:
                    r++;
                    break;
            }
            int index = logic.nodeExist(r, c);
            if (logic.factory.get(index).seated) {
                for (BotPlayer b : logic.botPlayers) {
                    if (b.getNode() == logic.factory.get(index))
                        b.getLogic().die();
                }
            }
            shoot=false;
            sleep();
        }
    }
    private void sleep(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                shoot=true;
            }
        };
        timer.schedule(task, 3000);
    }
    private void check(int r, int c){
        int index = logic.nodeExist(r,c);
        if(!logic.factory.get(index).isTaken)
            logic.factory.get(index).setColor(mainPlayer.getColor());
        else if (logic.factory.get(index).seated){
            for (BotPlayer b: logic.botPlayers){
                if (b.getNode()==logic.factory.get(index))
                    b.getLogic().die();
            }
        }
    }
    private void area(int r,int c){
        for (int i=r-1;i<=r+1;i++){
            for (int j=c-1;j<=c+1;j++){
                check(i,j);
            }
        }
    }
}
