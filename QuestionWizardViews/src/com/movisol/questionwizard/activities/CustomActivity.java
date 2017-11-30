package com.movisol.questionwizard.activities;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//import com.movisol.adsservice.client.BannerViewer;
import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.applicationcontroller.ScreenReceiver;
import com.movisol.questionwizard.entities.ChoiceButtonImageQuestion;
import com.movisol.questionwizard.views.controls.ChoiceImageQuestionView;

import com.movisol.tools.HelperUtils;

public class CustomActivity extends Activity {
	
	protected ApplicationController ac = ApplicationController.getInstance();
	protected Context context;

	//protected BannerViewer bw;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = ac.getContext();
		if(context == null)
			context = getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);  
		ac.setExit(true);
	}

	
	
		
	
	
	@Override
	protected void onPause() {
		
	
	    	   super.onPause();
	
		
		//if (bw != null)
		//	bw.stopLoadingAds();
		
//		ac.pauseApp();
		/*if(ac.getUsageClient() != null)
			ac.getUsageClient().pauseTracking();*/
	}
	
	


	@Override
	protected void onResume() {
		super.onResume();
		// initialize receiver
		/*if(ac.getFilter() == null)
		{
			IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			filter.addAction(Intent.ACTION_USER_PRESENT);
			ac.setFilter(filter);
		}
		
		if(ac.getmReceiver() == null)
			ac.setmReceiver(new ScreenReceiver());
			
		if(!ac.isReceiverRegistered())
			ac.registerReceiver();
		
		ac.setAppVisible(true);
	//	ac.setActualBannerViewer(bw);
		ac.setExit(true);
		
		if(ac.isSplashing())
			ac.setExit(false);
		*/
	//	ac.resumeApp();
		
		//Resume de los Ads
	//	if (bw != null)
		//	bw.resumeLoadingAds();
		
		/*if(ac.getUsageClient() != null)
			ac.getUsageClient().enableTracking(context);*/
			
		
		//Chequeamos si se ha cambiado el idioma dedse la ultima vez que se entr—
		if(ac.getLastLanguageUsed() == null)
		{
			ac.setLastLanguageUsed(HelperUtils.getDeviceLanguage());
			ac.setNeedAppToReboot(false);
		}
		else
		{
			if(!ac.getLastLanguageUsed().equals(HelperUtils.getDeviceLanguage()))
			{
				ac.setLoaded(false);
				ac.setNeedAppToReboot(true);
				
			}
		}

		
		
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/* Apa–o para que al estar presente un Ad, salir de la app con HOMEBUTTON 
	 * y volver a entrar, no quede un espacio negro en la parte superior*/
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    if (hasFocus) {
	        getWindow().getDecorView().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	            }
	        }, 500);
	    }
	}

	public void exit() 
	{
	/*	if (bw != null)
		{
			bw.stopLoadingAds();
			bw.stopTimer();
		}*/

//		ac.unregisterReceiver();
		//commented by Arun
		finish();

	}

	@Override
	protected void onUserLeaveHint() {
		/*ac.getUsageClient().detecHomeButtonPressed(context);*/
	}
	


}
