package com.movisol.questionwizard.views.listeners;

import java.util.ArrayList;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView.OnEditorActionListener;

import com.movisol.wheel.OnWheelChangedListener;
import com.movisol.wheel.OnWheelScrollListener;
import com.movisol.wheel.WheelView;

public class CompositeListener implements OnClickListener, OnWheelChangedListener, OnWheelScrollListener, OnDateChangedListener, OnEditorActionListener{
	
	//This list will contains all the listeners registered
	List<OnClickListener> onClickListeners = new ArrayList<OnClickListener>();
	List<OnWheelChangedListener> onWheelChangedListeners = new ArrayList<OnWheelChangedListener>();
	List<OnWheelScrollListener> onWheelScrollListener = new ArrayList<OnWheelScrollListener>();
	List<OnDateChangedListener> onDateChangedListener = new ArrayList<OnDateChangedListener>();
	List<OnEditorActionListener> onEditorActionListener = new ArrayList<OnEditorActionListener>();
	
	public void addListener(OnClickListener listener)
	{
		onClickListeners.add(listener);
	}
	
	public void addListener(OnWheelChangedListener listener)
	{
		onWheelChangedListeners.add(listener);
	}
	
	public void addListener(OnWheelScrollListener listener)
	{
		onWheelScrollListener.add(listener);
	}
	
	public void addListener(OnDateChangedListener listener)
	{
		onDateChangedListener.add(listener);
	}
	
	public void addListener(OnEditorActionListener listener)
	{
		onEditorActionListener.add(listener);
	}
	
	//Overrides the method in order to run each listener registered
	@Override
	public void onClick(View v) 
	{
		for(OnClickListener listener:onClickListeners)
		{
				listener.onClick(v);
		}
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) 
	{
		for(OnWheelChangedListener listener:onWheelChangedListeners)
		{
			listener.onChanged(wheel, oldValue, newValue);

		}
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
		
		for(OnWheelScrollListener listener:onWheelScrollListener)
		{
			listener.onScrollingStarted(wheel);

		}
		
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		
		for(OnWheelScrollListener listener:onWheelScrollListener)
		{
			listener.onScrollingFinished(wheel);

		}
		
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		for(OnDateChangedListener listener:onDateChangedListener)
		{
			listener.onDateChanged(view, year, monthOfYear, dayOfMonth);

		}
		
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		for(OnEditorActionListener listener:onEditorActionListener)
		{
			if(event != null)
				switch(event.getAction())
				{	
					case KeyEvent.ACTION_UP:
						listener.onEditorAction(v, actionId, event);
					break;
				}
		}
		return true;
	}


}
