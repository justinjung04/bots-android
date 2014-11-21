package com.luxl.botsandroid;

import com.luxl.botsandroid.R;

public class Skin {
	
	private int[] skins = { R.string.skin_default, R.string.skin_guerrilla, R.string.skin_battleship, R.string.skin_pilot, R.string.skin_christmas };
	
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
	private int guerillaCost = 50;
	
	/** Battleship skin images **/
	private int[] battleshipTankImages = {R.drawable.battleship_tank_up, R.drawable.battleship_tank_down, R.drawable.battleship_tank_left, 
			   R.drawable.battleship_tank_right, R.drawable.battleship_tank_dead };
	private int battleshipBombImage = R.drawable.battleship_bomb;
	private int battleshipCoinImage = R.drawable.battleship_coin;
	private int battleshipFloorImage = R.drawable.battleship_floor;
	private int battleshipCost = 200;
	
	/** Pilot skin images **/
	private int[] pilotTankImages = {R.drawable.pilot_tank_up, R.drawable.pilot_tank_down, R.drawable.pilot_tank_left, 
			   R.drawable.pilot_tank_right, R.drawable.pilot_tank_dead };
	private int pilotBombImage = R.drawable.pilot_bomb;
	private int pilotCoinImage = R.drawable.pilot_coin;
	private int pilotFloorImage = R.drawable.pilot_floor;
	private int pilotCost = 500;
	
	/** Christmas skin images **/
	private int[] christmasTankImages = {R.drawable.christmas_tank_up, R.drawable.christmas_tank_down, R.drawable.christmas_tank_left, 
			   R.drawable.christmas_tank_right, R.drawable.christmas_tank_dead };
	private int christmasBombImage = R.drawable.christmas_bomb;
	private int christmasCoinImage = R.drawable.christmas_coin;
	private int christmasFloorImage = R.drawable.christmas_floor;
	private int christmasCost = 1000;
	
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
		} else if(skinType.equals("Battleship")) {
			tankImages = battleshipTankImages;
			bombImage = battleshipBombImage;
			coinImage = battleshipCoinImage;
			floorImage = battleshipFloorImage;
			cost = battleshipCost;
		} else if(skinType.equals("Pilot")) {
			tankImages = pilotTankImages;
			bombImage = pilotBombImage;
			coinImage = pilotCoinImage;
			floorImage = pilotFloorImage;
			cost = pilotCost;
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
	
	public int[] getSkins() {
		return skins;
	}
}
