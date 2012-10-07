package br.edu.granbery.core;

import java.util.ArrayList;
import java.util.List;

public class Piece {
	private List<Coordinate> coords;
	private int value;
	
	public Piece() {
		this.coords = new ArrayList<Coordinate>();
		value = -1; 
	}
	
	public int getPieceSize() {
		return coords.size();
	}
	
	public void put(Coordinate c) {
		coords.add(c);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
