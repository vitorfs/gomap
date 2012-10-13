package br.edu.granbery.gomap.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitor Freitas
 * vitorfs@gmail.com
 */
public class Piece {
    public List<Point> coordinates;
    private int id;
    private int value;
    
    public Piece() {
        coordinates = new ArrayList<Point>();
        value = -1;
    }
    
    public Piece(int id) {
        coordinates = new ArrayList<Point>();
        value = -1;
        this.id = id;
    }
    
    public int size() {
        return coordinates.size();
    }
    
    public void clear() {
        coordinates.clear();
    }

    public void add(int x, int y) {
        coordinates.add(new Point(x, y));
    }
    
    public void add(Point p) {
        coordinates.add(p);
    }
    
    public void remove(int x, int y) {
        Point p = new Point(x, y);
        coordinates.remove(p);
    }
    
    public void remove(Point p) {
        coordinates.remove(p);
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
