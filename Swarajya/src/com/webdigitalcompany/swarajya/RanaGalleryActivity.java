package com.webdigitalcompany.swarajya;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RanaGalleryActivity extends Activity {

	private Button galleryBackButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rana_gallery);
		galleryBackButton = (Button)findViewById(R.id.galleryBackButton);
		galleryBackButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
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

