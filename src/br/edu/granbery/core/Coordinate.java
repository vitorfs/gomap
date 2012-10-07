package br.edu.granbery.core;

public class Coordinate {
	public int x;
	public int y;
	
	public Coordinate() {
		
	}
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		
		if (o instanceof Coordinate) {
			Coordinate castObj = (Coordinate)o;
			return castObj.x == this.x && 
					castObj.y == this.y;
		}
		else {
			return false;
		}
	}
}
