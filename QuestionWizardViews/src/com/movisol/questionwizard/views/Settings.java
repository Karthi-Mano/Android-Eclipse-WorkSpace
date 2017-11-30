package com.movisol.questionwizard.views;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.movisol.adsservice.helper.AdsUtil;
import com.movisol.questionwizard.activities.CustomActivity;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.tools.HelperUtils;

public class Settings extends CustomActivity implements ScreenViewable,OnClickListener {

	Button btnBack;
	Button btnImperial;
	Button btnMetric;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initializeControls();
		
		}
	@Override
	public void initializeControls() {
		 
		//Initialize adBanner
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.settingsPageAdLayout);
		adLayout.removeAllViews();
		if(!Boolean.parseBoolean(HelperUtils.getConfigParam("hideAds", context)))
		{
		//	bw = AdsUtil.getBannerViewerForSettings(context, ac.getSku(), this);
		//	adLayout.addView(bw);
		}else
		{
			ImageView logoBannerImageView = new ImageView(context);
			logoBannerImageView.setImageResource(HelperUtils.getDrawableResource("logobanner", context));
			adLayout.addView(logoBannerImageView);
		}
		
		//Initializes the credits page background
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.settingsPageMainLayout);
		rLayout.setBackgroundResource(HelperUtils.getDrawableResource("backgroundhome", getApplicationContext()));
		//Initializes Previous Button that leads us to the Main Page
		btnBack=(Button)  findViewById(R.id.btnReturnSettings);
		btnBack.setText(ac.getLiteralsValueByKey("back"));
		btnBack.setBackgroundResource(HelperUtils.getDrawableResource("btn_back_settings", getApplicationContext()));
		btnBack.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnBackSettings", getApplicationContext()));
		btnBack.setOnClickListener(this);
		
		btnImperial = (Button) findViewById(R.id.btnImperial);
		btnImperial.setText(ac.getLiteralsValueByKey("settingsImperialUnits"));
		btnImperial.setBackgroundResource(HelperUtils.getDrawableResource("btn_imperial", getApplicationContext()));
		btnImperial.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
		btnImperial.setOnClickListener(this);
		
		btnMetric = (Button) findViewById(R.id.btnMetric);
		btnMetric.setText(ac.getLiteralsValueByKey("settingsMetricUnits"));
		btnMetric.setBackgroundResource(HelperUtils.getDrawableResource("btn_metric", getApplicationContext()));
		btnMetric.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
		btnMetric.setOnClickListener(this);
		

		if(ac.isMetric())
		{
			btnMetric.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnitsSelected", getApplicationContext()));
			btnMetric.setSelected(true);
			btnImperial.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
			btnImperial.setSelected(false);
		}
		else
		{
			btnImperial.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnitsSelected", getApplicationContext()));
			btnImperial.setSelected(true);
			btnMetric.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
			btnMetric.setSelected(false);
		}
		
		//Initializes the copyright footer
		TextView tvTitle= (TextView) findViewById(R.id.tvSettingsTitle);
		tvTitle.setText(ac.getLiteralsValueByKey("settingsMeasureUnits"));		 
		//Loads the predefined style for the copyright footer
		tvTitle.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("settingsTitleStyle", getApplicationContext()));
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			ac.setExit(false);
			exit();
			return true;
		}
		else
			return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.btnReturnSettings)
		{
			ac.setExit(false);
			exit();
		} else if (v.getId() == R.id.btnImperial)
		{
			ac.setMetric(false);
			btnImperial.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnitsSelected", getApplicationContext()));
			btnImperial.setSelected(true);
			btnMetric.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
			btnMetric.setSelected(false);
			ac.readQuestions();
		} else if (v.getId() == R.id.btnMetric)
		{
			ac.setMetric(true);
			btnMetric.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnitsSelected", getApplicationContext()));
			btnMetric.setSelected(true);
			btnImperial.setTextAppearance(getApplicationContext(), HelperUtils.getStyleResource("btnUnits", getApplicationContext()));
			btnImperial.setSelected(false);
			ac.readQuestions();
		}
		
		
	}

}
