package com.movisol.questionwizard.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.tools.HelperUtils;

public class Splash extends Activity implements ScreenViewable {

	private final static int SPLASH_LOADING_BOX_ORIGIN_TOP = 0;
	protected ApplicationController ac = ApplicationController.getInstance();
	private boolean finish = false;
	private Context context;
	private String topicCode;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Splash", "OnCreate");
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ac.setExit(true);
		context = ac.getContext();
		//Commented by Arun krishna
		//context=super.getApplicationContext();
		setContentView(R.layout.splash);
		initializeControls();
		new AsyncLoadRaw().execute();
		
	}

	@Override
	protected void onStart() {

		super.onStart();
		// new AsyncLoadRaw().execute();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		ac.setExit(false);
		// If loading is finished, then finish the activity
		if (finish)
			finish();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			return false;
		} else
			return super.onKeyDown(keyCode, event);
	}

	private class AsyncLoadRaw extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ac.setQuestionsResourcesId(HelperUtils.getRawResource("appconfig"+topicCode,
					context));
			ac.setCommonsLiteralsResourceId(HelperUtils.getRawResource(
					"commonsliterals", context));
			ac.setLiteralsResourceId(HelperUtils.getRawResource("literals",
					context));
			ac.initialize();
			ac.setLastLanguageUsed(HelperUtils.getDeviceLanguage());
//Log.d("Current Device langauge",HelperUtils.getDeviceLanguage());
			// if(ac.getUsageClient() != null)
			// ac.getUsageClient().resumeTracking();
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ProgressBar pb = (ProgressBar) findViewById(R.id.splasPgbLoad);
			pb.setVisibility(View.INVISIBLE);
			TextView tvLoaded = (TextView) findViewById(R.id.txtLoaded);
			tvLoaded.setText(getString(R.string.tap_to_continue));
			ac.setNeedAppToReboot(false);
			finish = true;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		/*if (ac.getUsageClient() != null)
			ac.getUsageClient().pauseTracking();*/
	}

	@Override
	protected void onResume() {
		super.onResume();

		/*if (ac.getUsageClient() != null)
			ac.getUsageClient().enableTracking(context);*/

	}

	@Override
	public void initializeControls() {

		ImageView splash = (ImageView) findViewById(R.id.imageViewSplash);

		splash.setBackgroundResource(HelperUtils.getDrawableResource("splash",
				context));

		RelativeLayout rBottomLayout = (RelativeLayout) findViewById(R.id.splashBottomLayout);
		rBottomLayout.setBackgroundColor(Color.BLACK);
		rBottomLayout.setVisibility(View.VISIBLE);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		if (HelperUtils.getConfigParam("splashLoadingBoxOriginY", context) != null) {
			if (Integer.valueOf(HelperUtils.getConfigParam(
					"splashLoadingBoxOriginY", context)) == SPLASH_LOADING_BOX_ORIGIN_TOP)
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			else
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		} else {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}

		rBottomLayout.setLayoutParams(params);

		TextView tvLoaded = (TextView) findViewById(R.id.txtLoaded);
		tvLoaded.setText(getString(R.string.loading));
		topicCode = ac.getTopicCode();
	}

}
