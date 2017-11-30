package com.webdigitalcompany.swarajya;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.*;


public class SplashActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Thread timer= new Thread()
		{
			public void run()
			{
				try
				{
					//Display for 3 seconds
					sleep(2000);
				}
				catch (InterruptedException e) 
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				finally
				{   
					startMainActivity();

				}
			}
		};
		timer.start();
	}


	public void startMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();	
	}

/*	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("ONTOUCH","OUTSIDE");
		if(event.getAction() == MotionEvent.ACTION_UP){
			Log.d("ONTOUCH","INSIDE");
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			this.finish();

		}
		return super.onTouchEvent(event);
	}*/

}
