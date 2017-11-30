package com.movisol.questionwizard.views;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

//import com.movisol.adsservice.helper.AdsUtil;
import com.movisol.questionwizard.activities.CustomActivity;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.tools.HelperUtils;

public class MainPage extends CustomActivity implements OnClickListener,
		ScreenViewable {
	private static final int REQUEST_SPLASH = 1;
	private static final int REQUEST_SEND_MAIL = 2;

	private Button btnStart;
	private TextSwitcher ts;
	private boolean helpWebBodyShowed = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MainPage", "OnCreate");
		// Retrieves general information about the phone
		DisplayMetrics metrics = new DisplayMetrics();

		// Gets the metrics that give us the density information
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		ac.setDisplayMetrics(metrics);

		setContentView(R.layout.mainpage);
		/*
		 * Si no se han cargado los recursos en el aplication controller se
		 * llama a la vista splash para que los carge en background y cuando
		 * termine inicializa los controles,sino como ya estan cargados los
		 * recursos se inicializan controles
		 */
		ac.setSplashing(true);
		Intent splashView = new Intent(this, Splash.class);
		startActivityForResult(splashView, REQUEST_SPLASH);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SPLASH) {
			ac.setSplashing(false);
			//context = ac.getContext();
			//Added by Arun krishna For runtime locale Cahne issue Fix
			context=super.getApplicationContext();
			Log.d("context language selected  ", "the language"+ac.getLastLanguageUsed());
			initializeControls();
		}

	}
  
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (ac.isNeedAppToReboot() && !ac.isSplashing()) {
			ac.setSplashing(true);
			//ac.getUsageClient().enableTracking(context);
			Intent splashView = new Intent(this, Splash.class);
			startActivityForResult(splashView, REQUEST_SPLASH);
		} else if (ts != null
				&& ac.getLiteralsValueByKey("helpWebBody") != null) {
			ts.setText(Html.fromHtml(ac.getLiteralsValueByKey("homeWebBody")));
			btnStart.setText(ac.getLiteralsValueByKey("start"));
			helpWebBodyShowed = false;

		}
	}

	public void initializeControls() {

		final RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.mainPageMainLayout);

		rLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"backgroundhome", context));
        //rLayout.invalidate();
		// Initialize adBanner
		RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.mainPageAdLayout);
		adLayout.removeAllViews();
		if (!Boolean.parseBoolean(HelperUtils
				.getConfigParam("hideAds", context))) {
			//bw = AdsUtil.getBannerViewerForMainPage(context, ac.getSku(), this);
		//	adLayout.addView(bw);
		} else {
			ImageView logoBannerImageView = new ImageView(context);
			logoBannerImageView.setImageResource(HelperUtils
					.getDrawableResource("logobanner", context));
			adLayout.addView(logoBannerImageView);
		}
		// Start
		btnStart = (Button) findViewById(R.id.btnMainPageStart);
		btnStart.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_start", context));
		btnStart.getBackground().setDither(true);
		btnStart.setTextAppearance(context,
				HelperUtils.getStyleResource("btnStart", context));
		btnStart.setOnClickListener(this);
		btnStart.setText(ac.getLiteralsValueByKey("start"));

		// Settings
		if (Boolean.parseBoolean(HelperUtils.getConfigParam("ShowSettings",
				context))) {
			Button btnSettings = (Button) findViewById(R.id.btnSettings);
			btnSettings.setBackgroundResource(HelperUtils.getDrawableResource(
					"btn_settings", context));
			btnSettings.getBackground().setDither(true);
			btnSettings.setOnClickListener(this);
			btnSettings.setVisibility(View.VISIBLE);
		}

		// Share
		Button btnShare = (Button) findViewById(R.id.btnMainPageShare);
		btnShare.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_share", context));
		btnShare.setText(ac.getLiteralsValueByKey("share"));
		btnShare.setTextAppearance(context,
				HelperUtils.getStyleResource("btnShare", context));
		btnShare.setOnClickListener(this);

		/**
		 * Zed Changes starts to remove Share button for SFR
		 */

		if (Boolean.parseBoolean(HelperUtils.getConfigParam("hideShare",
				context))) {
			btnShare.setVisibility(View.INVISIBLE);
		}

		// Credits
		ImageButton btnCredits = (ImageButton) findViewById(R.id.btnMainPageCredits);
		btnCredits.setImageResource(HelperUtils.getDrawableResource(
				"smalllogomovisol", context));
		btnCredits.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_credits", context));
		btnCredits.setOnClickListener(this);

		/**
		 * Zed Changes starts to Align credits layout to center for SFR
		 */
		if (Boolean.parseBoolean(HelperUtils.getConfigParam("hideShare",
				context))) {
			RelativeLayout creditsLayout = (RelativeLayout) findViewById(R.id.relativeLayout4);
			creditsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			int px = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 12, getResources()
							.getDisplayMetrics());
			creditsLayout.setPadding(0, 0, 0, px);
		}

		// Body
		if (ts == null) {
			ts = (TextSwitcher) findViewById(R.id.textSwitcher1);
			// TextView tv = (TextView)
			// findViewById(R.id.mainText);//getLayoutInflater().inflate(HelperUtils.getDrawableResource("textviewmainpage",
			// context), null);
			// ts.addView(tv);//, 0, new
			// ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
			// ViewGroup.LayoutParams.WRAP_CONTENT));

			// ts.setFactory(new ViewSwitcher.ViewFactory() {
			// @Override
			// public View makeView() {
			//
			// return tv;
			// }
			// });
		}
		Animation in = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		Animation out = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);
		ts.setInAnimation(in);
		ts.setOutAnimation(out);
TextView mainTextView = (TextView) findViewById(R.id.textView1);
			mainTextView.setTextAppearance(context, HelperUtils.getStyleResource("homeBox", context));
		ts.setCurrentText(Html.fromHtml(ac.getLiteralsValueByKey("homeWebBody")));

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btnMainPageStart) {
			if (ac.getLiteralsValueByKey("helpWebBody") != null
					&& !helpWebBodyShowed) {
				ts.setText(Html.fromHtml(ac
						.getLiteralsValueByKey("helpWebBody")));
				btnStart.setText(ac.getLiteralsValueByKey("buttonReady"));
				helpWebBodyShowed = true;

			} else {
				// Starts a new test
				ac.setExit(false);
				ac.start();
				Intent testView = new Intent(this, Test.class);
				startActivity(testView);
			}
		} else if (v.getId() == R.id.btnMainPageCredits) {
			ac.setExit(false);
			// Goes to credits
			Intent creditsView = new Intent(this, Credits.class);
			startActivity(creditsView);
		} else if (v.getId() == R.id.btnMainPageShare) {
			ac.setExit(false);
			// Sends new mail
			sendMail();
		} else if (v.getId() == R.id.btnSettings) {
			ac.setExit(false);
			// Goes to settings
			Intent settingsView = new Intent(this, Settings.class);
			startActivity(settingsView);
		}
	}

	private void sendMail() {

		// HelperUtils.deleteExternalStoragePrivateFile(context,
		// "mailshare.jpg");
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String subject = ac.getLiteralsValueByKey("mailSubject");
		String emailtext = ac.getLiteralsValueByKey("mailBody");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		Spanned txt = Html.fromHtml(emailtext, null, null);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, txt);

		final PackageManager pm = getPackageManager();
		final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent,
				0);
		ResolveInfo best = null;
		for (final ResolveInfo info : matches)
			if (info.activityInfo.packageName.endsWith(".gm")
					|| info.activityInfo.name.toLowerCase().contains("gmail"))
				best = info;

		// Si ha encontrado el paquete de Gmail, abre el intent del composer
		// Gmail directamente
		if (best != null) {
			emailIntent.setClassName(best.activityInfo.packageName,
					best.activityInfo.name);
			startActivity(emailIntent);
		}
		// En caso de no encontrarlo, genera un Chooser para que el usuario
		// elija como desea mandar el email.
		else {
			startActivityForResult(
					Intent.createChooser(emailIntent, "Send mail"),
					REQUEST_SEND_MAIL);
		}

		// emailIntent.setType("plain/text");
		// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new
		// String[]{ addres});

		/*
		 * Spanned txt = Html.fromHtml(emailtext, new Html.ImageGetter() {
		 * 
		 * @Override public Drawable getDrawable(String source) {
		 * 
		 * return getResources().getDrawable(
		 * HelperUtils.getDrawableResource("mailshare", context));
		 * 
		 * } }, null);
		 */
		// Adjunta el mailshare creando un temporal en la sdcard (No se
		// recomienda)

		// if (!HelperUtils.hasExternalStoragePrivateFile(context,
		// "mailshare.jpg"))
		// if (HelperUtils.createExternalStoragePrivatelFile("mailshare.jpg",
		// "mailshare", context))
		// {
		// File f = new File(Environment.getExternalStorageDirectory(),
		// "mailshare.jpg");
		// emailIntent.putExtra(android.content.Intent.EXTRA_STREAM,
		// Uri.parse("file://" + f.getAbsolutePath()));
		// }

		// emailIntent.putExtra(Intent.EXTRA_STREAM,
		// Uri.parse("android.resource://" + getPackageName() + "/" +
		// HelperUtils.getDrawableResource("mailshare", context)));
		// emailIntent.setType("image/png");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// ac.setExit(true);
			//ac.getUsageClient().stop(context);
			exit();
			return true;
		} else
			return super.onKeyDown(keyCode, event);

	}

}
