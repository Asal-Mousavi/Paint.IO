package com.example.paintio;

import java.util.ArrayList;

public class Node2DArrayList<T> {
    private ArrayList<ArrayList<T>> list;

    public Node2DArrayList() {
        this.list = new ArrayList<ArrayList<T>>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void addRow(ArrayList<T> row) {
        list.add(row);
    }

    public void addColumn(ArrayList<T> column) {
        for (int i = 0; i < column.size(); i++) {
            if (i < list.size()) {
                list.get(i).add(column.get(i));
            } else {
                ArrayList<T> newRow = new ArrayList<T>();
                newRow.add(column.get(i));
                list.add(newRow);
            }
        }
    }

    public T get(int row, int col) {
        if (row < 0) {
            row = list.size() + row;
        }
        if (col < 0) {
            col = list.get(0).size() + col;
        }
        return list.get(row).get(col);
    }

    public void set(int row, int col, T element) {
        if (row < 0) {
            row = list.size() + row;
        }
        if (col < 0) {
            col = list.get(0).size() + col;
        }
        list.get(row).set(col, element);
    }

    public ArrayList<T> getRow(int row) {
        if (row < 0) {
            row = list.size() + row;
        }
        return list.get(row);
    }

    public ArrayList<T> getColumn(int col) {
        if (col < 0) {
            col = list.get(0).size() + col;
        }
        ArrayList<T> column = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            column.add(list.get(i).get(col));
        }
        return column;
    }
}
