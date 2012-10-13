package br.edu.granbery.core;

import android.graphics.Point;

public class Graph {

    public Piece[] nodes;
    private boolean[][] arcs;
    private int[][] controlGrid;
    private final int MAX_NODES;
    
    public Graph(int maxNodes) {
        MAX_NODES = maxNodes;
        nodes = new Piece[maxNodes];
        arcs = new boolean[maxNodes][maxNodes];
    }

    public void join(int node1, int node2) {
        if (node1 < MAX_NODES && node2 < MAX_NODES) {
            arcs[node1][node2] = true;
        }
    }

    public void remove(int node1, int node2) {
        if (node1 < MAX_NODES && node2 < MAX_NODES) {
            arcs[node1][node2] = false;
        }
    }

    public boolean isAdjacent(int node1, int node2) {
        return arcs[node1][node2];
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
