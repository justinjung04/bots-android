package com.example.botsandroid;

public class Bomb extends GameObject {
	private int[] images = 
			{R.drawable.bomb_1b, R.drawable.bomb_2b, R.drawable.bomb_3b, R.drawable.bomb_4b, R.drawable.bomb_5b, 
			 R.drawable.bomb_6b, R.drawable.bomb_7b, R.drawable.bomb_8b, R.drawable.bomb_9b, R.drawable.bomb_10b, 
			 R.drawable.bomb_11b, R.drawable.bomb_12b, R.drawable.bomb_13b, R.drawable.bomb_14b, R.drawable.bomb_15b, 
			 R.drawable.bomb_16b, R.drawable.bomb_17b, R.drawable.bomb_18b, R.drawable.bomb_19b, R.drawable.bomb_20b, 
			 R.drawable.bomb_21b, R.drawable.bomb_22b, R.drawable.bomb_23b, R.drawable.bomb_24b, R.drawable.bomb_25b, 
			 R.drawable.bomb_26b, R.drawable.bomb_27b, R.drawable.bomb_28b, R.drawable.bomb_29b, R.drawable.bomb_30b, 
			 R.drawable.bomb_31b, R.drawable.bomb_32b, R.drawable.bomb_33b, R.drawable.bomb_34b, R.drawable.bomb_35b, 
			 R.drawable.bomb_36b, R.drawable.bomb_37b, R.drawable.bomb_38b, R.drawable.bomb_39b, R.drawable.bomb_40b };
	
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
