package com.luxl.botsandroid;

import com.luxl.botsandroid.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityMain extends Activity {
	private boolean backPressedOnce;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		if(sharedPref.getAll().isEmpty()) {
			initializeData(sharedPref);
		}
		
		changeFont("cambria.ttf");
	}
	
	@Override
	public void onBackPressed() {
	    if (backPressedOnce) {
	        finish();
	        return;
	    }
	    this.backPressedOnce = true;
	    Toast.makeText(this, getString(R.string.toast_press_back), Toast.LENGTH_SHORT).show();
	    new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	backPressedOnce = false;                       
	        }
	    }, 2500);
	} 
	
	public void openActivity(View view) {
		Intent intent = null;
		if(view.getId() == R.id.main_button_start) {
			intent = new Intent(this, ActivityGame.class);
			intent.putExtra("deviceWidth", getDeviceWidth());
		} else if(view.getId() == R.id.main_button_profile) {
			intent = new Intent(this, ActivityProfile.class);
		} else {
			intent = new Intent(this, ActivityAchievements.class);
		}
		startActivity(intent);
	}
	
	private int getDeviceWidth() {
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    return dm.widthPixels;
	}
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getAssets(), newFont);
		Button button1 = (Button) findViewById(R.id.main_button_start);
		Button button2 = (Button) findViewById(R.id.main_button_profile);
		Button button3 = (Button) findViewById(R.id.main_button_achievements);
		button1.setTypeface(fontCambria);
		button2.setTypeface(fontCambria);
		button3.setTypeface(fontCambria);
	}
	
	private void initializeData(SharedPreferences sharedPref) {
		Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.data_username), getString(R.string.new_user));
		editor.putInt(getString(R.string.data_total_coins), 0);
		editor.putInt(getString(R.string.data_most_coins), 0);
		editor.putInt(getString(R.string.data_highest_level), 0);
		editor.putString(getString(R.string.data_selected_skin), getString(R.string.skin_default));
		editor.putString(getString(R.string.data_available_skins), getString(R.string.skin_default));
		editor.putString(getString(R.string.data_completed_achievements), "");
		editor.commit();
	}
}
