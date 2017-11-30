package com.example.tourist_guide;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


public class DiscriptionActivity extends Activity {
private String famousPlaceString;
private Animation anim5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_discription);
	anim5=AnimationUtils.loadAnimation(this,R.anim.tilt);
	Intent famousPlaceIntent2 = getIntent();
	famousPlaceString = famousPlaceIntent2.getStringExtra("FamPlace");
	Toast.makeText(this, "Famous Selected is " + famousPlaceString, Toast.LENGTH_SHORT).show();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.discription, menu);
		return true;
	}
	
public void travelTipsDetails(View v){
		v.startAnimation(anim5);
//		Intent intent=new Intent(this,TravelTipsActivity.class);
//		this.overridePendingTransition(R.anim.flip,R.anim.flipout);
//		startActivity(intent);
		//String label="sai";
		//float latitude=27.1742f;
		//float longitude=78.0422f;
		//String uri="geo:"+latitude+","+longitude+"("+label+")";

		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:<43.7732>,<11.2560>?q=<43.7732><11.2560>(Lable+Name)"));
		this.overridePendingTransition(R.anim.flip,R.anim.flipout);
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(intent);
		
	}
public void festivalDetails(View v){
	v.startAnimation(anim5);
	Intent intent=new Intent(this,FestivalsActivity.class);
	intent.putExtra("FamPlace1", famousPlaceString);
	this.overridePendingTransition(R.anim.flip,R.anim.flipout);
	startActivity(intent);
}
public void hotelDetails(View v){
	v.startAnimation(anim5);
	Intent intent=new Intent(this,HotelsActivity.class);
	intent.putExtra("FamPlace1", famousPlaceString);
	this.overridePendingTransition(R.anim.flip,R.anim.flipout);
	startActivity(intent);
}
public void imageButtonPlace(View v){
	v.startAnimation(anim5);
	Intent intent=new Intent(this,AboutplaceActivity.class);
	intent.putExtra("FamPlace1", famousPlaceString);
	this.overridePendingTransition(R.anim.flip,R.anim.flipout);
	startActivity(intent);
}
public void reviewDetails(View v){
	v.startAnimation(anim5);
	
	Intent intent=new Intent(this,ReviewActivity.class);
	intent.putExtra("FamPlace1", famousPlaceString);
	this.overridePendingTransition(R.anim.flip,R.anim.flipout);
	startActivity(intent);

	}

}
