package com.luxl.botsandroid;

import com.luxl.botsandroid.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogGameOver extends DialogFragment {
	private View view;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.dialog_gameover, null);
	    
	    changeFont("cambria.ttf");
	    loadTexts();
	    
	    builder.setView(view);
	    return builder.create();
    }
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getActivity().getAssets(), newFont);
	    TextView textView1 = (TextView) view.findViewById(R.id.dialog_gameover_text_title);
	    TextView textView2 = (TextView) view.findViewById(R.id.dialog_gameover_text_level);
	    TextView textView3 = (TextView) view.findViewById(R.id.dialog_gameover_text_coins);
	    TextView textView4 = (TextView) view.findViewById(R.id.dialog_gameover_text_achievements);
	    Button button1 = (Button) view.findViewById(R.id.dialog_gameover_button_home);
	    Button button2 = (Button) view.findViewById(R.id.dialog_gameover_button_share);
	    Button button3 = (Button) view.findViewById(R.id.dialog_gameover_button_retry);
		textView1.setTypeface(fontCambria);
		textView2.setTypeface(fontCambria);
		textView3.setTypeface(fontCambria);
		textView4.setTypeface(fontCambria);
		button1.setTypeface(fontCambria);
		button2.setTypeface(fontCambria);
		button3.setTypeface(fontCambria);
	}
	
	private void loadTexts() {
		int coins = getArguments().getInt("coins");
		int level = getArguments().getInt("level");
		int reward = getArguments().getInt("reward");
		String achievements = getArguments().getString("achievements");
		
		TextView textView1 = (TextView) view.findViewById(R.id.dialog_gameover_text_level);
		textView1.setText(getString(R.string.level_achieved) + ": " + level);
		
		TextView textView2 = (TextView) view.findViewById(R.id.dialog_gameover_text_coins);
		textView2.setText(getString(R.string.coins_collected) + ": " + coins);
		
		TextView textView3 = (TextView) view.findViewById(R.id.dialog_gameover_text_achievements);
		String achievementString = getString(R.string.extra_rewards) + ": " + reward;
		if(reward != 0) {
			achievementString += "\n\n<" + getString(R.string.completed_achievements) + ">";
			achievementString += achievements;
		}
		
		textView3.setText(achievementString);
	}
}
