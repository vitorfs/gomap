package br.edu.granbery.core;

import android.graphics.Point;

public class Graph {

    public Piece[] nodes;
    private int[][] controlGrid;
    
    public Graph(int maxNodes) {
        nodes = new Piece[maxNodes];
    }
    
    public void setControlGrid(int[][] controlGrid) {
    	this.controlGrid = controlGrid;
    }
    
    public int[][] getControlGrid() {
    	return controlGrid;
    }
    
    public Piece getPiece(int x, int y) {
    	Point point = new Point(x, y);
    	return getPiece(point);
    }
        
    public Piece getPiece(Point point) {
    	for (Piece piece : nodes) {
    		if (piece.coordinates.contains(point)) {
    			return piece;
    		}
    	}    	
    	return null;
    }
}
