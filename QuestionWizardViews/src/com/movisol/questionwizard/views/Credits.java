package com.movisol.questionwizard.views;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.movisol.adsservice.helper.AdsUtil;
import com.movisol.questionwizard.activities.CustomActivity;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.tools.HelperUtils;

public class Credits extends CustomActivity implements ScreenViewable,
		OnClickListener {

	Button btnBack;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		initializeControls();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ac.isNeedAppToReboot()) {
			exit();
		}
	}

	@SuppressLint("NewApi") @Override
	public void initializeControls() {

		// Initialize adBanner
		RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.creditsPageAdLayout);
		adLayout.removeAllViews();
		if (!Boolean.parseBoolean(HelperUtils
				.getConfigParam("hideAds", context))) {
		//	bw = AdsUtil.getBannerViewerForCredits(context, ac.getSku(), this);
		//	adLayout.addView(bw);
		} else {
			ImageView logoBannerImageView = new ImageView(context);
			logoBannerImageView.setImageResource(HelperUtils
					.getDrawableResource("logobanner", context));
			adLayout.addView(logoBannerImageView);
		}

		// Initializes the credits page background
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.creditsPageRealativeLayoutBackground);
		rLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"backgroundcredits", getApplicationContext()));

		RelativeLayout tabLayout = (RelativeLayout) findViewById(R.id.tabLayout);
		tabLayout.setBackgroundResource(HelperUtils.getDrawableResource("tab",
				getApplicationContext()));

		// Initializes Previous Button that leads us to the Main Page
		btnBack = (Button) findViewById(R.id.btnCreditsPageBack);
		btnBack.setText(ac.getLiteralsValueByKey("back"));
		btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_back_credits", getApplicationContext()));
		btnBack.setTextAppearance(getApplicationContext(), HelperUtils
				.getStyleResource("btnBackCredits", getApplicationContext()));
		btnBack.setOnClickListener(this);

		// Builds the credits description appending the required html code to be
		// able to read the CSS styles
		WebView webView = (WebView) findViewById(R.id.creditsPageWebLayout);
		webView.setBackgroundColor(Color.TRANSPARENT);
		
		//webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body><head>");
		sb.append(ac.getLiteralsValueByKey("styleCSS"));
		sb.append("</head>");

		switch (getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK) {
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			sb.append("<div id=\"aboutBoxBig\">");
			break;
		case 4:// Configuration.SCREENLAYOUT_SIZE_XLARGE:
			sb.append("<div id=\"aboutBoxBig\">");
			break;
		default:
			sb.append("<div id=\"aboutBox\">");
			break;
		}

		sb.append(ac.getLiteralsValueByKey("creditsWebBody"));

		/**
		 * Zed Changes starts to remove External link for SFR
		 */

		if (!Boolean.parseBoolean(HelperUtils.getConfigParam("hideCreditsLink",
				context))) {
			//sb.append(ac.getLiteralsValueByKey("moreAppsLocalHTML"));

		}
		
		sb.append("</div>");
		sb.append("</body></html>");
		webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8",
				null);

		// Initializes the copyright footer
		TextView txtCopyRight = (TextView) findViewById(R.id.txtPageCreditsCopyRight);
		txtCopyRight.setText(ac.getLiteralsValueByKey("copyrightFooter"));

		// Loads the predefined style for the copyright footer
		 txtCopyRight.setTextAppearance(getApplicationContext(),
		 HelperUtils.getStyleResource("copyrightFooterStyle",
		 getApplicationContext()));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			ac.setExit(false);
			exit();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		ac.setExit(false);
		exit();
	}

}
