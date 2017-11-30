	package com.movisol.questionwizard.views;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

//import com.facebook.android.DialogError;
//import com.facebook.android.Facebook;
//import com.facebook.android.Facebook.DialogListener;
//import com.facebook.android.FacebookError;
//import com.movisol.adsservice.helper.AdsUtil;
import com.movisol.questionwizard.activities.CustomActivity;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.tools.HelperUtils;

public class Results extends CustomActivity implements ScreenViewable,
		OnClickListener {
	private MediaPlayer pageflipSound;

	private static final int RESULT_START = 2;
	private static final int RESULT_TEST = 1;
	//private Facebook facebook = null;
	private PopupWindow puw = null;
	private Button fbButton;
	private RelativeLayout rLayout;
	private String name;
	private SharedPreferences sharedPrefs;
	private Activity thiz;
	private String fields;
	private LinearLayout postLayout;
	private LinearLayout closeLayout;
	private LinearLayout cancelLayout;
	private LinearLayout buttonsLayout; 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thiz = this;
		// TODO esto se hace por el error reportado en varias apps, que da fallo
		// por NullPointer en
		// el la instanciación de MediaPlayer.create
		if (context == null)
			context = super.getApplicationContext();

		getWindow().setFormat(PixelFormat.RGBA_8888);
		setContentView(R.layout.results);
		pageflipSound = MediaPlayer.create(context, R.raw.pageflip);
		initializeControls();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ac.isNeedAppToReboot()) {
			setResult(RESULT_START);
			ac.setExit(false);
			exit();
		}
	}

	@Override
	public void initializeControls() {
		rLayout = (RelativeLayout) findViewById(R.id.resultPageMainLayout);
		rLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"background", context));

		// Initialize adBanner
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.resultPageAdLayout);
		adLayout.removeAllViews();
		if (!Boolean.parseBoolean(HelperUtils
				.getConfigParam("hideAds", context))) {
		//	bw = AdsUtil.getBannerViewerForResult(context, ac.getSku(), this);
			//adLayout.addView(bw);
		} else {
			ImageView logoBannerImageView = new ImageView(context);
			logoBannerImageView.setImageResource(HelperUtils
					.getDrawableResource("logobanner", context));
			adLayout.addView(logoBannerImageView);
		}
		FrameLayout bottomLayout = (FrameLayout) findViewById(R.id.viewResultPageGroupContainer);
		bottomLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"containeropacoresult", context));
		if (bottomLayout.getBackground() != null)
			bottomLayout.getBackground().setDither(true);

		LinearLayout layoutfacebook = (LinearLayout) findViewById(R.id.layoutFacebook);
		layoutfacebook.setBackgroundColor(Color.argb(128, 64, 64, 64));

		fbButton = (Button) findViewById(R.id.btnfacebook);
		fbButton.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_facebook", context));
		fbButton.setText(ac.getLiteralsValueByKey("shareOnFacebook"));
		fbButton.setTextSize(10);
		fbButton.setTextColor(Color.WHITE);
		fbButton.setOnClickListener(this);
		layoutfacebook.setPadding(0, 0, 0, 14);
		layoutfacebook.setGravity(Gravity.CENTER);

		/**
		 * Zed Changes starts to remove facebook access for SFR 
		 */
		if (Boolean.parseBoolean(HelperUtils.getConfigParam("hideFacebook",
				context))) {
			layoutfacebook.setVisibility(View.INVISIBLE);
		}
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.viewResultBottomLayout);
		layout.addView(ac.getResultClass());

		RelativeLayout rLayoutNavigBar = (RelativeLayout) findViewById(R.id.resultPageNavigBar);
		rLayoutNavigBar.setBackgroundResource(HelperUtils.getDrawableResource(
				"bottom", context));

		Button btnHome = (Button) findViewById(R.id.btnResultPageStart);
		btnHome.setText(ac.getLiteralsValueByKey("menu"));
		btnHome.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_menu", context));
		btnHome.setTextAppearance(context,
				HelperUtils.getStyleResource("btnNextEnabled", context));
		btnHome.setOnClickListener(this);

		Button btnPrev = (Button) findViewById(R.id.btnResultPageBack);
		btnPrev.setText(ac.getLiteralsValueByKey("previous"));
		btnPrev.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_previous", context));
		btnPrev.setTextAppearance(context,
				HelperUtils.getStyleResource("btnNextEnabled", context));
		btnPrev.setOnClickListener(this);

		if (HelperUtils.getConfigParam("QWNavigationDisabled ", context) != null) {
			if (Boolean.valueOf(HelperUtils.getConfigParam(
					"QWNavigationDisabled ", context)))
				btnPrev.setVisibility(View.INVISIBLE);
		}

	}

	class AsyncLogIn extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			logIn();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

			/*if (facebook.isSessionValid()) {
				showPopUp();
			} else {
				facebook.authorize(thiz, new String[] { "publish_stream" },
						Facebook.FORCE_DIALOG_AUTH, new DialogListener() {
							@Override
							public void onComplete(Bundle values) {
								String token = facebook.getAccessToken();
								long token_expires = facebook
										.getAccessExpires();
								sharedPrefs = PreferenceManager
										.getDefaultSharedPreferences(context);
								sharedPrefs
										.edit()
										.putLong("access_expires",
												token_expires).commit();
								sharedPrefs.edit()
										.putString("access_token", token)
										.commit();
								if (HelperUtils.checkConnectivity(context))
									postWall();
								else
									showNoConnectivityAlert();
							}

							@Override
							public void onFacebookError(FacebookError error) {
							}

							@Override
							public void onError(DialogError e) {
							}

							@Override
							public void onCancel() {
							}
						});

			}*/
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnResultPageStart) {
			setResult(RESULT_START);
			ac.setExit(false);
			exit();
		} else if (v.getId() == R.id.btnResultPageBack) {
			try{
			ac.setExit(false);
			setResult(RESULT_TEST);
			exit();
			pageflipSound.reset();
			pageflipSound = MediaPlayer.create(context, R.raw.pageflip);
			try {
				pageflipSound.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pageflipSound.start();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		} /*else if (v.getId() == R.id.btnfacebook) {
			facebook = new Facebook(HelperUtils.getConfigParam("FB_APP_ID",
					context));
			if (HelperUtils.checkConnectivity(context))
				new AsyncLogIn().execute();
			else
				showNoConnectivityAlert();
		}*/
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			if (HelperUtils.getConfigParam("QWNavigationDisabled ", context) != null) {
				if (Boolean.valueOf(HelperUtils.getConfigParam(
						"QWNavigationDisabled ", context))) {
					// Si est‡ deshabilitada la navegaci—n, se hace como si
					// puls‡ramos el bot—n para ir a la MainPage
					setResult(RESULT_START);
				}
				ac.setExit(false);
				exit();
			} else {
				ac.setExit(false);
				exit();
			}
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	public void showNoConnectivityAlert() {
		final PopupWindow alert = new PopupWindow(this);

		LinearLayout.LayoutParams lp = new LayoutParams();
		lp.width = LayoutParams.FILL_PARENT;
		lp.height = LayoutParams.FILL_PARENT;

		LinearLayout buttonsLayout = new LinearLayout(context);
		buttonsLayout.setGravity(Gravity.CENTER);
		buttonsLayout.setOrientation(LinearLayout.VERTICAL);
		buttonsLayout.setLayoutParams(lp);

		Button button = new Button(context);
		button.setText(ac.getLiteralsValueByKey("connectionrequiredfacebook"));
		button.setTextSize(16);
		button.setGravity(Gravity.CENTER);
		button.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_post_fb", context));
		button.setTextAppearance(context,
				HelperUtils.getStyleResource("btnPostWallFB", context));
		button.setPadding(2, 0, 2, 0);
		buttonsLayout.addView(button, 2 * rLayout.getMeasuredWidth() / 3,
				LayoutParams.WRAP_CONTENT);

		alert.setContentView(buttonsLayout);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});

		alert.showAtLocation(rLayout, Gravity.CENTER, 0, 0);
		alert.update(0, 0, rLayout.getMeasuredWidth(),
				5 * rLayout.getMeasuredHeight() / 6);
		alert.getBackground().setAlpha(192);

	}

	public void logIn() {

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String access_token = sharedPrefs.getString("access_token", null);
		Long expires = sharedPrefs.getLong("access_expires", -1);

		Date now = new Date();

	/*	if (access_token != null && expires != -1) {
			if (expires - now.getTime() < 0) {
				try {
					facebook.logout(context);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				facebook.setAccessToken(access_token);
				facebook.setAccessExpires(expires);
			}
		}
*/
	}

	class AsyncFbRequest extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			//Parameters to get the fields
 			Bundle parameters = new Bundle();
			parameters.putString("fields", "name");
		/*	
			try {
				fields = facebook.request("me", parameters);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try {
				// Retrieves the JSONObject from the fields
				JSONTokener jst = new JSONTokener(fields);
				JSONObject jso = (JSONObject) jst.nextValue();

				// Extract the name from the JSONObject
				name = jso.getString("name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			TextView tvName = new TextView(context);
			
			tvName.setText(name);
			tvName.setTypeface(Typeface.DEFAULT_BOLD);
			tvName.setTextSize(18);
			tvName.setGravity(Gravity.CENTER);
			tvName.setPadding(0, 5, 0, 30);

			buttonsLayout.addView(tvName, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);

			buttonsLayout.addView(postLayout, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);

			Button b1 = new Button(context);
			b1.setBackgroundResource(HelperUtils.getDrawableResource(
					"btn_void_fb", context));
			b1.setHeight(5);
			b1.setVisibility(View.INVISIBLE);

			buttonsLayout.addView(b1, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			buttonsLayout.addView(closeLayout, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);

			Button b2 = new Button(context);
			b2.setBackgroundResource(HelperUtils.getDrawableResource(
					"btn_void_fb", context));
			b2.setHeight(5);
			b2.setVisibility(View.INVISIBLE);

			buttonsLayout.addView(b2, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			buttonsLayout.addView(cancelLayout, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);

			puw.setContentView(buttonsLayout);

			puw.showAtLocation(rLayout, Gravity.CENTER, 0, 0);
			puw.update(0, 0, 8 * rLayout.getMeasuredWidth() / 10,
					6 * rLayout.getMeasuredHeight() / 10);
			puw.getBackground().setAlpha(0);
			fbButton.setEnabled(false);
		}

	}

	class AsyncFbLogOut extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			/*try {
				
				//facebook.logout(context);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			sharedPrefs.edit().putLong("access_expires", -1).commit();
            sharedPrefs.edit().putString("access_token", null).commit();

			puw.dismiss();
			fbButton.setEnabled(true);
		}
		
		
	}

	public void showPopUp() {
		puw = new PopupWindow(this);

		LinearLayout.LayoutParams lp = new LayoutParams();
		lp.width = LayoutParams.FILL_PARENT;
		lp.height = LayoutParams.FILL_PARENT;

		postLayout = new LinearLayout(context);
		closeLayout = new LinearLayout(context);
		cancelLayout = new LinearLayout(context);
		buttonsLayout = new LinearLayout(context);

		buttonsLayout.setGravity(Gravity.CENTER);
		buttonsLayout.setOrientation(LinearLayout.VERTICAL);
		buttonsLayout.setLayoutParams(lp);
		buttonsLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"sharebox", context));

		Button close = new Button(context);
		close.setText(ac.getLiteralsValueByKey("logout"));
		close.setTextSize(16);
		close.setGravity(Gravity.CENTER);
		close.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_closesession_fb", context));
		close.setTextAppearance(context,
				HelperUtils.getStyleResource("btnCloseSessionFB", context));

		Button post = new Button(context);
		post.setText(ac.getLiteralsValueByKey("publishResults"));
		post.setTextSize(16);
		post.setGravity(Gravity.CENTER);
		post.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_post_fb", context));
		post.setTextAppearance(context,
				HelperUtils.getStyleResource("btnPostWallFB", context));

		Button cancel = new Button(context);
		cancel.setText(ac.getLiteralsValueByKey("cancel"));
		cancel.setTextSize(16);
		cancel.setGravity(Gravity.CENTER);
		cancel.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_cancel_fb", context));
		cancel.setTextAppearance(context,
				HelperUtils.getStyleResource("btnCancelFB", context));

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AsyncFbLogOut().execute();
			}
		});

		post.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (HelperUtils.checkConnectivity(context))
					postWall();
				else
					showNoConnectivityAlert();
				puw.dismiss();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				puw.dismiss();
				fbButton.setEnabled(true);

			}
		});

		postLayout.addView(post, 2 * rLayout.getMeasuredWidth() / 3,
				LayoutParams.WRAP_CONTENT);
		postLayout.setOrientation(LinearLayout.VERTICAL);
		postLayout.setGravity(Gravity.CENTER);

		closeLayout.addView(close, 2 * rLayout.getMeasuredWidth() / 3,
				LayoutParams.WRAP_CONTENT);
		closeLayout.setOrientation(LinearLayout.VERTICAL);
		closeLayout.setGravity(Gravity.CENTER);

		cancelLayout.addView(cancel, 2 * rLayout.getMeasuredWidth() / 3,
				LayoutParams.WRAP_CONTENT);
		cancelLayout.setOrientation(LinearLayout.VERTICAL);
		cancelLayout.setGravity(Gravity.CENTER);

		postLayout.setWeightSum(1);
		closeLayout.setWeightSum(1);
		cancelLayout.setWeightSum(1);

		new AsyncFbRequest().execute();

	}

	private void postWall() {
		String name = "";
		String result = null;
		String caption = "";
		String imgSrc = "";
		String description = " ";
		String link = "";

		Bundle parameters = new Bundle();

		if (ac.getLiteralsValueByKey("fbName") != null)
			name = ac.getLiteralsValueByKey("fbName");

		if (ac.getLiteralsValueByKey("fbLink") != null)
			link = ac.getLiteralsValueByKey("fbLink");

		if (ac.getResultId() != null)
			result = String.valueOf(ac.getResultId());

		if (result != null) {
			if (ac.getLiteralsValueByKey(result + "FbCaption") != null)
				caption = ac.getLiteralsValueByKey(result + "FbCaption");
			if (caption.equals("")
					&& ac.getLiteralsValueByKey("fbCaption") != null)
				caption = ac.getLiteralsValueByKey("fbCaption");

			if (ac.getLiteralsValueByKey(result + "FbDescription") != null)
				description = ac
						.getLiteralsValueByKey(result + "FbDescription");
			if (description.equals(" ")
					&& ac.getLiteralsValueByKey("fbDescription") != null)
				description = ac.getLiteralsValueByKey("fbDescription");

			if (ac.getLiteralsValueByKey(result + "FbImageSrc") != null)
				imgSrc = ac.getLiteralsValueByKey(result + "FbImageSrc");

		}

		if (result == null || !ac.isImagePerCategory()) {
			if (ac.getLiteralsValueByKey("fbImageSrc") != null)
				imgSrc = ac.getLiteralsValueByKey("fbImageSrc");
			if (ac.getLiteralsValueByKey("R1FbImageSrc") != null)
				imgSrc = ac.getLiteralsValueByKey("R1FbImageSrc");

			if (caption.equals("")
					&& ac.getLiteralsValueByKey("fbCaption") != null)
				caption = ac.getLiteralsValueByKey("fbCaption");
			if (caption.equals("")
					&& ac.getLiteralsValueByKey("R1FbCaption") != null)
				caption = ac.getLiteralsValueByKey("R1FbCaption");

			if (description.equals(" ")
					&& ac.getLiteralsValueByKey("fbDescription") != null)
				description = ac.getLiteralsValueByKey("fbDescription");
			if (description.equals(" ")
					&& ac.getLiteralsValueByKey("R1FbDescription") != null)
				description = ac.getLiteralsValueByKey("R1FbDescription");

		}

		if (caption.indexOf("[[RESULT]]") != -1)
			caption = caption.replace("[[RESULT]]",
					String.valueOf(ac.getResultId()));
		if (description.indexOf("[[RESULT]]") != -1)
			description = description.replace("[[RESULT]]",
					String.valueOf(ac.getResultId()));
		if (caption.indexOf("[[RIGHTANSWERS]]") != -1)
			caption = caption.replace("[[RIGHTANSWERS]]",
					String.valueOf(ac.getRightAnswers()));
		if (caption.indexOf("[[TOTALQUESTIONS]]") != -1)
			caption = caption.replace("[[TOTALQUESTIONS]]", HelperUtils
					.getConfigParam("QWRandomizeQuestionCount", context));
		if (caption.indexOf("[[RECOMMENDEDWEIGHT]]") != -1)
			caption = caption.replace("[[RECOMMENDEDWEIGHT]]",
					String.valueOf(ac.getTmpVariableForResult()));
		if (caption.indexOf("[[LIFEEXPECTANCY]]") != -1)
			caption = caption.replace("[[LIFEEXPECTANCY]]",
					String.valueOf(ac.getTmpVariableForResult()));
		if (caption.indexOf("[[CURRENTSCORE]]") != -1)
			caption = caption.replace("[[CURRENTSCORE]]",
					String.valueOf(ac.getTmpVariableForResult()));
		if (description.indexOf("[[OTHERNAME]]") != -1)
			description = description.replace("[[OTHERNAME]]",
					String.valueOf(ac.getTmpVariableForResult()));

		JSONObject properties = new JSONObject();

		try {

			if (ac.getLiteralsValueByKey("fbMoreInfoText1") != null
					&& ac.getLiteralsValueByKey("fbMoreInfoLink1") != null) {
				JSONObject jsonFb = new JSONObject();
				jsonFb.put("text", ac.getLiteralsValueByKey("fbMoreInfoText1"));
				jsonFb.put("href", ac.getLiteralsValueByKey("fbMoreInfoLink1"));

				properties.put(ac.getLiteralsValueByKey("fbMoreInfoCaption1"),
						jsonFb);
			}

			if (ac.getLiteralsValueByKey("fbMoreInfoText2") != null
					&& ac.getLiteralsValueByKey("fbMoreInfoLink2") != null) {
				JSONObject jsonFb = new JSONObject();
				jsonFb.put("text", ac.getLiteralsValueByKey("fbMoreInfoText2"));
				jsonFb.put("href", ac.getLiteralsValueByKey("fbMoreInfoLink2"));

				properties.put(ac.getLiteralsValueByKey("fbMoreInfoCaption2"),
						jsonFb);
			}

			if (ac.getLiteralsValueByKey("fbMoreInfoText3") != null
					&& ac.getLiteralsValueByKey("fbMoreInfoLink3") != null) {
				JSONObject jsonFb = new JSONObject();
				jsonFb.put("text", ac.getLiteralsValueByKey("fbMoreInfoText3"));
				jsonFb.put("href", ac.getLiteralsValueByKey("fbMoreInfoLink3"));

				properties.put(ac.getLiteralsValueByKey("fbMoreInfoCaption3"),
						jsonFb);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		parameters.putString("name", name);
		parameters.putString("caption", caption);
		parameters.putString("description", description);
		parameters.putString("link", link);
		parameters.putString("picture", imgSrc);
		parameters.putString("properties", properties.toString());

		/*facebook.dialog(this, "feed", parameters, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
				error.toString();
			}

			@Override
			public void onError(DialogError e) {
				e.toString();
			}

			@Override
			public void onCancel() {
			}
		});*/

		fbButton.setEnabled(true);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//facebook.authorizeCallback(requestCode, resultCode, data);
	}

}
