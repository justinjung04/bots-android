package com.example.botsandroid;

public class Skin {
	
	/** Normal skin images **/
	private int[] defaultTankImages = {R.drawable.default_tank_up, R.drawable.default_tank_down, R.drawable.default_tank_left, 
									   R.drawable.default_tank_right, R.drawable.default_tank_dead };
	private int defaultBombImage = R.drawable.default_bomb;
	private int defaultCoinImage = R.drawable.default_coin;
	private int defaultFloorImage = R.drawable.default_floor;
	private int defaultCost = 0;
	
	/** Guerrilla skin images **/
	private int[] guerrillaTankImages = {R.drawable.guerrilla_tank_up, R.drawable.guerrilla_tank_down, R.drawable.guerrilla_tank_left, 
										 R.drawable.guerrilla_tank_right, R.drawable.default_tank_dead };
	private int guerrillaBombImage = defaultBombImage;
	private int guerrillaCoinImage = defaultCoinImage;
	private int guerrillaFloorImage = defaultFloorImage;
	private int guerillaCost = 10;
	
	/** Christmas skin images **/
	private int[] christmasTankImages = defaultTankImages;
	private int christmasBombImage = defaultBombImage;
	private int christmasCoinImage = defaultCoinImage;
	private int christmasFloorImage = defaultFloorImage;
	private int christmasCost = 50;
	
	private int[] tankImages;
	private int bombImage;
	private int coinImage;
	private int floorImage;
	private int cost;
	
	public Skin(String skinType) {
		if(skinType.equals("Default")) {
			tankImages = defaultTankImages;
			bombImage = defaultBombImage;
			coinImage = defaultCoinImage;
			floorImage = defaultFloorImage;
			cost = defaultCost;
		} else if(skinType.equals("Guerrilla")) {
			tankImages = guerrillaTankImages;
			bombImage = guerrillaBombImage;
			coinImage = guerrillaCoinImage;
			floorImage = guerrillaFloorImage;
			cost = guerillaCost;
		} else if(skinType.equals("Christmas")) {
			tankImages = christmasTankImages;
			bombImage = christmasBombImage;
			coinImage = christmasCoinImage;
			floorImage = christmasFloorImage;
			cost = christmasCost;
		}
	}
	
	public int[] getTankImages() {
		return tankImages;
	}
	
	public int getBombImage() {
		return bombImage;
	}
	
	public int getCoinImage() {
		return coinImage;
	}
	
	public int getFloorImage() {
		return floorImage;
	}
	
	public int getCost() {
		return cost;
	}
}
