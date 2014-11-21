package com.example.botsandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogBuy extends DialogFragment {
	private View view;
	private Skin skin;
	private int totalCoins;
	private int remainingBalance;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.dialog_buy, null);
	    
	    changeFont("cambria.ttf");
	    loadTexts();
	    
	    builder.setView(view);
	    return builder.create();
    }
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getActivity().getAssets(), newFont);
	    TextView textView1 = (TextView) view.findViewById(R.id.dialog_buy_text_title);
	    TextView textView2 = (TextView) view.findViewById(R.id.dialog_buy_text_current_balance);
	    TextView textView3 = (TextView) view.findViewById(R.id.dialog_buy_text_skin_cost);
	    TextView textView4 = (TextView) view.findViewById(R.id.dialog_buy_text_remaining_balance);
	    Button button1 = (Button) view.findViewById(R.id.dialog_buy_button_cancel);
	    Button button2 = (Button) view.findViewById(R.id.dialog_buy_button_buy);
		textView1.setTypeface(fontCambria);
		textView2.setTypeface(fontCambria);
		textView3.setTypeface(fontCambria);
		textView4.setTypeface(fontCambria);
		button1.setTypeface(fontCambria);
		button2.setTypeface(fontCambria);
	}
	
	private void loadTexts() {
		totalCoins = getArguments().getInt("totalCoins");
		String skinType = getArguments().getString("skinType");
		skin = new Skin(skinType);
		
		TextView textView1 = (TextView) view.findViewById(R.id.dialog_buy_text_title);
		textView1.setText(getString(R.string.buy) + " " + getString(R.string.skin) + " " + skinType + "?");
		
		TextView textView2 = (TextView) view.findViewById(R.id.dialog_buy_text_current_balance);
		textView2.setText(getString(R.string.current_balance) + getString(R.string.equals) + " " + totalCoins);
		
		TextView textView3 = (TextView) view.findViewById(R.id.dialog_buy_text_skin_cost);
		textView3.setText(getString(R.string.skin) + " " + getString(R.string.cost) + getString(R.string.equals) + " " + skin.getCost());
		
		TextView textView4 = (TextView) view.findViewById(R.id.dialog_buy_text_remaining_balance);
		remainingBalance = totalCoins - skin.getCost();
		textView4.setText(getString(R.string.remaining_balance) + getString(R.string.equals) + " " + remainingBalance);
		if(remainingBalance < 0) {
			textView4.setTextColor(Color.RED);
		}
	}
	
	public int getRemainingBalance() {
		return remainingBalance;
	}
}
