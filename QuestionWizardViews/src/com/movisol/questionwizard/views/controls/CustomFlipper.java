package com.movisol.questionwizard.views.controls;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ViewFlipper;

import com.movisol.tools.HelperUtils;

public class CustomFlipper extends ViewFlipper {

	View tempView;
	int pageFlipSound;
	int pageFinishSound;
	private Context context;

	public CustomFlipper(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void showNext() {
		super.showNext();
		playPageFlipSound();
		removeView(tempView);
	}

	@Override
	public void showPrevious() {
		// TODO Auto-generated method stub
		super.showPrevious();
		playPageFlipSound();
		removeView(tempView);
	}

	public void playPageFlipSound() {
		if(!Build.PRODUCT.equals("sdk")) //Evita un warning continuo en el emulador
			HelperUtils.playsound(context, HelperUtils.PAGEFLIP);
	}

	public void playFinishPageSound() {
		if(!Build.PRODUCT.equals("sdk"))//Evita un warning continuo en el emulador
			HelperUtils.playsound(context, HelperUtils.BELL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#addView(android.view.View)
	 */
	@Override
	public void addView(View child) {
		tempView = getCurrentView();
		super.addView(child);
	}

	/**
	 * @param pageflipSound
	 *            the pageflipSound to set
	 */
	public void setPageFlipSound(int pageFlipSound) {
		this.pageFlipSound = pageFlipSound;
	}

	/**
	 * @param pageFinishSound
	 *            the pageFinishSound to set
	 */
	public void setPageFinishSound(int pageFinishSound) {
		this.pageFinishSound = pageFinishSound;
	}
	
	public void initSoundPool()
	{		
		HelperUtils.initSoundPool(context,pageFlipSound, pageFinishSound);
	}


	// TODO temporarily bug fixes to solve the
	// "Receiver not registered: android.widget.ViewFlipper" error
	@Override
	protected void onDetachedFromWindow() {
		try {
			super.onDetachedFromWindow();
		} catch (IllegalArgumentException e) {
			stopFlipping();
		}
	}

}