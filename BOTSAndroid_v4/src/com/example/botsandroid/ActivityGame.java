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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityGame extends Activity implements FragmentCanvas.OnHideListener {
	
	private int level;
	private int coinCounter;
	private int mapSize;
	private ArrayList<Bomb> bombs;
	private ArrayList<Coin> coins;
	private Tank tank;
	private FragmentCanvas canvas;
	private GestureDetectorCompat mDetector;
	private boolean isMovable;
	private String skinType;
	private DialogGameOver dialogGameOver;
	private String achievementsName = "";
	private int extraRewards = 0;
	private Achievements ach;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		level = 1;
		coinCounter = 0;
		isMovable = false;
		ach = new Achievements();
		mDetector = new GestureDetectorCompat(this, new OnFlingListener());
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		skinType = sharedPref.getString(getString(R.string.data_selected_skin), "");
		Toast.makeText(this, "Swipe to move the tank", Toast.LENGTH_SHORT).show();
		initCanvas();
		changeFont("cambria.ttf");
	}
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getAssets(), newFont);
		TextView textView1 = (TextView) findViewById(R.id.game_text_level);
		TextView textView2 = (TextView) findViewById(R.id.game_text_coins);
		textView1.setTypeface(fontCambria);
		textView2.setTypeface(fontCambria);
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
		canvas = new FragmentCanvas();
		achievementsName = "";
		extraRewards = 0;
		setLevelText();
		setCoinText();
		getFragmentManager().beginTransaction().replace(R.id.fragment_canvas, canvas).commit();
	}
	
	private void setLevelText() {
		TextView levelText = (TextView) findViewById(R.id.game_text_level);
		levelText.setText("Level " + level);
	}
	
	private void setCoinText() {
		TextView coinText = (TextView) findViewById(R.id.game_text_coins);
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
					canvas.paint(bomb2.getRow(), bomb2.getColumn(), bomb2.getImage());
				}
				new Handler().postDelayed(new Runnable() {
				    @Override
				    public void run() {
				    	openDialogGameOver();
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
	
	private void openDialogGameOver() {
		dialogGameOver = new DialogGameOver();
		Bundle args = new Bundle();
		args.putInt("coins", coinCounter);
		args.putInt("level", level);
		args.putInt("reward", extraRewards);
		args.putString("achievements", achievementsName);
		dialogGameOver.setArguments(args);
    	dialogGameOver.show(getFragmentManager(), "DialogGameOver");
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
		
		String achievementsNumber = "";
		String oldCompletedAChievements = sharedPref.getString(getString(R.string.data_completed_achievements), "");
		if(!oldCompletedAChievements.contains(";0;")) {
			achievementsNumber += ";0;";
			achievementsName +=  "\n" + getString(ach.getNames()[0]);
			extraRewards += ach.getRewards()[0];
		}
		if((coinCounter == 0) && (!oldCompletedAChievements.contains(";1;"))) {
			achievementsNumber += "1;";
			achievementsName +=  "\n" + getString(ach.getNames()[1]);
			extraRewards += ach.getRewards()[1];
		}
		if((totalCoins >= 50) && (!oldCompletedAChievements.contains(";2;"))) {
			achievementsNumber += "2;";
			achievementsName +=  "\n" + getString(ach.getNames()[2]);
			extraRewards += ach.getRewards()[2];
		}
		if((totalCoins >= 100)  && (!oldCompletedAChievements.contains(";4;"))) {
			achievementsNumber += "4;";
			achievementsName +=  "\n" + getString(ach.getNames()[4]);
			extraRewards += ach.getRewards()[4];
		}
		if((totalCoins >= 200)  && (!oldCompletedAChievements.contains(";6;"))) {
			achievementsNumber += "6;";
			achievementsName +=  "\n" + getString(ach.getNames()[6]);
			extraRewards += ach.getRewards()[6];
		}
		if((totalCoins >= 400)  && (!oldCompletedAChievements.contains(";8;"))) {
			achievementsNumber += "8;";
			achievementsName +=  "\n" + getString(ach.getNames()[8]);
			extraRewards += ach.getRewards()[8];
		}
		if((totalCoins >= 600)  && (!oldCompletedAChievements.contains(";9;"))) {
			achievementsNumber += "9;";
			achievementsName +=  "\n" + getString(ach.getNames()[9]);
			extraRewards += ach.getRewards()[9];
		}
		if((totalCoins >= 800)  && (!oldCompletedAChievements.contains(";12;"))) {
			achievementsNumber += "12;";
			achievementsName +=  "\n" + getString(ach.getNames()[12]);
			extraRewards += ach.getRewards()[12];
		}
		if((totalCoins >= 1000)  && (!oldCompletedAChievements.contains(";13;"))) {
			achievementsNumber += "13;";
			achievementsName +=  "\n" + getString(ach.getNames()[13]);
			extraRewards += ach.getRewards()[13];
		}
		if((totalCoins >= 1200)  && (!oldCompletedAChievements.contains(";16;"))) {
			achievementsNumber += "16;";
			achievementsName +=  "\n" + getString(ach.getNames()[16]);
			extraRewards += ach.getRewards()[16];
		}
		if((totalCoins >= 1400)  && (!oldCompletedAChievements.contains(";17;"))) {
			achievementsNumber += "17;";
			achievementsName +=  "\n" + getString(ach.getNames()[17]);
			extraRewards += ach.getRewards()[17];
		}
		if((totalCoins >= 1600)  && (!oldCompletedAChievements.contains(";20;"))) {
			achievementsNumber += "20;";
			achievementsName +=  "\n" + getString(ach.getNames()[20]);
			extraRewards += ach.getRewards()[20];
		}
		if((totalCoins >= 1800)  && (!oldCompletedAChievements.contains(";22;"))) {
			achievementsNumber += "22;";
			achievementsName +=  "\n" + getString(ach.getNames()[22]);
			extraRewards += ach.getRewards()[22];
		}
		if((totalCoins >= 2000)  && (!oldCompletedAChievements.contains(";23;"))) {
			achievementsNumber += "23;";
			achievementsName +=  "\n" + getString(ach.getNames()[23]);
			extraRewards += ach.getRewards()[23];
		}
		if((level >= 5)  && (!oldCompletedAChievements.contains(";3;"))) {
			achievementsNumber += "3;";
			achievementsName +=  "\n" + getString(ach.getNames()[3]);
			extraRewards += ach.getRewards()[3];
		}
		if((level >= 10)  && (!oldCompletedAChievements.contains(";7;"))) {
			achievementsNumber += "7;";
			achievementsName +=  "\n" + getString(ach.getNames()[7]);
			extraRewards += ach.getRewards()[7];
		}
		if((level >= 15)  && (!oldCompletedAChievements.contains(";11;"))) {
			achievementsNumber += "11;";
			achievementsName +=  "\n" + getString(ach.getNames()[11]);
			extraRewards += ach.getRewards()[11];
		}
		if((level >= 20)  && (!oldCompletedAChievements.contains(";14;"))) {
			achievementsNumber += "14;";
			achievementsName +=  "\n" + getString(ach.getNames()[14]);
			extraRewards += ach.getRewards()[14];
		}
		if((level >= 25)  && (!oldCompletedAChievements.contains(";19;"))) {
			achievementsNumber += "19;";
			achievementsName +=  "\n" + getString(ach.getNames()[19]);
			extraRewards += ach.getRewards()[19];
		}
		String newCompletedAchievements = sharedPref.getString(getString(R.string.data_completed_achievements), "") + achievementsNumber;
		editor.putString(getString(R.string.data_completed_achievements), newCompletedAchievements);
		
		int newTotalCoins = sharedPref.getInt(getString(R.string.data_total_coins), 0)  + extraRewards;
		editor.putInt(getString(R.string.data_total_coins), newTotalCoins);
		
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
	
	public void goToMain(View view) {
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
		View view = getWindow().getDecorView().getRootView();
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
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
