package com.example.paintio;

import javafx.scene.paint.Color;
import java.util.ArrayList;
public class ColorCollection {
    private static ColorCollection instance=null;
    private ArrayList<Color> territoryColor=new ArrayList<>();
    private ArrayList<Color> tailColor=new ArrayList<>();
    private ColorCollection(){
        // MainPlayer
        territoryColor.add(Color.GOLD);
        tailColor.add(Color.YELLOW);
        // Bot1
        territoryColor.add(Color.MEDIUMPURPLE);
        tailColor.add(Color.ORCHID);
        // Bot2
        territoryColor.add(Color.STEELBLUE);
        tailColor.add(Color.POWDERBLUE);
        // Bot3
        territoryColor.add(Color.SEAGREEN);
        tailColor.add(Color.LIGHTGREEN);
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
