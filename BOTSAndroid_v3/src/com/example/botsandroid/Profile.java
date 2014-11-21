package com.example.botsandroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	private DialogSkin dialogSkin;
	private DialogName dialogName;
	private DialogBuy dialogBuy;
	private String username;
	private String availableSkins;
	private int totalCoins;
	private String newSkinType;
	private int[] skins = { R.string.skin_default, R.string.skin_guerrilla, R.string.skin_christmas }; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		changeFont("cambria.ttf");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		displayData();
		setNameClickable();
	}
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getAssets(), newFont);
		Button button = (Button) findViewById(R.id.profile_button_home);
		ArrayList<TextView> textViews = new ArrayList<TextView>();
		textViews.add((TextView) findViewById(R.id.profile_text_commander));
		textViews.add((TextView) findViewById(R.id.profile_text_username));
		textViews.add((TextView) findViewById(R.id.profile_text_total_coin_label));
		textViews.add((TextView) findViewById(R.id.profile_text_total_coin_value));
		textViews.add((TextView) findViewById(R.id.profile_text_most_coin_label));
		textViews.add((TextView) findViewById(R.id.profile_text_most_coin_value));
		textViews.add((TextView) findViewById(R.id.profile_text_highest_level_label));
		textViews.add((TextView) findViewById(R.id.profile_text_highest_level_value));
		textViews.add((TextView) findViewById(R.id.profile_text_skin));
		
		button.setTypeface(fontCambria);
		for(Iterator<TextView> it = textViews.iterator(); it.hasNext(); ) {
			TextView textView = it.next();
			textView.setTypeface(fontCambria);
		}
	}
	
	private void displayData() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		
		username = sharedPref.getString(getString(R.string.data_username), "");
		TextView nameView = (TextView) findViewById(R.id.profile_text_username);
		nameView.setText(username);
		
		totalCoins = sharedPref.getInt(getString(R.string.data_total_coins), 0);
		TextView totalCoinsView = (TextView) findViewById(R.id.profile_text_total_coin_value);
		totalCoinsView.setText(Integer.toString(totalCoins));
		RatingBar ratingBar = (RatingBar) findViewById(R.id.profile_rating_exp);
		ratingBar.setRating(totalCoins/1000f);
		
		int mostCoins = sharedPref.getInt(getString(R.string.data_most_coins), 0);
		TextView mostCoinsView = (TextView) findViewById(R.id.profile_text_most_coin_value);
		mostCoinsView.setText(Integer.toString(mostCoins));
		
		int highestLevel = sharedPref.getInt(getString(R.string.data_highest_level), 0);
		TextView highestLevelView = (TextView) findViewById(R.id.profile_text_highest_level_value);
		highestLevelView.setText(Integer.toString(highestLevel));
		
		String currentSkinType = sharedPref.getString(getString(R.string.data_selected_skin), "");
		updateSelectedSkin(currentSkinType);
		
		updateAvailableSkins();
	}
	
	private void updateAvailableSkins() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
		availableSkins = sharedPref.getString(getString(R.string.data_available_skins), "");
	}
	
	private void updateSelectedSkin(String skinType) {
		for(int i=0; i<skins.length; i++) {
			String skinName = "skin_" + getString(skins[i]).toLowerCase(Locale.CANADA);
			ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(skinName, "id", getPackageName()));
			imageView.setImageResource(getResources().getIdentifier(skinName, "drawable", getPackageName()));
		}
		
		String selectedSkinName = "skin_" + skinType.toLowerCase(Locale.CANADA);
		ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(selectedSkinName, "id", getPackageName()));
		Drawable[] layers = new Drawable[2];
		layers[0] = getResources().getDrawable(getResources().getIdentifier(selectedSkinName, "drawable", getPackageName()));
		layers[1] = getResources().getDrawable(R.drawable.selection);
		imageView.setImageDrawable(new LayerDrawable(layers));
	}

	private void setNameClickable() {
		TextView nameView = (TextView) findViewById(R.id.profile_text_username);
		nameView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialogName = new DialogName();
				Bundle args = new Bundle();
			    args.putString("username", username);
			    dialogName.setArguments(args);
				dialogName.show(getFragmentManager(), null);
			}
		});
	}
	
	public void changeUsername(View view) {
		if(view.getId() == R.id.dialog_name_button_apply) {
			String newUsername = dialogName.getUsername();
			if(newUsername.length() > 0) {
				SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
				Editor editor = sharedPref.edit();
				editor.putString(getString(R.string.data_username), newUsername);
				editor.commit();
				TextView nameView = (TextView) findViewById(R.id.profile_text_username);
				nameView.setText(newUsername);
			} else {
				Toast.makeText(this, getString(R.string.toast_username_error), Toast.LENGTH_SHORT).show();
				return;
			}
		}
		dialogName.dismiss();
	}
	
	public void openSkin(View view) {
		newSkinType = view.getTag().toString();
		openSkin(newSkinType);
	}
	
	private void openSkin(String newSkinType) {
		dialogSkin = new DialogSkin();
		Bundle args = new Bundle();
	    args.putString("skinType", newSkinType);
	    args.putBoolean("isOwned", availableSkins.contains(newSkinType));
	    dialogSkin.setArguments(args);
		dialogSkin.show(getFragmentManager(), null);
	}
	
	public void onButtonClick(View view) {
		Button button = (Button) view;
		String command = button.getText().toString();
		if(command.equals(getString(R.string.apply))) {
			SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit();
			editor.putString(getString(R.string.data_selected_skin), newSkinType);
			editor.commit();
			updateSelectedSkin(newSkinType);
		} else if(command.equals(getString(R.string.buy))) {
			dialogBuy = new DialogBuy();
			Bundle args = new Bundle();
		    args.putString("skinType", newSkinType);
		    args.putInt("totalCoins", totalCoins);
		    dialogBuy.setArguments(args);
		    dialogBuy.show(getFragmentManager(), null);
		    return;
		}
		dialogSkin.dismiss();
	}
	
	public void changeSkin(View view) {
		Button button = (Button) view;
		String command = button.getText().toString();
		if(command.equals(getString(R.string.yes))) {
			if(dialogBuy.getRemainingBalance() < 0) {
				Toast.makeText(this, getString(R.string.insufficient_balance), Toast.LENGTH_SHORT).show();
				return;
			}
			SharedPreferences sharedPref = getSharedPreferences(getString(R.string.data_title), Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit();
			String oldAvailableSkins = sharedPref.getString(getString(R.string.data_available_skins), "");
			String newAvailableSkins = oldAvailableSkins + ", " + newSkinType;
			editor.putString(getString(R.string.data_available_skins), newAvailableSkins);
			editor.putInt(getString(R.string.data_total_coins), dialogBuy.getRemainingBalance());
			editor.commit();
			dialogBuy.dismiss();
			dialogSkin.dismiss();
			displayData();
			openSkin(newSkinType);
		} 
		dialogBuy.dismiss();
	}
	
	public void goToMain(View view) {
		finish();
	}
}
