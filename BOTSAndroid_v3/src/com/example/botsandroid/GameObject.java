package com.example.botsandroid;

public abstract class GameObject {
	
	private Coordinate coordinate;
	protected Skin skin;
	
	public GameObject(String skinType) {
		skin = new Skin(skinType);
		coordinate = new Coordinate();
	}
	
	public int getRow() {
		return coordinate.getRow();
	}
	
	public int getColumn() {
		return coordinate.getColumn();
	}
	
	public void setCoordinate(int x, int y) {
		coordinate.setCoordinate(x, y);
	}
	
	public abstract int getImage();
}
