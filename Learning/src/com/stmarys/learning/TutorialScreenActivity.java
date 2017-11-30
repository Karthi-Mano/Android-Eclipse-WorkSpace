package com.stmarys.learning;
/* 
* File Name:            TutorialScreenActivity.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import com.stmarys.quiz.Quizzz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TutorialScreenActivity extends Activity implements OnClickListener{
	
	private WebView tutorialWebView;
	private Button cppButton, javaButton, csharpButton,quizButton;
	private String topicCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_screen);
		initialization();
		
	}

	@SuppressLint("NewApi") public void initialization(){
		
		topicCode = getIntent().getStringExtra("topicNumber");
		
		tutorialWebView = (WebView)findViewById(R.id.tutorialWebView);
		tutorialWebView.getSettings().setBuiltInZoomControls(true);
		tutorialWebView.getSettings().setDisplayZoomControls(false);
		tutorialWebView.setBackgroundColor(Color.TRANSPARENT);
		tutorialWebView.loadUrl("file:///android_asset/tutorial"+topicCode+".html");
				
		cppButton = (Button)findViewById(R.id.cppButton);
		cppButton.setOnClickListener(this); 
		
		javaButton  = (Button)findViewById(R.id.javaButton);
		javaButton.setOnClickListener(this);
		 
		csharpButton  = (Button)findViewById(R.id.csharpButton);
		csharpButton.setOnClickListener(this);
		
		quizButton  = (Button)findViewById(R.id.quizButton);
		quizButton.setOnClickListener(this);
		
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
		
		Intent intentSyntax;
	
		switch(v.getId()){
		
			case R.id.cppButton:
				 intentSyntax = new Intent(this, CppSyntaxScreenActivity.class);
				 intentSyntax.putExtra("syntaxCode",topicCode);
				 startActivity(intentSyntax);
			break;
			case R.id.javaButton :
				 intentSyntax = new Intent(this, JavaSyntaxActivity.class);
				 intentSyntax.putExtra("syntaxCode",topicCode);
				 startActivity(intentSyntax);
			break;
			case R.id.csharpButton :
				 intentSyntax = new Intent(this, CsharpSyntaxActivity.class);
				 intentSyntax.putExtra("syntaxCode",topicCode);
				 startActivity(intentSyntax);
			break;
			case R.id.quizButton :
				 intentSyntax = new Intent(this, Quizzz.class);
				 intentSyntax.putExtra("syntaxCode",topicCode);
				 startActivity(intentSyntax);
			break;
			default:
			break;
	
	}
  }
}
