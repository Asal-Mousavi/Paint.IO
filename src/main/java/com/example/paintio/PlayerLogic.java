package com.example.paintio;

import javafx.scene.layout.GridPane;

public class PlayerLogic extends GameLogic{
    private static PlayerLogic instance=null;
    private final Player mainPlayer;
    private PlayerLogic(int gS, int cS) {
        super(gS, cS);
        mainPlayer =new Player(0,gridSize,this);
        players.add(mainPlayer);
        initialize();
        defaultArea(mainPlayer,mainPlayer.getColor(),gridSize/2,gridSize/2);
        recentM=true;
    }
    public static synchronized PlayerLogic getInstance(int gS, int cS){
        if (instance==null){
            instance=new PlayerLogic(gS,cS);
        }
        return instance;
    }
    public Player getMainPlayer(){
        return mainPlayer;
    }

    // Generate node
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
        lastM=recentM;
        recentM=false;
        if(direction){
            //Right move
            c += gridSize-1;
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
        lastM=recentM;
        recentM=true;
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
    void color(int r, int c) {
        int index = nodeExist(r, c);
        if (factory.get(index).getColor() == mainPlayer.getColor()) {
            // Closed area
            vertex.add(factory.get(index));
            boolean right=false;
            // Territory is on the right or not
            int i=0;
            if(vertex.size()>1){
                if(vertex.size()>2 && vertex.get(0)==vertex.get(1))
                    // Duplicated vertex in the beginning
                    i++;
                if(vertex.get(i).getColumn()>vertex.get(i+1).getColumn() )
                    // second vertex is on the left of first vertex
                    right=true;
            }
            conquest(mainPlayer,right);
        }else {
            if(vertex.size()==0){
                int h=nodeExist(mainPlayer.getX(),mainPlayer.getY());
                vertex.add(factory.get(h));
            }
            factory.get(index).setColor(mainPlayer.getTailColor());
            factory.get(index).isTaken=true;
            mainPlayer.tail.add(factory.get(index));
        }
    }

    // Arrange nodes on the gridPane
    public void fillGridPane(GridPane g, int r , int c ){
        deduplication(factory);
        g.getChildren().clear();
        for(int k=0 ; k<gridSize ; k++){
            int i=r+k;
            for(int z=0 ; z<gridSize ;z++){
                int j=z+c;
                int index=nodeExist(i,j);
                if(k==gridSize/2 && z==gridSize/2){
                    mainPlayer.setX(i);
                    mainPlayer.setY(j);
                    kill();
                    color(i,j);
                    addVertex(mainPlayer.getX(),mainPlayer.getY());
                }
                g.add(factory.get(index),z, k);
            }
        }
    }
    @Override
    public void kill(){
        int r = mainPlayer.getX();
        int c = mainPlayer.getY();
        for (BotPlayer b: botPlayers){
            for (PaintNode p: b.tail){
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
        mainPlayer.setAlive(false);
        setRunning(false);
    }
}
