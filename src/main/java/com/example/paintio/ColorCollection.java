package com.example.paintio;

import javafx.scene.paint.Color;
import java.util.ArrayList;
public class ColorCollection {
    private static ColorCollection instance=null;
    private ArrayList<Color> territoryColor=new ArrayList<>();
    private ArrayList<Color> tailColor=new ArrayList<>();
    private ColorCollection(){

        territoryColor.add(Color.GOLD);
        tailColor.add(Color.YELLOW);

        territoryColor.add(Color.DARKBLUE);
        tailColor.add(Color.BLUE);

        territoryColor.add(Color.GREEN);
        tailColor.add(Color.CHARTREUSE);

        territoryColor.add(Color.RED);
        tailColor.add(Color.ORANGE);

        territoryColor.add(Color.BROWN);
        tailColor.add(Color.SADDLEBROWN);

        territoryColor.add(Color.PURPLE);
        tailColor.add(Color.MEDIUMPURPLE);
    }
    public static synchronized ColorCollection getInstance(){
        if (instance==null){
            instance=new ColorCollection();
        }
        return instance;
    }
    public Color getTailColor(int num){
        return tailColor.get(num);
    }
    public Color getTerritoryColor(int num){
        return territoryColor.get(num);
    }
}
