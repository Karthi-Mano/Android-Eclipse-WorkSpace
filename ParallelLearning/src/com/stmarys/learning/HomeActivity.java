package com.stmarys.learning;
/* 
* File Name:            HomeActivity.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/


import com.stmarys.learning.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener{
	private Button startButton, achieveButton,creditButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initialization();
	}

	public void initialization(){
		
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setOnClickListener(this);
		achieveButton = (Button) findViewById(R.id.achieveButton);
		achieveButton.setOnClickListener(this);
		creditButton  = (Button) findViewById(R.id.creditsButton);
		creditButton.setOnClickListener(this);
		
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
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId())
		{
		case R.id.startButton :
			intent = new Intent(this, TopicsListActivity.class);
			startActivity(intent);
		break;
		
		case R.id.achieveButton :
			intent = new Intent(this, AchievementActivity.class);
			startActivity(intent);
		break;
		
		case R.id.creditsButton :
			intent = new Intent(this, CreditsActivity.class);
			startActivity(intent);
		break;
		
		default:
		break;
		}
		
	}
}
