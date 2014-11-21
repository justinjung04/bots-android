package com.example.botsandroid;

public class Skin {
	
	/** Normal skin images **/
	private int[] defaultTankImages = {R.drawable.default_tank_up, R.drawable.default_tank_down, R.drawable.default_tank_left, 
									   R.drawable.default_tank_right, R.drawable.default_tank_dead };
	private int[] defaultBombImages = {R.drawable.normal_1_bomb, R.drawable.normal_2_bomb, R.drawable.normal_3_bomb, R.drawable.normal_4_bomb, R.drawable.normal_5_bomb,
									   R.drawable.normal_6_bomb, R.drawable.normal_7_bomb, R.drawable.normal_8_bomb, R.drawable.normal_9_bomb, R.drawable.normal_10_bomb, 
									   R.drawable.normal_11_bomb, R.drawable.normal_12_bomb, R.drawable.normal_13_bomb, R.drawable.normal_14_bomb, R.drawable.normal_15_bomb, 
									   R.drawable.normal_16_bomb, R.drawable.normal_17_bomb, R.drawable.normal_18_bomb };
	private int defaultCoinImage = R.drawable.normal_coin;
	private int defaultFloorImage = R.drawable.normal_floor;
	private int defaultCost = 0;
	
	/** Guerrilla skin images **/
	private int[] guerrillaTankImages = {R.drawable.guerrilla_tank_up, R.drawable.guerrilla_tank_down, R.drawable.guerrilla_tank_left, 
										 R.drawable.guerrilla_tank_right, R.drawable.default_tank_dead };
	private int[] guerrillaBombImages = defaultBombImages;
	private int guerrillaCoinImage = defaultCoinImage;
	private int guerrillaFloorImage = defaultFloorImage;
	private int guerillaCost = 10;
	
	/** Christmas skin images **/
	private int[] christmasTankImages = defaultTankImages;
	private int[] christmasBombImages = defaultBombImages;
	private int christmasCoinImage = defaultCoinImage;
	private int christmasFloorImage = defaultFloorImage;
	private int christmasCost = 2000;
	
	private int[] tankImages;
	private int[] bombImages;
	private int coinImage;
	private int floorImage;
	private int cost;
	
	public Skin(String skinType) {
		if(skinType.equals("Default")) {
			tankImages = defaultTankImages;
			bombImages = defaultBombImages;
			coinImage = defaultCoinImage;
			floorImage = defaultFloorImage;
			cost = defaultCost;
		} else if(skinType.equals("Guerrilla")) {
			tankImages = guerrillaTankImages;
			bombImages = guerrillaBombImages;
			coinImage = guerrillaCoinImage;
			floorImage = guerrillaFloorImage;
			cost = guerillaCost;
		} else if(skinType.equals("Christmas")) {
			tankImages = christmasTankImages;
			bombImages = christmasBombImages;
			coinImage = christmasCoinImage;
			floorImage = christmasFloorImage;
			cost = christmasCost;
		}
	}
	
	public int[] getTankImages() {
		return tankImages;
	}
	
	public int[] getBombImages() {
		return bombImages;
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
