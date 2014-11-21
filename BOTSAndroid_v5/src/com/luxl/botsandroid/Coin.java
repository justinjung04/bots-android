package com.luxl.botsandroid;

public class Coin extends GameObject {

	public Coin(String skinType) {
		super(skinType);
	}

	@Override
	public int getImage() {
		return skin.getCoinImage();
	}
}
