package com.movisol.questionwizard.views.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.RecyclerListener;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceButtonImageQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.tools.HelperUtils;


public class ChoiceButtonImageQuestionView extends LinearLayout implements OnClickListener, ScreenViewable {
	
	private ChoiceButtonImageQuestion question;
	private  List<Choice> choiceList;

	private List<ImageButton> buttonList;
	private ApplicationController ac = ApplicationController.getInstance();
	CompositeListener compositeListener;
	private CountDownTimer countDown;
	private long maxTime;
	private int remainingBonus;
	private TextView bonus;
	private long millisToFinish;
	private Context context;
	
	private int MAX_TIME_BONUS = -1;
	
	public ChoiceButtonImageQuestionView(Context context) {
		super(context);
		this.context = context;
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)context.getSystemService(infService);
		li.inflate(R.layout.choicebuttonimagequestion, this, true);		
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
		if(HelperUtils.getConfigParam("QWMaxTimeBonusPerQuestion", getContext()) != null)
			MAX_TIME_BONUS = Integer.parseInt(HelperUtils.getConfigParam("QWMaxTimeBonusPerQuestion", getContext()));
	}
		

	@SuppressWarnings("rawtypes")
	public void initialize (CompositeListener cl)
	{
		buttonList = new ArrayList<ImageButton>();
		choiceList = new ArrayList<Choice>();
		compositeListener = cl;
		
		//Gets the actual question from the Singleton Application Controller
		question = (ChoiceButtonImageQuestion) ac.getActualQuestion();

		//Adds the listener of this class to ours composite listeners
		compositeListener.addListener(this);

    
		if (question != null)
		{
			
			if(question.getBackground() != null)
			{
				setBackgroundResource(HelperUtils.getDrawableResource(question.getBackground(), context));
			}
			getBackground().setDither(true);
			
			if(question.getAnswerTime() != -1 && Boolean.parseBoolean(HelperUtils.getConfigParam("QWQuestionTimeEnabled", context)))
			{
				bonus = (TextView) findViewById(R.id.tvBonusImageButton);
			//	bonus.setVisibility(View.VISIBLE);
				bonus.setText("BONUS: "+MAX_TIME_BONUS);
				bonus.setTextColor(Color.RED);
				maxTime = (long)question.getAnswerTime();
				millisToFinish = maxTime*1000L+1500;
				
				newCountDown();
			}
			
			
			//Adds the statement of the question to the current view
			TextView title = (TextView) findViewById(R.id.choiceIBQtvTitle);
			//Girish
			//title.setTextColor(Color.parseColor("#920219"));			
			title.setText(question.getTitle());
			
			

			//Loads the predefined style for the title of the question
			//title.setTextAppearance(context, HelperUtils.getStyleResource("questionTitle", context));
			
			Iterator it = question.getChoices().iterator();
	
			int i = 0;
			Drawable imgDrawable = null;
			while (it.hasNext()) 
			{
				choiceList.add((Choice) it.next());
				ImageButton imgBtn = (ImageButton) findViewWithTag("ib"+(i+1));
		
				imgDrawable = null;
				
				try {
					imgDrawable = context.getResources().getDrawable(HelperUtils.getDrawableResource(choiceList.get(i).getImage(), context));
//					Bitmap bMap = BitmapFactory.decodeResource(getResources(), HelperUtils.getDrawableResource(choiceList.get(i).getImage(), context));
//					imgDrawable = new BitmapDrawable(HelperUtils.getRoundedCornerBitmap(bMap, 15));
					
				} catch (NotFoundException e) {
					Log.e("QuestionWizardViews", "ChoiceButtonImageQuestionView: "+choiceList.get(i).getImage()+" --> Not Found Exception");
				}
				
				imgBtn.setBackgroundDrawable(imgDrawable);
				imgBtn.setVisibility(View.VISIBLE);

				if(question.isAnswered() && question.getSelectedValue().getImage().equals(question.getChoices().get(i).getImage()))
					imgDrawable.setAlpha(140);
				else
					imgDrawable.setAlpha(255);

				buttonList.add(imgBtn);
				i++;
			}
			
		}
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		switch(visibility)
		{
		case View.VISIBLE:
			if(countDown == null && MAX_TIME_BONUS != -1)
			{
				newCountDown();
				countDown.start();
			}
		break;
		
		case View.GONE:
			if( MAX_TIME_BONUS != -1)
			{
				countDown.cancel();
				countDown = null;
			}
			
		break;
		}
	}

	private void newCountDown() {
		countDown = new CountDownTimer(millisToFinish, 1) {

		     public void onTick(long millisUntilFinished) {
		    	 millisToFinish = millisUntilFinished; 
		         float remainingProgress = (float)Math.floor((millisUntilFinished/(maxTime*1000f)*100));
		         remainingBonus = (int)(MAX_TIME_BONUS*remainingProgress/100f); 
		         
		         if(remainingBonus <= MAX_TIME_BONUS)
		        	 bonus.setText("BONUS: "+remainingBonus);
		     }

		     public void onFinish() {

		     }
		  };
		
	}

	public void setQuestion(ChoiceButtonImageQuestion question) {
		this.question = question;
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


	public List<ImageButton> getButtonList() {
		return buttonList;
	}


	public void setButtonList(List<ImageButton> buttonList) {
		this.buttonList = buttonList;
	}


	@Override
	public void onClick(View v) {
		if( MAX_TIME_BONUS != -1)
		{
			countDown.cancel();
			((ChoiceButtonImageQuestion)ac.getActualQuestion()).setRemainingBonus(remainingBonus);
		}
		for(int i = 0; i < buttonList.size(); i++)
		{
			buttonList.get(i).getBackground().setAlpha(255);
			//Once we have selected a choice, we look for the one that was clicked	
			if( buttonList.get(i).getTag().equals( ((ImageButton)v).getTag()))
			{
				ac.getActualQuestion().setAnswered(true);
				buttonList.get(i).getBackground().setAlpha(140);
				//Changes the background image of the button clicked
				((ChoiceButtonImageQuestion)ac.getActualQuestion()).setSelectedValue(choiceList.get(i));
				//If our choice has tip, then set it up into the question

				if(choiceList.get(i).getTip() != null && choiceList.get(i).getTip().length() > 0)
					((ChoiceButtonImageQuestion)ac.getActualQuestion()).setQuestionTip(choiceList.get(i).getTip());
			}
		}
		
	}

	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}
	
	public void addButtonsListener(OnClickListener listener){
		Iterator<ImageButton> it = getButtonList().iterator();
		
		while (it.hasNext()) {
			ImageButton button = (ImageButton) it.next();
			button.setOnClickListener(listener);
		}
	}

}