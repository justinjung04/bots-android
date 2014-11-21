package com.example.botsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		if(sharedPref.getAll().isEmpty()) {
			Editor editor = sharedPref.edit();
			editor.putString(getString(R.string.data_username), "New User");
			editor.putInt(getString(R.string.data_total_coins), 0);
			editor.putInt(getString(R.string.data_most_coins), 0);
			editor.putInt(getString(R.string.data_highest_level), 0);
			editor.putString(getString(R.string.data_skin), "normal");
			editor.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private boolean backPressedOnce;
	
	@Override
	public void onBackPressed() {
	    if (backPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.backPressedOnce = true;
	    Toast.makeText(this, getString(R.string.message_press_back), Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	backPressedOnce = false;                       
	        }
	    }, 2500);
	} 
	
	public void openActivity(View view) {
		Intent intent = null;
		if(view.getId() == R.id.button_profile) {
			intent = new Intent(this, Profile.class);
		} else if(view.getId() == R.id.button_game) {
			intent = new Intent(this, Game.class);
			intent.putExtra("DEVICE_WIDTH", getDeviceWidth());
		}
		startActivity(intent);
	}
	
	private int getDeviceWidth() {
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    return dm.widthPixels;
	}
}
