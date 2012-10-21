package br.edu.granbery.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

public class Piece implements Cloneable{
    public List<Point> coordinates;
    private int id;
    //private int value;
    public List<Piece> adjacency;
    
    public Piece() {
        coordinates = new ArrayList<Point>();
        adjacency = new ArrayList<Piece>();
        //value = -1;
    }
    
    public Piece(int id) {
        coordinates = new ArrayList<Point>();
        adjacency = new ArrayList<Piece>();
        //value = -1;
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
    
    /*public void setValue(int value) {
        this.value = value;
    }*/
    
    /*public int getValue() {
        return value;
    }*/
    
    public void addAdjacency(Piece p) {
    	if (!this.adjacency.contains(p)) {
        	this.adjacency.add(p);
        	p.addAdjacency(this);
    	}
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o == this) return true;
    	if (o instanceof Piece) {
    		Piece castObj;
    		castObj = (Piece)o;
    		if (this.id == castObj.id) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    	else {
    		return false;
    	}
    }
    
    /*@Override
    public Piece clone() {
    	Piece clone = null;
		try {
			clone = (Piece) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return clone;
    }*/
    
}

