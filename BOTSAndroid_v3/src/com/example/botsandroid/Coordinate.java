package com.example.botsandroid;

public class Coordinate {
	private int row;
	private int column;
	
	public Coordinate() {
		this.row = 0;
		this.column = 0;
	}
	
	public void setCoordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}