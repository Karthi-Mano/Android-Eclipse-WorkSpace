package com.stmarys.learning;

/* 
* File Name:            JavaSyntaxActivity.java  
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

public class JavaSyntaxActivity extends Activity {
	private WebView javaWebView;
	private String topicCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_java_syntax);
		initialization(); 
	} 
	@SuppressLint("NewApi") public void initialization()
	{
		
		topicCode = getIntent().getStringExtra("syntaxCode");
	
		javaWebView = (WebView)findViewById(R.id.javaWebView);
		javaWebView.getSettings().setBuiltInZoomControls(true);
		javaWebView.setBackgroundColor(Color.TRANSPARENT);
		javaWebView.getSettings().setDisplayZoomControls(false);
		javaWebView.loadUrl("file:///android_asset/javatopic"+topicCode+".html");
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
