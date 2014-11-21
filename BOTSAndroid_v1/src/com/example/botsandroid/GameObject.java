package com.example.botsandroid;

public class GameObject {
	
	private Coordinate coordinate;
	protected int imagePath;
	
	public GameObject() {
		coordinate = new Coordinate();
	}
	
	public int getXCoordinate() {
		return coordinate.getXCoordinate();
	}
	
	public int getYCoordinate() {
		return coordinate.getYCoordinate();
	}
	
	public void setCoordinate(int x, int y) {
		coordinate.setCoordinate(x, y);
	}
	
	public int getImage() {
		return imagePath;
	}
}
