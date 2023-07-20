package com.example.paintio;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player extends StackPane {
    public ArrayList<PaintNode> tail = new ArrayList<>();
    public ArrayList<PaintNode> owned = new ArrayList<>();
    private Color color;
    private int num;
    public boolean isAlive;

    Player(int id,Color clr) {
        this.num = id;
        this.color = clr;
        isAlive = true;
    }
}
