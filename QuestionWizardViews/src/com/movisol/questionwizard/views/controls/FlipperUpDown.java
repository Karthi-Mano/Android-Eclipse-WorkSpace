package com.movisol.questionwizard.views.controls;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.movisol.questionwizard.views.R;

public class FlipperUpDown extends CustomFlipper{
	private Animation outToDown;
	private Animation fadeOut;
	private Animation inFromDown;
	private Animation fadeIn;
	public static final int TYPE_COME=0;
	public static final int TYPE_GONE=1;
	public FlipperUpDown(Context context) {
		super(context);
	
		outToDown = AnimationUtils.loadAnimation(context,R.anim.outtodown);
		fadeOut = AnimationUtils.loadAnimation(context,R.anim.fadeout);
		inFromDown = AnimationUtils.loadAnimation(context,R.anim.infromdown);
		fadeIn = AnimationUtils.loadAnimation(context,R.anim.fadein);
		
	}
	/**
	 * @return the outToDown
	 */
	public Animation getOutToDown() {
		return outToDown;
	}
	/**
	 * @param outToDown the outToDown to set
	 */
	public void setOutToDown(Animation outToDown) {
		this.outToDown = outToDown;
	}
	/**
	 * @return the fadeOut
	 */
	public Animation getFadeOut() {
		return fadeOut;
	}
	/**
	 * @param fadeOut the fadeOut to set
	 */
	public void setFadeOut(Animation fadeOut) {
		this.fadeOut = fadeOut;
	}
	/**
	 * @return the inFromDown
	 */
	public Animation getInFromDown() {
		return inFromDown;
	}
	/**
	 * @param inFromDown the inFromDown to set
	 */
	public void setInFromDown(Animation inFromDown) {
		this.inFromDown = inFromDown;
	}
	/**
	 * @return the fadeIn
	 */
	public Animation getFadeIn() {
		return fadeIn;
	}
	/**
	 * @param fadeIn the fadeIn to set
	 */
	public void setFadeIn(Animation fadeIn) {
		this.fadeIn = fadeIn;
	}
	public void setAnimationType(int Type){
		switch(Type){
			case TYPE_COME:
				setInAnimation(inFromDown);
				setOutAnimation(fadeOut);
				break;
			case TYPE_GONE:
				setInAnimation(fadeIn);
				setOutAnimation(outToDown);
				break;
		}
	}
	
	//TODO temporarily bug fixes to solve the "Receiver not registered: android.widget.ViewFlipper" error
	@Override
	protected void onDetachedFromWindow() {
	    try {
	        super.onDetachedFromWindow();
	    }
	    catch (IllegalArgumentException e) {
	        stopFlipping();
	    }
	}
}
