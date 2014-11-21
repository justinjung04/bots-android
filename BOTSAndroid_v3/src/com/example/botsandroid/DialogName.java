package com.example.botsandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogName extends DialogFragment {
	private View view;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.dialog_name, null);
	    
		String oldUsername = getArguments().getString("username");
		EditText editText = (EditText) view.findViewById(R.id.dialog_name_text_username_input);
		editText.setText(oldUsername);
		
		changeFont("cambria.ttf");
		
	    builder.setView(view);
	    return builder.create();
    }
	
	public String getUsername() {
		EditText editText = (EditText) view.findViewById(R.id.dialog_name_text_username_input);
		return editText.getText().toString();
	}
	
	private void changeFont(String newFont) {
		Typeface fontCambria = Typeface.createFromAsset(getActivity().getAssets(), newFont);
	    TextView textView = (TextView) view.findViewById(R.id.dialog_name_text_username_label);
	    Button button1 = (Button) view.findViewById(R.id.dialog_name_button_apply);
		Button button2 = (Button) view.findViewById(R.id.dialog_name_button_cancel);
		textView.setTypeface(fontCambria);
		button1.setTypeface(fontCambria);
		button2.setTypeface(fontCambria);
	}
}
