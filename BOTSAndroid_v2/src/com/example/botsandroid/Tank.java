package com.example.botsandroid;

public class Tank extends GameObject {
	private boolean alive;
	private int[] tankAlive = { R.drawable.tank_up, R.drawable.tank_down, R.drawable.tank_left, R.drawable.tank_right };
	private int[] tankAliveG = { R.drawable.tank_g_up, R.drawable.tank_g_down, R.drawable.tank_g_left, R.drawable.tank_g_right };
	private int tankDead = R.drawable.tank_dead;
	
	public Tank(String type) {
		if(type.equals("normal")) {
			imagePath = R.drawable.tank_right;
		} else if(type.equals("guerilla")){
			imagePath = R.drawable.tank_g_right;
		}
		alive = true;
	}
	
	public void setImage(Command c, String type) {
		if(alive) {
			if(type.equals("normal")) {
				imagePath = tankAlive[c.ordinal()];
			} else if(type.equals("guerilla")){
				imagePath = tankAliveG[c.ordinal()];
			}
			
		} else {
			imagePath = tankDead;
		}
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
