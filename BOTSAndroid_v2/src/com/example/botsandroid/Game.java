package com.example.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class Game extends Activity implements Canvas.OnHideListener {
	
	private int level = 1;
	private int coinCounter = 0;
	private int mapSize = 0;
	private ArrayList<Bomb> bombs;
	private ArrayList<Coin> coins;
	private Tank tank;
	private Canvas canvas;
	private GestureDetectorCompat mDetector;
	private boolean isMovable;
	private String tankType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getActionBar().hide();
		mDetector = new GestureDetectorCompat(this, new OnFlingListener());
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		tankType = sharedPref.getString(getString(R.string.data_skin), "");
		initCanvas();
	}
	
	@Override
	public void onBombsHiden() {
		isMovable = true;
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    
    private class OnFlingListener extends GestureDetector.SimpleOnGestureListener {
    	@Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
    		FlingCalculator calculator = new FlingCalculator(event1.getX(), event1.getY(), event2.getX(), event2.getY());
    		if(isMovable) {
    			moveTank(calculator.getCommand());
    		}
            return true;
        }
    }
	
	protected void initCanvas() {
		bombs = new ArrayList<Bomb>();
		coins = new ArrayList<Coin>();
		tank = new Tank(tankType);
		canvas = new Canvas();
		setLevelText();
		setCoinText();
		getFragmentManager().beginTransaction().replace(R.id.fragment_canvas, canvas).commit();
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
	
	protected void moveTank(Command c) {
		int x = tank.getXCoordinate();
		int y = tank.getYCoordinate();
		Floor floor = new Floor();
		
		if(c == Command.UP){
			if(!(y == 0)) {
				canvas.paint(x, y, floor.getImage());
				checkItems(x, y-1);
				y--;
			}
		} else if(c == Command.DOWN) {
			if(!(y == getMapSize()-1)) {
				canvas.paint(x, y, floor.getImage());
				checkItems(x, y+1);
				y++;
			}
		} else if(c == Command.LEFT) {
			if(!(x == 0)) {
				canvas.paint(x, y, floor.getImage());
				checkItems(x-1, y);
				x--;
			}
		} else if(c == Command.RIGHT) {
			if(!(x == getMapSize()-1)) {
				canvas.paint(x, y, floor.getImage());
				checkItems(x+1, y);
				x++;
			}
		}
		tank.setCoordinate(x, y);
		tank.setImage(c, tankType);
		canvas.paint(tank.getXCoordinate(), tank.getYCoordinate(), tank.getImage());
		setCoinText();
		if(coins.size() == 0) {
			isMovable = false;
			new Handler().postDelayed(new Runnable() {
			    @Override
			    public void run() {
			    	level++;
					initCanvas();
			    }
			}, 500);
		}
	}
	
	private void checkItems(int x, int y) {
		for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
			Bomb bomb = bombIterator.next();
			if((bomb.getXCoordinate() == x) && (bomb.getYCoordinate() == y)) {
				tank.setAlive(false);
				bombIterator.remove();
				isMovable = false;
				saveData();
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
	
	private void saveData() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		
		Editor editor = sharedPref.edit();
		if(sharedPref.getInt(getString(R.string.data_most_coins), 0) < coinCounter) {
			editor.putInt(getString(R.string.data_most_coins), coinCounter);
		}
		if(sharedPref.getInt(getString(R.string.data_highest_level), 0) < level) {
			editor.putInt(getString(R.string.data_highest_level), level);
		}
		int totalCoins = sharedPref.getInt(getString(R.string.data_total_coins), 0) + coinCounter;
		editor.putInt(getString(R.string.data_total_coins), totalCoins);
		editor.commit();
	}


}
