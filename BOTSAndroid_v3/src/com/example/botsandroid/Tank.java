package com.example.botsandroid;

public class Tank extends GameObject {
	private int imageIndex;
	
	public Tank(String skinType) {
		super(skinType);
	}
	
	public void setImage(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	@Override
	public int getImage() {
		return skin.getTankImages()[imageIndex];
	}

}
