package com.example.botsandroid;

public class Bomb extends GameObject {
	private int[] images = 
			{R.drawable.bomb_1b, R.drawable.bomb_2b, R.drawable.bomb_3b, R.drawable.bomb_4b, R.drawable.bomb_5b, 
			 R.drawable.bomb_6b, R.drawable.bomb_7b, R.drawable.bomb_8b, R.drawable.bomb_9b, R.drawable.bomb_10b, 
			 R.drawable.bomb_11b, R.drawable.bomb_12b, R.drawable.bomb_13b, R.drawable.bomb_14b, R.drawable.bomb_15b, 
			 R.drawable.bomb_16b, R.drawable.bomb_17b, R.drawable.bomb_18b };
	
	public Bomb() {
		imagePath = R.drawable.bomb_1b;
	}
	
	public void setImage(int i) {
		imagePath = images[i];
	}
	
	public int getNumberOfImages() {
		return images.length;
	}
}
