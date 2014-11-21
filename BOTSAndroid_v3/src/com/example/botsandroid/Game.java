package com.example.botsandroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements Canvas.OnHideListener {
	
	private int level;
	private int coinCounter;
	private int mapSize;
	private ArrayList<Bomb> bombs;
	private ArrayList<Coin> coins;
	private Tank tank;
	private Canvas canvas;
	private GestureDetectorCompat mDetector;
	private boolean isMovable;
	private String skinType;
	private DialogGameOver dialogGameOver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getActionBar().hide();
		level = 1;
		coinCounter = 0;
		isMovable = false;
		mDetector = new GestureDetectorCompat(this, new OnFlingListener());
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		skinType = sharedPref.getString(getString(R.string.data_selected_skin), "");
		Toast.makeText(this, "Swipe to move the tank", Toast.LENGTH_SHORT).show();
		initCanvas();
	}
	
	@Override
	public void onBombsHidden() {
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
		tank = new Tank(skinType);
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
	
	
	
	protected void moveTank(Command c) {
		int row = tank.getRow();
		int column = tank.getColumn();
		Floor floor = new Floor(skinType);
		tank.setImage(c.ordinal());
		
		if(c == Command.UP){
			if(!(row == 0)) {
				canvas.paint(row, column, floor.getImage());
				checkItems(row-1, column);
				row--;
			}
		} else if(c == Command.DOWN) {
			if(!(row == getMapSize()-1)) {
				canvas.paint(row, column, floor.getImage());
				checkItems(row+1, column);
				row++;
			}
		} else if(c == Command.LEFT) {
			if(!(column == 0)) {
				canvas.paint(row, column, floor.getImage());
				checkItems(row, column-1);
				column--;
			}
		} else if(c == Command.RIGHT) {
			if(!(column == getMapSize()-1)) {
				canvas.paint(row, column, floor.getImage());
				checkItems(row, column+1);
				column++;
			}
		}
		tank.setCoordinate(row, column);
		canvas.paint(row, column, tank.getImage());
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
	
	private void checkItems(int row, int column) {
		for(Iterator<Bomb> bombIterator = bombs.iterator(); bombIterator.hasNext(); ) {
			Bomb bomb = bombIterator.next();
			if((bomb.getRow() == row) && (bomb.getColumn() == column)) {
				tank.setImage(4);
				bombIterator.remove();
				isMovable = false;
				saveData();
				for(Iterator<Bomb> bombIterator2 = bombs.iterator(); bombIterator2.hasNext(); ) {
					Bomb bomb2 = bombIterator2.next();
					bomb2.setImage(0);
					canvas.paint(bomb2.getRow(), bomb2.getColumn(), bomb2.getImage());
				}
				new Handler().postDelayed(new Runnable() {
				    @Override
				    public void run() {
				    	dialogGameOver = new DialogGameOver();
				    	dialogGameOver.show(getFragmentManager(), "DialogGameOver");
				    }
				}, 500);
				break;
			}
		}
		for(Iterator<Coin> coinIterator = coins.iterator(); coinIterator.hasNext(); ) {
			Coin coin = coinIterator.next();
			if((coin.getRow() == row) && (coin.getColumn() == column)) {
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
	
	protected int getWidth() {
		return getIntent().getExtras().getInt("deviceWidth") / 10;
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
	
	protected String getSkinType() {
		return skinType;
	}
	
	public void navigateToMain(View view) {
		dialogGameOver.dismiss();
		finish();
	}
	
	public void restartGame(View view) {
		dialogGameOver.dismiss();
		level = 1;
		coinCounter = 0;
		isMovable = false;
		Toast.makeText(this, "Swipe to move the tank", Toast.LENGTH_SHORT).show();
		initCanvas();
	}
	
	public void shareScore(View view) {
		dialogGameOver.dismiss();
		String imagePath = Environment.getExternalStorageDirectory().toString() + File.separator + "BOTS Scores";
		File imageDir = new File(imagePath);
		if(!imageDir.exists()) {
			imageDir.mkdirs();
		}
		Bitmap bitmap = takeScreenshot();
		File myScreenshot = saveBitmap(bitmap, imagePath);
		
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		Uri screenshotUri = Uri.fromFile(myScreenshot);
		sharingIntent.setType("image/png");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
		startActivity(Intent.createChooser(sharingIntent, "Share your score using"));
	}
	
	private Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		
		LinearLayout gameLayout = (LinearLayout) findViewById(R.id.layout_game);
		int paddingX = 100;
		int paddingY = 100;
		if(Math.round(gameLayout.getX()) < paddingX/2) {
			paddingX = 0;
		}
		Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache(), Math.round(gameLayout.getX())-(paddingX/2), Math.round(gameLayout.getY()), 
				gameLayout.getWidth()+paddingX, gameLayout.getHeight()+paddingY);
		rootView.setDrawingCacheEnabled(false);
		return bitmap;
	}
    
	public File saveBitmap(Bitmap bitmap, String imagePath) {
	    String fileName = new SimpleDateFormat("yyyyMMddhhmm'_score.png'").format(new Date());
	    File myScreenshot = new File(imagePath, fileName);
	    FileOutputStream fos = null;
	    try {
	        fos = new FileOutputStream(myScreenshot);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.e("ScreenshotSave", e.getMessage(), e);
	    } catch (IOException e) {
	        Log.e("ScreenshotSave", e.getMessage(), e);
	    }
	    return myScreenshot;
	}
}
