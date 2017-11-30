package com.movisol.questionwizard.views.controls;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoicePickerQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.tools.HelperUtils;
import com.movisol.wheel.OnWheelChangedListener;
import com.movisol.wheel.OnWheelScrollListener;
import com.movisol.wheel.WheelView;
import com.movisol.wheel.adapters.ArrayWheelAdapter;


public class ChoicePickerQuestionView extends LinearLayout implements OnWheelChangedListener, ScreenViewable {
	
	private ChoicePickerQuestion choicePickerQuestion;
	private  List<Choice> choiceList;
    private List<WheelView> wheelViewList = null;
    private List<OnWheelScrollListener> scrollListenerList;
    private List<OnWheelChangedListener> changedListenerList;
    private boolean correctValues;
    
	private List<Button> buttonList;
	private ApplicationController ac = ApplicationController.getInstance();
	CompositeListener compositeListener;
	protected boolean wheelScrolling = false;
	
	public ChoicePickerQuestionView(Context context) {
		super(context);
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize (CompositeListener cl)
	{
		buttonList = new ArrayList<Button>();
		choiceList = new ArrayList<Choice>();
		compositeListener = cl;
		
		//Gets the actual question from the Singleton Application Controller
		choicePickerQuestion = (ChoicePickerQuestion) ac.getActualQuestion();

		//Adds the listener of this class to ours composite listeners
		compositeListener.addListener(this);
    
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
	    li.inflate(R.layout.choicepickerquestion, this, true);

		//Title of the question
		TextView title = (TextView) findViewById(R.id.pickerQtvTitle);

		int size = 0;
		//Switch to select the best xml to inflate with the title
		switch(ac.getDpi()){
	     case DisplayMetrics.DENSITY_LOW:
	    	 size = 20;
            break;
	     case DisplayMetrics.DENSITY_MEDIUM:
	    	 size = 20;
             break;
	     case DisplayMetrics.DENSITY_HIGH:
	    	 size = 28;
             break;
	     default :
	    	 size = 32;
	    	 break;
		}	
		title.setText(choicePickerQuestion.getTitle());
		
		//Loads the predefined style for the title of the question
		title.setTextAppearance(getContext(), HelperUtils.getStyleResource("questionTitle", getContext()));
		
		wheelViewList = new ArrayList<WheelView>();
		changedListenerList = new ArrayList<OnWheelChangedListener>();
		scrollListenerList = new ArrayList<OnWheelScrollListener>();

		LinearLayout wheelLayout = (LinearLayout) findViewById(R.id.pickersLayout);
		
		//Iterates over all the wheels to set them up
		for(int i = 0; i < choicePickerQuestion.getOptions().size(); i++)
		{

			WheelView tmpWheel = new WheelView(getContext());
			int from = 0;
			int to = 0;
			int increment = 1;
			int decimalDigits = 0;
			int width = choicePickerQuestion.getOptions().get(i).getWidth();
			int optionNumber = 0;
			int total = 0;
			int init = 0;
			String defaultValue = null;
			String wheelTitle = choicePickerQuestion.getOptions().get(i).getTitle();
			List<Object> listaValores = new ArrayList<Object>();
			List<Integer> listaIds = new ArrayList<Integer>();
			
			tmpWheel.setTitle(wheelTitle, size);

			if(choicePickerQuestion.getOptions().get(i).getRange() != null)
			{
				from = choicePickerQuestion.getOptions().get(i).getRange().getFrom();
				to = choicePickerQuestion.getOptions().get(i).getRange().getTo();
				increment = choicePickerQuestion.getOptions().get(i).getRange().getInterval();
				decimalDigits = choicePickerQuestion.getOptions().get(i).getRange().getDecimalDigits();

				//The total number of questions 
				total = (to-increment)/increment - from+1;
			}
			
			if(choicePickerQuestion.getOptions().get(i).getDefaultValue() != null && !choicePickerQuestion.getOptions().get(i).getDefaultValue().equals("0"))
				defaultValue = String.valueOf(Integer.parseInt(choicePickerQuestion.getOptions().get(i).getDefaultValue())-from);
			
			if(choicePickerQuestion.getOptions().get(i).getOption() != null && choicePickerQuestion.getOptions().get(i).getOption().size() > 0)
			{
				//How many individual options we have in the wheel
				optionNumber = choicePickerQuestion.getOptions().get(i).getOption().size();
				total += optionNumber;
				init += optionNumber;
				
				for(int j = 0; j < choicePickerQuestion.getOptions().get(i).getOption().size(); j++)
				{
					String optionTitle = choicePickerQuestion.getOptions().get(i).getOption().get(j).getTitle();
					int value = choicePickerQuestion.getOptions().get(i).getOption().get(j).getValue();
					listaValores.add(optionTitle);
					listaIds.add(value);
				}
			}
			
			if(choicePickerQuestion.getOptions().get(i).getRange() != null)
			{
				String tmp;
				for(int j = init; j < total; j++)
				{
					tmp = String.valueOf(from+(j-init)*increment);
					
					if(decimalDigits != 0)
						tmp = String.format("%0"+decimalDigits+"d",Integer.valueOf(tmp));

					listaValores.add(tmp);
					listaIds.add(j);
					
				}
			}
						
			tmpWheel.setViewAdapter(new ArrayWheelAdapter(getContext(), listaValores.toArray()));
			
			if(tmpWheel.getViewAdapter() instanceof ArrayWheelAdapter)
				((ArrayWheelAdapter)tmpWheel.getViewAdapter()).setTextSize(20);

			
			if(choicePickerQuestion.getOptions().get(i).getSelectedValue() != null && !choicePickerQuestion.getOptions().get(i).getSelectedValue().equals(""))
			{
				
				defaultValue = String.valueOf(listaValores.indexOf(choicePickerQuestion.getOptions().get(i).getSelectedValue()));
				tmpWheel.setCurrentItem(Integer.parseInt(defaultValue));
				correctValues = true;
			}
	        
			
			OnWheelChangedListener tmpChangedListener = new OnWheelChangedListener() {
		        public void onChanged(WheelView wheel, int oldValue, int newValue) {
		        	checkSelection();
		        }
		    };
		    
			changedListenerList.add(tmpChangedListener);
			tmpWheel.addChangingListener(tmpChangedListener);
			tmpWheel.addChangingListener(compositeListener);

			OnWheelScrollListener tmpScrolledListener = new OnWheelScrollListener() {
		        public void onScrollingStarted(WheelView wheel) {
		        	wheelScrolling  = true;
		        }
		        public void onScrollingFinished(WheelView wheel) {
		        	wheelScrolling = false;
		        }
		    };
		    
		    scrollListenerList.add(tmpScrolledListener);
		    tmpWheel.addScrollingListener(tmpScrolledListener);
		    tmpWheel.addScrollingListener(compositeListener);
			
			
			tmpWheel.setCyclic(false);
			tmpWheel.setInterpolator(new AnticipateOvershootInterpolator(0.0f));
			
			wheelViewList.add(tmpWheel);
			LinearLayout layout = new LinearLayout (getContext());
			DisplayMetrics metrics = ac.getDisplayMetrics();
			layout.addView(wheelViewList.get(i), metrics.widthPixels*((int)Math.floor(width*1.5f))/480, LayoutParams.WRAP_CONTENT);
			layout.setGravity(Gravity.CENTER);
			
			wheelLayout.addView(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
		}
		
		checkSelection();
		
	}
	
	//Checks if the selected values in all the wheels are correct. (no blank, or 0 values)
    @SuppressWarnings("rawtypes")
	private void checkSelection() 
    {
    	int cont = 0;
    	for(int i = 0; i < wheelViewList.size(); i++)
    	{
    		String tmp = ((ArrayWheelAdapter) wheelViewList.get(i).getViewAdapter()).getItemText(wheelViewList.get(i).getCurrentItem()).toString();
    		
    		choicePickerQuestion.getOptions().get(i).setSelectedValue(tmp);
    		
    		if(((ArrayWheelAdapter) wheelViewList.get(i).getViewAdapter()).getItemText(wheelViewList.get(i).getCurrentItem()).toString().equals("0") ||
    				((ArrayWheelAdapter) wheelViewList.get(i).getViewAdapter()).getItemText(wheelViewList.get(i).getCurrentItem()).toString().equals("00") ||
    				((ArrayWheelAdapter) wheelViewList.get(i).getViewAdapter()).getItemText(wheelViewList.get(i).getCurrentItem()).toString().equals(""))
    		{
    			cont++;
    		}
    	}
    	
    	//If the total numnber of 0's are the same to the size of the list, we have no correct values
    	if(cont == wheelViewList.size())
    	{
    		correctValues = false;
    		choicePickerQuestion.setAnswered(false);
    	}
    	else
    	{
        	correctValues = true;
        	choicePickerQuestion.setAnswered(true);
    	}
	}

	public void setQuestion(ChoicePickerQuestion question) {
		this.choicePickerQuestion = question;
	}

	
	public ViewGroup getLayout() {
		return this;
	}



	public List<Choice> getChoiceList() {
		return choiceList;
	}


	public void setChoiceList(List<Choice> choiceList) {
		this.choiceList = choiceList;
	}


	public List<Button> getButtonList() {
		return buttonList;
	}


	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
	}

	public boolean isCorrectValues() {
		return correctValues;
	}

	public void setCorrectValues(boolean correctValues) {
		this.correctValues = correctValues;
	}


	public boolean isWheelScrolling() {
		return wheelScrolling;
	}

	public void setWheelScrolling(boolean wheelScrolling) {
		this.wheelScrolling = wheelScrolling;
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
	}
	
	
	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}




}