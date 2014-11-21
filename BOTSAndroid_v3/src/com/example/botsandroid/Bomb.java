package com.example.botsandroid;

public class Bomb extends GameObject {
	private int imageIndex = 0;
	
	public Bomb(String skinType) {
		super(skinType);
	}
	
	public void setImage(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	@Override
	public int getImage() {
		return skin.getBombImages()[imageIndex];
	}
}
