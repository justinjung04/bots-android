package com.luxl.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.luxl.botsandroid.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentCanvas extends Fragment {
	
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
	
	private ActivityGame game;
	private ArrayList<Bomb> bombs;
	private ArrayList<Coin> coins;
	private Tank tank;
	private int mapSize;
	private int deviceWidth;
	private int bombImageCounter;
	private OnHideListener mCallback;
	private Handler hideHandler;
	private String skinType;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnHideListener) activity;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_canvas, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		game = (ActivityGame) getActivity();
		bombs = game.getBombs();
		coins = game.getCoins();
		tank = game.getTank();
		mapSize = game.getMapSize();
		deviceWidth = game.getWidth();
		skinType = game.getSkinType();
		
		initLayout(mapSize);
		initTank(mapSize);
		initBombs(mapSize);
		initCoins(mapSize);
		hideBombs(mapSize);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		hideHandler.removeCallbacks(hideRunnable);
	};
	
	public interface OnHideListener {
		public void onBombsHidden();
	}
	
	private void hideBombs(int mapSize) {
		hideHandler = new Handler();
		bombImageCounter = 0;
		hideHandler.postDelayed(hideRunnable, mapSize*400);
	}
	
	private Runnable hideRunnable = new Runnable() {
		@Override
		public void run() {
			if(bombImageCounter < 5) {
				for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
					Bomb bomb = bombIterator.next();
					if((bombImageCounter % 2) == 1) {
						paint(bomb.getRow(), bomb.getColumn(), bomb.getImage());
					} else {
						paint(bomb.getRow(), bomb.getColumn(), new Floor(skinType).getImage());
					}
				}
				bombImageCounter++;
				hideHandler.postDelayed(this, 70);
			} else {
				mCallback.onBombsHidden();
				hideHandler.removeCallbacks(this);
			}
		}
	};

	private void initLayout(int mapSize) {
		for(int row=0; row<mapSize; row++) {
			for(int column=0; column<mapSize; column++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setId(coordinates[row][column]);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(deviceWidth, deviceWidth));
				imageView.setImageResource(new Floor(skinType).getImage());
				LinearLayout rowLayout = (LinearLayout) getActivity().findViewById(rows[row]);
				rowLayout.addView(imageView);
			}
		}
	}
	
	private void initTank(int mapSize) {
		tank.setCoordinate((mapSize/2)-1, 0);
		tank.setImage(3);
		paint(tank.getRow(), tank.getColumn(), tank.getImage());
	}
	
	private void initBombs(int mapSize) {
		int tankRow = 0;
		
		switch(tank.getRow()) {
			case 1: case 3:
				tankRow = tank.getRow() - 1;
				break;
			case 2: case 4:
				tankRow = tank.getRow();
				break;
		}
		
		Random rand = new Random();
		for(int row=0; row<mapSize; row+=2) {
			for(int column=0; column<mapSize; column+=2) {
				if(!((row == tankRow) && (column == 0))) {
					int x = rand.nextInt(2);
					int y = rand.nextInt(2);
					if((column+x == 1) && ((row+y == 1) || (row+y == mapSize-2))) {
						x--;
					} else if ((column+x == mapSize-2) && ((row+y == 1) || (row+y == mapSize-2))) {
						x++;
					}
					Bomb bomb = new Bomb(skinType);
					bomb.setCoordinate(row+y, column+x);
					paint(bomb.getRow(), bomb.getColumn(), bomb.getImage());
					bombs.add(bomb);
				}
			}
		}
	}
	
	private void initCoins(int mapSize) {
		Random rand = new Random();
		int num, row, column;
		boolean overlap;
		
		for(int i=0; i<mapSize; i++) {
			num = rand.nextInt(mapSize*mapSize);
			row = num / mapSize;
			column = num % mapSize;
			overlap = false;
			
			for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
				Bomb bomb = bombIterator.next();
				if((row == bomb.getRow()) && (column == bomb.getColumn())) {
					overlap = true;
					break;
				}
			}
			for(Iterator<Coin> coinIterator = coins.iterator(); coinIterator.hasNext(); ) {
				Coin coin = coinIterator.next();
				if((row == coin.getRow()) && (column == coin.getColumn())) {
					overlap = true;
					break;
				}
			}
			if((row == tank.getRow()) && (column == tank.getColumn())) {
				overlap = true;
			}
			
			if(overlap) {
				i--;
			} else {
				Coin coin = new Coin(skinType);
				coin.setCoordinate(row, column);
				paint(coin.getRow(), coin.getColumn(), coin.getImage());
				coins.add(coin);
			}
		}
	}
	
	public void paint(int row, int column, int imageResource) {
		ImageView imageView = (ImageView) getActivity().findViewById(coordinates[row][column]);
		imageView.setImageResource(imageResource);
	}
}
