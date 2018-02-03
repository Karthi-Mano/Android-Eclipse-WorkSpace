package com.stmarys.learning;

/* 
* File Name:            CreditsActivity.java  
*           
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import com.stmarys.learning.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class CreditsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credits, menu);
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	}
