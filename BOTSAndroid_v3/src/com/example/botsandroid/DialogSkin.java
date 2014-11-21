package com.example.botsandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogSkin extends DialogFragment {
	private View view;
	private Skin skin;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.dialog_skin, null);
	    
	    changeFont("cambria.ttf");
	    loadImages();
	    loadTexts();
	    
	    builder.setView(view);
	    return builder.create();
    }
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getActivity().getAssets(), newFont);
	    TextView textView1 = (TextView) view.findViewById(R.id.dialog_skin_text_title);
	    TextView textView2 = (TextView) view.findViewById(R.id.dialog_skin_text_availability);
	    TextView textView3 = (TextView) view.findViewById(R.id.dialog_skin_text_cost);
	    Button button1 = (Button) view.findViewById(R.id.dialog_skin_button_apply);
		Button button2 = (Button) view.findViewById(R.id.dialog_skin_button_cancel);
		textView1.setTypeface(fontCambria);
		textView2.setTypeface(fontCambria);
		textView3.setTypeface(fontCambria);
		button1.setTypeface(fontCambria);
		button2.setTypeface(fontCambria);
	}
	
	private void loadImages() {
		String skinType = getArguments().getString("skinType");
		skin = new Skin(skinType);
	    TextView textView = (TextView) view.findViewById(R.id.dialog_skin_text_title);
	    ImageView tankView = (ImageView) view.findViewById(R.id.dialog_skin_image_tank);
		ImageView bombView = (ImageView) view.findViewById(R.id.dialog_skin_image_bomb);
		ImageView coinView = (ImageView) view.findViewById(R.id.dialog_skin_image_coin);
		textView.setText(getString(R.string.skin) + " " + skinType);
		tankView.setImageResource(skin.getTankImages()[3]);
		bombView.setImageResource(skin.getBombImages()[0]);
		coinView.setImageResource(skin.getCoinImage());
	}
	
	private void loadTexts() {
		if(getArguments().getBoolean("isOwned")) {
			TextView textView = (TextView) view.findViewById(R.id.dialog_skin_text_availability);
			textView.setText(getString(R.string.owned));
			
			Button button = (Button) view.findViewById(R.id.dialog_skin_button_apply);
			button.setText(R.string.apply);
		} else {
			TextView textView1 = (TextView) view.findViewById(R.id.dialog_skin_text_availability);
			textView1.setText(getString(R.string.not_owned));
			
			TextView textView2 = (TextView) view.findViewById(R.id.dialog_skin_text_cost);
			textView2.setText(getString(R.string.cost) + getString(R.string.equals) + " " + skin.getCost());
			
			Button button = (Button) view.findViewById(R.id.dialog_skin_button_apply);
			button.setText(R.string.buy);
		}
	}
}
