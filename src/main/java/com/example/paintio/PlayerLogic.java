package com.example.paintio;

import javafx.scene.layout.GridPane;

public class PlayerLogic extends GameLogic{
    private static PlayerLogic instance=null;
    private Player mainPlayer;
    private PlayerLogic(int gS, int cS) {
        super(gS, cS);
      //  mainPlayer =new Player(0,Color.HOTPINK,gridSize/2,gridSize/2);
        mainPlayer =new Player(0,gridSize,this);
        players.add(mainPlayer);
        initialize();
        defult(mainPlayer,mainPlayer.getColor(),gridSize/2,gridSize/2);
    //    lastM=true;
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
        System.out.println("Game over!");
        mainPlayer.setAlive(false);
        setRunning(false);
    }
    void color(int r, int c) {
        int index = nodeExist(r, c);
        if (factory.get(index).getColor() == mainPlayer.getColor()) {
            vertex.add(factory.get(index));

            boolean right=false;
       //     if(mainPlayer.getY()<c || c<8)
            int i=0;
            if(vertex.size()>1){
                if(vertex.size()>2 && vertex.get(0)==vertex.get(1))
                    i++;
                if(vertex.get(i).getColumn()>vertex.get(i+1).getColumn() )
                    right=true;
            }
            System.out.println("Right: "+right);
            conquest(mainPlayer,right);
        //    findBase(mainPlayer,right);
        //    paintArea(mainPlayer,right);
    }else {
            if(vertex.size()==0){
                int h=nodeExist(mainPlayer.getX(),mainPlayer.getY());
                vertex.add(factory.get(h));
            }
            factory.get(index).setColor(mainPlayer.getTailColor());
            mainPlayer.tail.add(factory.get(index));
        }
    }
    public void fillGridPane(GridPane g, int r , int c ){
        deduplication(factory);
        g.getChildren().clear();
        for(int k=0 ; k< gridSize ; k++){
            int i=r+k;
            for(int z=0 ; z<gridSize ;z++){
                int j=z+c;
                int index=nodeExist(i,j);
                if(k==gridSize/2 && z==gridSize/2){
                    color(i,j);
                    addVertex(mainPlayer.getX(),mainPlayer.getY());
                    factory.get(index).setOwner(mainPlayer);
                    mainPlayer.setX(i);
                    mainPlayer.setY(j);
                    kill();
                }
                g.add(factory.get(index),z, k);
            }
        }
    //    lastM=recentM;
    }


}
