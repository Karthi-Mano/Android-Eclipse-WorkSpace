package com.webdigitalcompany.swarajya;

import android.app.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class  ShivajiDescriptionActivity extends  Activity  {

	private TextView shTextView;
	private Button shBackButton;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shivaji_description);
		shTextView = (TextView)findViewById(R.id.shTextView);
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/rana.ttf");
		shTextView.setTypeface(face);
		shTextView.setText(Html.fromHtml(getResources().getString(R.string.description)));
		//shBackButton=(Button)findViewById(R.id.shBackButton);
		//shBackButton.setOnClickListener(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 this.finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	/*@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.shBackButton :
			this.finish();
			break;

		default:
			break;
		}
	}*/
}
