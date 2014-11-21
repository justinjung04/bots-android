package com.example.botsandroid;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate() {
		this.x = 0;
		this.y = 0;
	}
	
	public void setCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}
}