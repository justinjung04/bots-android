package com.example.botsandroid;

public class Bomb extends GameObject {
	
	public Bomb(String skinType) {
		super(skinType);
	}

	@Override
	public int getImage() {
		return skin.getBombImage();
	}
}
