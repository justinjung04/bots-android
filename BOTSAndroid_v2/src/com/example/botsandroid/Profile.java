package com.example.botsandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().hide();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		displayData();
		setNameClickable();
	}
	
	private void displayData() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		
		String username = sharedPref.getString(getString(R.string.data_username), "");
		TextView nameView = (TextView) findViewById(R.id.text_profile_name);
		nameView.setText(username);
		
		int totalCoins = sharedPref.getInt(getString(R.string.data_total_coins), 0);
		TextView totalCoinsView = (TextView) findViewById(R.id.text_profile_total_coins);
		totalCoinsView.setText(Integer.toString(totalCoins));
		
		int mostCoins = sharedPref.getInt(getString(R.string.data_most_coins), 0);
		TextView mostCoinsView = (TextView) findViewById(R.id.text_profile_most_coins);
		mostCoinsView.setText(Integer.toString(mostCoins));
		
		int highestLevel = sharedPref.getInt(getString(R.string.data_highest_level), 0);
		TextView highestLevelView = (TextView) findViewById(R.id.text_profile_highest_level);
		highestLevelView.setText(Integer.toString(highestLevel));
		
		String tankType = sharedPref.getString(getString(R.string.data_skin), "");
		if(tankType.equals("normal")) {
			ImageView imageView = (ImageView) findViewById(R.id.skin_normal);
			imageView.setImageResource(R.drawable.tank_normal_selected);
		} else if(tankType.equals("guerilla")) {
			ImageView imageView = (ImageView) findViewById(R.id.skin_guerilla);
			imageView.setImageResource(R.drawable.tank_g_selected);
		}
	}
	
	private void setNameClickable() {
		TextView nameView = (TextView) findViewById(R.id.text_profile_name);
		nameView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				changeUserName();
			}
		});
	}
	
	private void changeUserName() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter user name:");
		
		final EditText input = new EditText(this);
		alert.setView(input);
		

		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String username= input.getText().toString();
				if(username.length() > 0) {
					TextView nameView = (TextView) findViewById(R.id.text_profile_name);
					nameView.setText(username);
					
					SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
					Editor editor = sharedPref.edit();
					editor.putString(getString(R.string.data_username), username);
					editor.commit();
				}
			}
		});

		alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {}
		});

		alert.show();
	}
	
	public void changeSkin(View view) {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		if(view.getId() == R.id.skin_normal) {
			editor.putString(getString(R.string.data_skin), "normal");
			
			ImageView normalImageView = (ImageView) findViewById(R.id.skin_normal);
			normalImageView.setImageResource(R.drawable.tank_normal_selected);
			
			ImageView guerillaImageView = (ImageView) findViewById(R.id.skin_guerilla);
			guerillaImageView.setImageResource(R.drawable.tank_g_right);
			
		} else if(view.getId() == R.id.skin_guerilla) {
			editor.putString(getString(R.string.data_skin), "guerilla");
			
			ImageView normalImageView = (ImageView) findViewById(R.id.skin_normal);
			normalImageView.setImageResource(R.drawable.tank_right);
			
			ImageView guerillaImageView = (ImageView) findViewById(R.id.skin_guerilla);
			guerillaImageView.setImageResource(R.drawable.tank_g_selected);
		}
		editor.commit();
	}
}
