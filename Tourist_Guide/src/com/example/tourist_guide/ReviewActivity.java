package com.example.tourist_guide;



import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ReviewActivity extends TabActivity {

	private String famousPlaceString1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent famousPlaceIntent1 = getIntent();
		 famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		
		setContentView(R.layout.activity_review);
		TabHost tabHost=getTabHost();
		
		TabSpec writeReview=tabHost.newTabSpec("WriteReview");
		writeReview.setIndicator("WriteReview");
		Intent writeIntent=new Intent(this,WriteYourReviewActivity.class);
		writeIntent.putExtra("FamPlace1", famousPlaceString1);
		writeReview.setContent(writeIntent);
		
		TabSpec readReview=tabHost.newTabSpec("ReadReview");
		readReview.setIndicator("ReadReview");
		Intent readIntent=new Intent(this,ReadReviewActivity.class);
		readIntent.putExtra("FamPlace1", famousPlaceString1);
		readReview.setContent(readIntent);
		
		tabHost.addTab(writeReview);
		tabHost.addTab(readReview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review, menu);
		return true;
	}
	
}
