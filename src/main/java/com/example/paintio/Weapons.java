package com.example.paintio;

public class Weapons {
    private int bullet=10;
    private PlayerLogic logic ;
    private Player mainPlayer;
    Weapons(int gridSize,int cellSize){
        logic=PlayerLogic.getInstance(gridSize,cellSize);
        mainPlayer=logic.getMainPlayer();
    }
    public void weaponA(int direction){
        bullet--;
        while (bullet>0){
            int r= mainPlayer.getX();
            int c= mainPlayer.getY();

            switch (direction){
                case 0:
                    for (int i=c;i<5+c;i++){
                        int index=logic.nodeExist(r,i);
                        check(index);
                    }
                    break;
                case 1:
                    for (int i=r;i>r-5;i--){
                        int index=logic.nodeExist(i,c);
                        check(index);
                    }
                    break;
                case 2:
                    for (int i=c;i>c-5;i--){
                        int index=logic.nodeExist(r,i);
                        check(index);
                    }
                    break;
                case 3:
                    for (int i=r;i<5+r;i++){
                        int index=logic.nodeExist(i,c);
                        check(index);
                    }
                    break;
            }
        }
    }
    private void check(int index){
        if(!logic.factory.get(index).isTaken)
            logic.factory.get(index).setColor(mainPlayer.getColor());
        else if (logic.factory.get(index).seated){
            for (BotPlayer b: logic.botPlayers){
                if (b.getNode()==logic.factory.get(index))
                    b.getLogic().die();
            }
        }
    }

}
