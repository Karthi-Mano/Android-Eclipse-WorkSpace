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


public class CreditsActivity extends Activity implements OnClickListener{

	private TextView creditsTextView;
	private Button creditsBackButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		creditsTextView = (TextView)findViewById(R.id.creditsTextView);
		
		creditsTextView.setText(Html.fromHtml(getResources().getString(R.string.creditsText)));
		creditsBackButton=(Button)findViewById(R.id.creditsBackButton);
		creditsBackButton.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 this.finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.creditsBackButton :
			this.finish();
			break;

		default:
			break;
		}
	}
	
}
