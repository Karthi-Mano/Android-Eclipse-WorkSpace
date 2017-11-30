package com.stmarys.learning;

/* 
* File Name:            ProgramActivity.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class ProgramActivity extends Activity implements OnClickListener{
	private WebView programWebView;
	private String topicCode;
	private Button tutorialButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program);
		initialization();
	}


	@SuppressLint("NewApi") public void initialization()
	{
		tutorialButton = (Button)findViewById(R.id.homeButton);
		tutorialButton.setOnClickListener(this);
		topicCode = getIntent().getStringExtra("topicCode");
		programWebView = (WebView)findViewById(R.id.programWebView);
		programWebView.getSettings().setBuiltInZoomControls(true);
		programWebView.setBackgroundColor(Color.TRANSPARENT);
		//cppWebView.getSettings().setLoadWithOverviewMode(true);
		//cppWebView.getSettings().setUseWideViewPort(true); 
		programWebView.getSettings().setDisplayZoomControls(false);
		programWebView.loadUrl("file:///android_asset/programs/program"+topicCode+".html");
	}

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
			switch(v.getId()){

			case R.id.homeButton:
				intent = new Intent(this, TopicsListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("EXIT", true);
				startActivity(intent);
				//startActivity(intent);
				finish();
				break;

			}   

		}
	}