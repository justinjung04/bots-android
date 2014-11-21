package com.luxl.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;

import com.luxl.botsandroid.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityAchievements extends Activity {
	Achievements ach;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievements);
		
		ach = new Achievements();
		changeFont("cambria.ttf");
		updateData();
	}
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getAssets(), newFont);
		ArrayList<TextView> textViews = new ArrayList<TextView>();
		textViews.add((TextView) findViewById(R.id.achievements));
		for(int i=0; i<ach.getIds().length; i++) {
			textViews.add((TextView) findViewById(ach.getIds()[i]));
		}
		for(Iterator<TextView> it = textViews.iterator(); it.hasNext(); ) {
			TextView textView = it.next();
			textView.setTypeface(fontCambria);
		}
		Button button = (Button) findViewById(R.id.achievement_button_home);
		button.setTypeface(fontCambria);
	}
	
	private void updateData() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		String completedAchievements = sharedPref.getString(getString(R.string.data_completed_achievements), "");
		for(int i=0; i<24; i++) {
			if(completedAchievements.contains(";" + i + ";")) {
				TextView textView1 = (TextView) findViewById(ach.getIds()[(i * 2)]);
				TextView textView2 = (TextView) findViewById(ach.getIds()[(i * 2) + 1]);
				textView1.setTextColor(Color.parseColor("#FFD700"));
				textView2.setTextColor(Color.parseColor("#FFD700"));
			}
		}
	}
	
	public void goToMain(View view) {
		finish();
	}
}
