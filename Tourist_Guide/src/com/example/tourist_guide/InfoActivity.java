package com.example.tourist_guide;

import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class InfoActivity extends Activity {

	private TextView infoDescriptionTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		infoDescriptionTextView = (TextView) findViewById(R.id.des);
		infoDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
		return true;
	}

}
