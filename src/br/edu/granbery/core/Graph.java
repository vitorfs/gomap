package br.edu.granbery.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

public class Graph implements Cloneable {

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
    
    public List<Piece> getPossibleMoves() {
    	List<Piece> possibleMoves = new ArrayList<Piece>();
    	for (Piece p : nodes) {
    		if (p.getValue() == -1) possibleMoves.add(p);
    	}
    	return possibleMoves;
    }
    
    @Override
    public Graph clone() {
    	try {
			Graph clone = (Graph) super.clone();
			
    		Piece newPieces[] = new Piece[nodes.length];
    		for (int i=0;i<nodes.length;i++){
    			newPieces[i] = nodes[i].clone();
    		}		
    		
			return clone;
		} catch (CloneNotSupportedException e) {
			return null;
		}
    }
}
