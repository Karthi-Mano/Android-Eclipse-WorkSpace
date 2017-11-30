package com.stmarys.learning;
/* 
* File Name:            ResultsView.java  
* 
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class CppSyntaxScreenActivity extends Activity {
	private WebView cppWebView;
	private String topicCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cpp_syntax_screen);
		initialization(); 
	} 
	@SuppressLint("NewApi") public void initialization()
	{
		topicCode = getIntent().getStringExtra("syntaxCode");
		
		cppWebView = (WebView)findViewById(R.id.cppWebView);
		cppWebView.getSettings().setBuiltInZoomControls(true);
		cppWebView.setBackgroundColor(Color.TRANSPARENT);
		//cppWebView.getSettings().setLoadWithOverviewMode(true);
		//cppWebView.getSettings().setUseWideViewPort(true); 
		cppWebView.getSettings().setDisplayZoomControls(false);
		cppWebView.loadUrl("file:///android_asset/cpptopic"+topicCode+".html");
	}   

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 this.finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

	
}
