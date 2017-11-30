package com.movisol.questionwizard.views.controls;

import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.DateQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.tools.HelperUtils;


public class DateQuestionView extends LinearLayout implements ScreenViewable , OnDateChangedListener{
	
	private DateQuestion question;
	private DatePicker datePicker;
	private CompositeListener compositeListener;
	private Date currentDate;
	private boolean correctDate = false;

	private ApplicationController ac = ApplicationController.getInstance();
	
	public DateQuestionView(Context context) {
		super(context);
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
	}
		

	public void initialize (CompositeListener cl)
	{
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
	    li.inflate(R.layout.datequestion, this, true);
		
	    compositeListener = cl;
		currentDate = new Date();
		currentDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());
		
		datePicker = (DatePicker) findViewById(R.id.dpBirthdate);

		//Gets the actual question from the Singleton Application Controller
		question = (DateQuestion) ac.getActualQuestion();
		
		datePicker.init(question.getSelectedDate().getYear()+1900, question.getSelectedDate().getMonth(), question.getSelectedDate().getDate(), compositeListener);
		
		checkDate(question.getSelectedDate().getYear()+1900, question.getSelectedDate().getMonth(), question.getSelectedDate().getDate());
		//Adds the listener of this class to ours composite listeners
		compositeListener.addListener(this);
			
		//Adds the statement of the question to the current view
		TextView title = (TextView) findViewById(R.id.dateTvTitle);
		title.setText(question.getTitle());

		//Loads the predefined style for the title of the question
		title.setTextAppearance(getContext(), HelperUtils.getStyleResource("questionTitle", getContext()));

}

	public void setQuestion(DateQuestion question) {
		this.question = question;
	}

	
	public ViewGroup getLayout() {
		return this;
	}
	
	public boolean isCorrectDate() {
		return correctDate;
	}


	public void setCorrectDate(boolean correctDate) {
		this.correctDate = correctDate;
	}


	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		
		checkDate(year, monthOfYear, dayOfMonth);
		
		
	}


	private void checkDate(int year, int monthOfYear, int dayOfMonth) {
		
		
		//The year 0 is 1900
		Date tmp = new Date(year-1900, monthOfYear, dayOfMonth);
		
		if(tmp.getTime() >= currentDate.getTime())
			correctDate = false;
		else
			correctDate = true;
		
		question.setAnswered(false);
		
		if(correctDate)
		{
			question.setSelectedDate(tmp);
			question.setAnswered(true);
		}
		
	}

}