package com.example.botsandroid;

public class Floor extends GameObject {

	public Floor(String skinType) {
		super(skinType);
	}

	@Override
	public int getImage() {
		return skin.getFloorImage();
	}

}
