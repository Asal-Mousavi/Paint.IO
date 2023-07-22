package com.example.paintio;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlayerLogic extends GameLogic{
    private static PlayerLogic instance=null;
    private Player mainPlayer;
    private PlayerLogic(int gS, int cS) {
        super(gS, cS);
      //  mainPlayer =new Player(0,Color.HOTPINK,gridSize/2,gridSize/2);
        mainPlayer =new Player(0,gridSize,this);
        players.add(mainPlayer);
        initialize();
        defult(mainPlayer,Color.RED,gridSize/2,gridSize/2);
    }
    public static synchronized PlayerLogic getInstance(int gS, int cS){
        if (instance==null){
            instance=new PlayerLogic(gS,cS);
        }
        return instance;
    }
    @Override
    public void kill(){
        int r = mainPlayer.getX();
        int c = mainPlayer.getY();
        for (BotPlayer b: botPlayers){
            for (PaintNode p: b.tail){
                System.out.println(b.getNum()+" "+b.isAlive());
                if(p.getRow()==r && p.getColumn()==c){
                    b.setAlive(false);
                    b.getLogic().die();
                    break;
                }
            }
        }
    }
    @Override
    public void die() {
        System.out.println("Game over!");
        mainPlayer.setAlive(false);
        setRunning(false);
    }
    public Player getMainPlayer(){
        return mainPlayer;
    }
    void color(int r, int c){
        int index =nodeExist(r,c);
        factory.get(index).setColor(Color.ORANGE);
        mainPlayer.tail.add(factory.get(index));
    }
    private void initialize(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                PaintNode node=new PaintNode(cellSize,i,j);
                factory.add(node);
                if(i==0 && !columns.contains(j))
                    columns.add(j);
            }
            if(!rows.contains(i))
                rows.add(i);
        }
    }
    public void generateColumn(int c,boolean direction){
        if(direction){
            //Right move
            c += gridSize-1;
        //    mainPlayer.setY(c-gridSize/2);
        }

        if(!columns.contains(c)){
            for(int i=0 ; i< rows.size() ; i++){
                int row = rows.get(i);
                PaintNode node=new PaintNode(cellSize,row,c);
                factory.add(node);
            }
            columns.add(c);
        }
    }
    public void generateRow(int r,boolean direction){
        if(direction){
            //Down move
            r += gridSize-1;
        }
        if(!rows.contains(r)){
            for(int j=0 ; j< columns.size() ; j++){
                int column=columns.get(j);
                PaintNode node=new PaintNode(cellSize,r,column);
                factory.add(node);
            }
            rows.add(r);
        }
    }
    public void fillGridPane(GridPane g, int r , int c ){
        deduplication();
        g.getChildren().clear();
        for(int k=0 ; k< gridSize ; k++){
            int i=r+k;
            for(int z=0 ; z<gridSize ;z++){
                int j=z+c;
                int index=nodeExist(i,j);
                if(k==gridSize/2 && z==gridSize/2){
                    color(i,j);
                    factory.get(index).setOwner(mainPlayer);
                    mainPlayer.setX(i);
                    mainPlayer.setY(j);
                    kill();
                }
                g.add(factory.get(index),z, k);
            }
        }

        System.out.println("\nx:"+ mainPlayer.getX()+" y:"+ mainPlayer.getY());
    }
}
