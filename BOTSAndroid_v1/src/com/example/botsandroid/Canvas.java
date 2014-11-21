package com.example.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Canvas extends Fragment {
	
	private int[][] coordinates = {
			{ R.id.coord_0_0, R.id.coord_0_1, R.id.coord_0_2, R.id.coord_0_3, R.id.coord_0_4, R.id.coord_0_5, R.id.coord_0_6, R.id.coord_0_7, R.id.coord_0_8, R.id.coord_0_9 },
			{ R.id.coord_1_0, R.id.coord_1_1, R.id.coord_1_2, R.id.coord_1_3, R.id.coord_1_4, R.id.coord_1_5, R.id.coord_1_6, R.id.coord_1_7, R.id.coord_1_8, R.id.coord_1_9 },
			{ R.id.coord_2_0, R.id.coord_2_1, R.id.coord_2_2, R.id.coord_2_3, R.id.coord_2_4, R.id.coord_2_5, R.id.coord_2_6, R.id.coord_2_7, R.id.coord_2_8, R.id.coord_2_9 },
			{ R.id.coord_3_0, R.id.coord_3_1, R.id.coord_3_2, R.id.coord_3_3, R.id.coord_3_4, R.id.coord_3_5, R.id.coord_3_6, R.id.coord_3_7, R.id.coord_3_8, R.id.coord_3_9 },
			{ R.id.coord_4_0, R.id.coord_4_1, R.id.coord_4_2, R.id.coord_4_3, R.id.coord_4_4, R.id.coord_4_5, R.id.coord_4_6, R.id.coord_4_7, R.id.coord_4_8, R.id.coord_4_9 },
			{ R.id.coord_5_0, R.id.coord_5_1, R.id.coord_5_2, R.id.coord_5_3, R.id.coord_5_4, R.id.coord_5_5, R.id.coord_5_6, R.id.coord_5_7, R.id.coord_5_8, R.id.coord_5_9 },
			{ R.id.coord_6_0, R.id.coord_6_1, R.id.coord_6_2, R.id.coord_6_3, R.id.coord_6_4, R.id.coord_6_5, R.id.coord_6_6, R.id.coord_6_7, R.id.coord_6_8, R.id.coord_6_9 },
			{ R.id.coord_7_0, R.id.coord_7_1, R.id.coord_7_2, R.id.coord_7_3, R.id.coord_7_4, R.id.coord_7_5, R.id.coord_7_6, R.id.coord_7_7, R.id.coord_7_8, R.id.coord_7_9 },
			{ R.id.coord_8_0, R.id.coord_8_1, R.id.coord_8_2, R.id.coord_8_3, R.id.coord_8_4, R.id.coord_8_5, R.id.coord_8_6, R.id.coord_8_7, R.id.coord_8_8, R.id.coord_8_9 },
			{ R.id.coord_9_0, R.id.coord_9_1, R.id.coord_9_2, R.id.coord_9_3, R.id.coord_9_4, R.id.coord_9_5, R.id.coord_9_6, R.id.coord_9_7, R.id.coord_9_8, R.id.coord_9_9 }};
	
	private int[] rows = {R.id.row_0, R.id.row_1, R.id.row_2, R.id.row_3, R.id.row_4, R.id.row_5, R.id.row_6, R.id.row_7, R.id.row_8, R.id.row_9};
	private Game game = null;
	private ArrayList<Bomb> bombs;
	private ArrayList<Coin> coins;
	private Tank tank;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_canvas, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		game = (Game) getActivity();
		bombs = game.getBombs();
		coins = game.getCoins();
		tank = game.getTank();
		
		initLayout(game.getMapSize());
		initTank(game.getMapSize());
		initBombs(game.getMapSize());
		initCoins(game.getMapSize());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		hideBombs();
	}
	
	private int index = 0;
	private Handler hideHandler = new Handler();
	
	private void hideBombs() {
		hideHandler.postDelayed(hideRunnable, 500);
	}
	
	private Runnable hideRunnable = new Runnable() {
		@Override
		public void run() {
			if(index < 40) {
				for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
					Bomb bomb = bombIterator.next();
					bomb.setImage(index);
					paint(bomb.getXCoordinate(), bomb.getYCoordinate(), bomb.getImage());
				}
	        	index++;
				hideHandler.postDelayed(this, 100);
			} else {
				game.initUserControl();
				hideHandler.removeCallbacks(this);
			}
		}
	};

	private void initLayout(int mapSize) {
		for(int i=0; i<mapSize; i++) {
			for(int j=0; j<mapSize; j++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setId(coordinates[i][j]);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(game.getWidth(), game.getWidth()));
				imageView.setImageResource(R.drawable.floor);
				
				LinearLayout gridLayout = (LinearLayout) getActivity().findViewById(rows[i]);
				gridLayout.addView(imageView);
			}
		}
	}
	
	private void initTank(int mapSize) {
		tank.setCoordinate(0, (mapSize/2)-1);
		paint(tank.getXCoordinate(), tank.getYCoordinate(), tank.getImage());
	}
	
	private void initBombs(int mapSize) {
		int x = 0;
		int y = 0;
		int num = 0;
		int tankY = 0;
		
		switch(tank.getYCoordinate()) {
			case 1: case 3:
				tankY = tank.getYCoordinate() - 1;
				break;
			case 2: case 4:
				tankY = tank.getYCoordinate();
				break;
		}
		
		Random rand = new Random();
		for(int i=0; i<mapSize; i=i+2) {
			for(int j=0; j<mapSize; j=j+2) {
				if(!((i == 0) && (j == tankY))) {
					num = rand.nextInt(4);
					switch(num) {
						case 0:
							x = 0;
							y = 0;
							break;
						case 1:
							x = 1;
							y = 0;
							break;
						case 2:
							x = 0;
							y = 1;
							break;
						case 3:
							x = 1;
							y = 1;
							break;
					}
					if(y+j == 1) {
						if((x+i == 1) || (x+i == mapSize-2)) {
							y--;
						}
					} else if(y+j == mapSize-2) {
						if((x+i == 1) || (x+i == mapSize-2)) {
							y++;
						}
					}
					Bomb bomb = new Bomb();
					bomb.setCoordinate(x+i, y+j);
					paint(bomb.getXCoordinate(), bomb.getYCoordinate(), bomb.getImage());
					bombs.add(bomb);
				}
			}
		}
	}
	
	private void initCoins(int mapSize) {
		Random rand = new Random();
		int num, x, y;
		boolean overlap;
		
		for(int i=0; i<mapSize; i++) {
			num = rand.nextInt(mapSize*mapSize);
			x = num / mapSize;
			y = num % mapSize;
			overlap = false;
			
			for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
				Bomb bomb = bombIterator.next();
				if((x == bomb.getXCoordinate()) && (y == bomb.getYCoordinate())) {
					overlap = true;
					break;
				}
			}
			
			for(Iterator<Coin> coinIterator = coins.iterator(); coinIterator.hasNext(); ) {
				Coin coin = coinIterator.next();
				if((x == coin.getXCoordinate()) && (y == coin.getYCoordinate())) {
					overlap = true;
					break;
				}
			}
			
			if((x == 0) && (y == (mapSize/2)-1)) {
				overlap = true;
			}
			
			if(overlap) {
				i--;
			} else {
				Coin coin = new Coin();
				coin.setCoordinate(x, y);
				paint(coin.getXCoordinate(), coin.getYCoordinate(), coin.getImage());
				coins.add(coin);
			}
		}
	}
	
	public void paint(int x, int y, int imageResource) {
		ImageView imageView = (ImageView) getActivity().findViewById(coordinates[y][x]);
		imageView.setImageResource(imageResource);
	}
}
