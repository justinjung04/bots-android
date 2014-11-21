package com.example.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.widget.TextView;

public class Game extends Activity {
	
	private int level = 10;
	private int coinCounter = 0;
	private int mapSize = 0;
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private Tank tank = new Tank();
	private GestureDetectorCompat mDetector;
	private Canvas canvas;
	private boolean isTouchable = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getActionBar().hide();
		
		setLevelText();
		setCoinText();
	}
	
	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
		if(isTouchable && (mDetector != null)) {
			this.mDetector.onTouchEvent(event);
	        return super.onTouchEvent(event);
		} else {
			return false;
		}
    }
	
	protected void initUserControl() {
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		canvas = (Canvas) getFragmentManager().findFragmentById(R.id.fragment_canvas);
		isTouchable = true;
	}
	
	private void setLevelText() {
		TextView levelText = (TextView) findViewById(R.id.text_game_level);
		levelText.setText("Level " + level);
	}
	
	private void setCoinText() {
		TextView coinText = (TextView) findViewById(R.id.text_game_coins);
		coinText.setText("Coins collected: " + coinCounter);
	}
	
	protected int getWidth() {
		return getIntent().getExtras().getInt("DEVICE_WIDTH") / 10;
	}
	
	protected int getMapSize() {
		if(level < 4) {
			mapSize = 4;
		} else if(level < 7) {
			mapSize = 6;
		} else if(level < 10) {
			mapSize = 8;
		} else {
			mapSize = 10;
		}
		return mapSize;
	}
	
	protected int getLevel() {
		return level;
	}
	
	protected ArrayList<Bomb> getBombs() {
		return bombs;
	}
	
	protected ArrayList<Coin> getCoins() {
		return coins;
	}
	
	protected Tank getTank() {
		return tank;
	}
	
	private class MyGestureListener extends OnSwipeListener {
		@Override
		public boolean onSwipe(Command c) {
			int x = tank.getXCoordinate();
			int y = tank.getYCoordinate();
			Floor floor = new Floor();
			
			if(c == Command.up){
				if(!(y == 0)) {
					canvas.paint(x, y, floor.getImage());
					checkItems(x, y-1);
					y--;
				}
			} else if(c == Command.down) {
				if(!(y == getMapSize()-1)) {
					canvas.paint(x, y, floor.getImage());
					checkItems(x, y+1);
					y++;
				}
			} else if(c == Command.left) {
				if(!(x == 0)) {
					canvas.paint(x, y, floor.getImage());
					checkItems(x-1, y);
					x--;
				}
			} else if(c == Command.right) {
				if(!(x == getMapSize()-1)) {
					canvas.paint(x, y, floor.getImage());
					checkItems(x+1, y);
					x++;
				}
			}
			tank.setCoordinate(x, y);
			tank.setImage(c);
			canvas.paint(tank.getXCoordinate(), tank.getYCoordinate(), tank.getImage());
			setCoinText();
			return false;
			
//			if(coins.size() == 0) {
//				canvas.getFrame().dispose();
//				setChanged();
//				notifyObservers(getCoinCounter());
//			}
		}
	}
	
	private void checkItems(int x, int y) {
		for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
			Bomb bomb = bombIterator.next();
			if((bomb.getXCoordinate() == x) && (bomb.getYCoordinate() == y)) {
				tank.setAlive(false);
				isTouchable = false;
				bombIterator.remove();
				for(Iterator<Bomb> bombIterator2 = bombs.iterator(); bombIterator2.hasNext(); ) {
					Bomb bomb2 = bombIterator2.next();
					bomb2.setImage(0);
					canvas.paint(bomb2.getXCoordinate(), bomb2.getYCoordinate(), bomb2.getImage());
				}
				break;
			}
		}
		
		for(Iterator<Coin> coinIterator = coins.iterator(); coinIterator.hasNext(); ) {
			Coin coin = coinIterator.next();
			if((coin.getXCoordinate() == x) && (coin.getYCoordinate() == y)) {
				coinCounter++;
				coinIterator.remove();
				break;
			}
		}
	}
}
