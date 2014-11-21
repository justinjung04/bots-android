package com.example.botsandroid;

public class Tank extends GameObject {
	private boolean alive;
	private int[] tankAlive = { R.drawable.tank_up, R.drawable.tank_down, R.drawable.tank_left, R.drawable.tank_right };
	private int[] tankNotAlive = { R.drawable.tank_up_fire, R.drawable.tank_down_fire, R.drawable.tank_left_fire, R.drawable.tank_right_fire };
	
	public Tank() {
		imagePath = R.drawable.tank_right;
		alive = true;
	}
	
	public void setImage(Command c) {
		if(alive) {
			imagePath = tankAlive[c.ordinal()];
		} else {
			imagePath = tankNotAlive[c.ordinal()];
		}
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
