package com.stmarys.learning;

/* 
* File Name:            CsharpSyntaxActivity.java  
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

public class CsharpSyntaxActivity extends Activity {
	private WebView csharpWebView;
	private String topicCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_csharp_syntax);
		initialization(); 
	} 
	@SuppressLint("NewApi") public void initialization()
	{
		topicCode = getIntent().getStringExtra("syntaxCode");
		csharpWebView = (WebView)findViewById(R.id.csharpWebView);
		csharpWebView.getSettings().setBuiltInZoomControls(true);
		csharpWebView.setBackgroundColor(Color.TRANSPARENT);
		//cppWebView.getSettings().setLoadWithOverviewMode(true);
		//cppWebView.getSettings().setUseWideViewPort(true); 
		csharpWebView.getSettings().setDisplayZoomControls(false);
		csharpWebView.loadUrl("file:///android_asset/csharptopic"+topicCode+".html");
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
