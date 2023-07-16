package com.example.paintio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class NodeFactory {

    public ArrayList<PaintNode> factory= new ArrayList<PaintNode>();
    private ArrayList<Integer> rows = new ArrayList<>();
    private ArrayList<Integer> columns = new ArrayList<>();

    private int gridSize;
    private int cellSize;

    NodeFactory(int gridSize,int cellSize){

        this.gridSize=gridSize;
        this.cellSize=cellSize;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                PaintNode node;
                if((i+j)%2==0){
                    node=new PaintNode(cellSize,Color.WHITE,i,j);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,i,j);
                }
                factory.add(node);
                if(i==0)
                    columns.add(j);
            }
            rows.add(i);
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

    private boolean check(int a, ArrayList<Integer> ar ){
        for(int i=0 ; i< ar.size() ; i++){
            if(ar.get(i)==a)
                return false;
        }
        return true;
    }

    public void generateColumn(int c,boolean direction){
        if(direction){
            //Right move
            c += gridSize-1;
        }
        if( check(c,columns) ){
            for(int i=0 ; i< rows.size() ; i++){
                int row = rows.get(i);
                PaintNode node;
                if((row+c)%2==0){
                    node=new PaintNode(cellSize,Color.WHITE,row,c);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,row,c);
                }
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
        if( check(r,rows) ){
            for(int j=0 ; j< columns.size() ; j++){
                int column=columns.get(j);
                PaintNode node;
                if((r+column)%2==0){
                    node=new PaintNode(cellSize,Color.WHITE,r,column);
                } else {
                    node=new PaintNode(cellSize,Color.GRAY,r,column);
                }
                factory.add(node);
            }
            rows.add(r);
        }
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

    public ArrayList<PaintNode> findRow(int r){
        ArrayList<PaintNode> tR= new ArrayList<PaintNode>();

        for(PaintNode p: factory){
            if(p.getRow()==r)
                tR.add(p);
        }
        return tR;
    }

    private ArrayList<PaintNode> orderColumn(ArrayList<PaintNode> o,int c){

        for(int i=0 ; i< o.size() ; i++){
            Collections.sort(o, new Comparator<PaintNode>() {
                public int compare(PaintNode p1, PaintNode p2) {
                    return p1.getColumn() - p2.getColumn();
                }
            });
        }

        ArrayList<PaintNode> temp = new ArrayList<PaintNode>();

        for(int i=0 ; i< o.size() ; i++){
            if(o.get(i).getColumn() >= c){
                temp.add(o.get(i));
            }
        }
        return temp;
    }

    private void color(int r,int c){
        int index =nodeExist(r,c);
        factory.get(index).setColor(Color.ORANGE);
    }
    public void fillGridPane(GridPane g, int r , int c ){
        deduplication();
        g.getChildren().clear();
        ArrayList<PaintNode> tempRow=new ArrayList<PaintNode>();
        for(int k=0 ; k< gridSize ; k++){
            int i=r+k;
            tempRow = findRow(i);
            tempRow=orderColumn(tempRow, c);
            for(int z=0 ; z<gridSize ;z++){
                g.add(tempRow.get(z),z, k);
                if(k==gridSize/2 && z==gridSize/2){
                    color(tempRow.get(z).getRow(),tempRow.get(z).getColumn());
                }
            }
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