package com.example.tourist_guide;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TravelTipsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_tips);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel_tips, menu);
		return true;
	}

}
