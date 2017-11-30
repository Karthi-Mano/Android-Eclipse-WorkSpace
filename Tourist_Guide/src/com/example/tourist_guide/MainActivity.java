package com.example.tourist_guide;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends Activity {
	Animation animation;
	Animation anim2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
     animation=AnimationUtils.loadAnimation(this,R.anim.animated);
     anim2=AnimationUtils.loadAnimation(this,R.anim.tilt);
     ImageView imageview = (ImageView)findViewById(R.id.imageViewCloud);
     imageview.startAnimation(animation);
    }

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
     getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void countriesList (View v)
    {
    	
    	v.startAnimation(anim2);
    	Intent intent=new Intent(this,CountryListActivity.class);
    	
    	startActivity(intent);
    	//this.overridePendingTransition(R.anim.flip,R.anim.flipout);
    }
    public void getInfo (View v)
    {
    	
    	v.startAnimation(anim2);
    	Intent intent=new Intent(this,InfoActivity.class);
    	
    	startActivity(intent);
    	//this.overridePendingTransition(R.anim.flip,R.anim.flipout);
    }
    
}
