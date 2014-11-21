package com.example.botsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
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
	
	public void openActivity(View view) {
		Intent intent = null;
		if(view.getId() == R.id.button_instructions) {
			intent = new Intent(this, Instructions.class);
		} else if(view.getId() == R.id.button_profile) {
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
