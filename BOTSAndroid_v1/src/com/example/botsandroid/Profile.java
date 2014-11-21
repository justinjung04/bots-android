package com.example.botsandroid;

import android.app.Activity;
import android.os.Bundle;

public class Profile extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().hide();
	}

}
