package com.movisol.questionwizard.views.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.tools.HelperUtils;


public class ChoiceQuestionView extends LinearLayout implements OnClickListener, ScreenViewable {
	
	private static final String CHOICEA = "choicea";
	private static final String CHOICEB = "choiceb";
	private static final String CHOICEC = "choicec";
	private static final String CHOICED = "choiced";
	private static final String CHOICEE = "choicee";
	private static final String CHOICEF = "choicef";
	private static final String CHOICEG = "choiceg";
	private static final String CHOICEH = "choiceh";
	private static final String CHOICEI = "choicei";
	private String[] choicesImagesList = {CHOICEA,CHOICEB,CHOICEC,CHOICED,CHOICEE,CHOICEF,CHOICEG,CHOICEH,CHOICEI};
	
	private static final String CHOICEAS = "choiceaselected";
	private static final String CHOICEBS = "choicebselected";
	private static final String CHOICECS = "choicecselected";
	private static final String CHOICEDS = "choicedselected";
	private static final String CHOICEES = "choiceeselected";
	private static final String CHOICEFS = "choicefselected";
	private static final String CHOICEGS = "choicegselected";
	private static final String CHOICEHS = "choicehselected";
	private static final String CHOICEIS = "choiceiselected";
	
	
	private String[] selectedChoicesImagesList = {CHOICEAS,CHOICEBS,CHOICECS,CHOICEDS,CHOICEES,CHOICEFS,CHOICEGS,CHOICEHS,CHOICEIS};
	private ChoiceQuestion question;
	private  List<Choice> choiceList;

	private List<Button> buttonList;
	private ApplicationController ac = ApplicationController.getInstance();
	private boolean horizontally = Boolean.parseBoolean(HelperUtils.getConfigParam("QWChoicesHorizontallyOriented", getContext()));
	CompositeListener compositeListener;
	
	public ChoiceQuestionView(Context context) {
		super(context);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
		
		if(!horizontally)
			li.inflate(R.layout.choicequestion, this, true);
		else
			li.inflate(R.layout.choicequestion_horizontal, this, true);
		
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
}
		

	@SuppressLint("NewApi") @SuppressWarnings("rawtypes")
	public void initialize (CompositeListener cl)
	{
		buttonList = new ArrayList<Button>();
		choiceList = new ArrayList<Choice>();
		compositeListener = cl;
		
		//Gets the actual question from the Singleton Application Controller
		question = (ChoiceQuestion) ac.getActualQuestion();

		//Adds the listener of this class to ours composite listeners
		compositeListener.addListener(this);

    
		if (question != null)
		{
			if(question.getBackground() != null)
			{
				setBackgroundResource(HelperUtils.getDrawableResource(question.getBackground(), getContext()));
			}
			getBackground().setDither(true);
			//Adds the statement of the question to the current view
			WebView title = (WebView) findViewById(R.id.tvTitle);
			title.getSettings().setBuiltInZoomControls(true);
			title.getSettings().setDisplayZoomControls(false);
			title.setBackgroundColor(Color.TRANSPARENT);
			title.loadUrl("file:///android_asset/html_quizz/"+ question.getTitle()+".html");
		
			/*title.setText(Html.fromHtml(question.getTitle()));
			title.setMovementMethod(new ScrollingMovementMethod());
			
			//Loads the predefined style for the title of the question
			title.setTextAppearance(getContext(), HelperUtils.getStyleResource("questionTitle", getContext()));
			*/
			Iterator it = question.getChoices().iterator();
	
			int i = 0;
			Button choiceTmp = (Button) findViewById(R.id.btChoice);
			LinearLayout choicesLayout = (LinearLayout) findViewById(R.id.choicesLayout);
			while (it.hasNext()) 
			{
				choiceList.add((Choice) it.next());
				
				Button choiceBtn = new Button(getContext());
			    
			    
				choiceBtn.setGravity(choiceTmp.getGravity());
				
				choiceBtn.setLayoutParams(choiceTmp.getLayoutParams());
				choiceBtn.setText(choiceList.get(choiceList.size()-1).getTitle());
				
				choiceBtn.setBackgroundResource(HelperUtils.getDrawableResource(choicesImagesList[i], getContext()));
				//Sets the predefined style for the Button
				choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("choiceTitleNormal", getContext()));
				
				if(choiceBtn.getText().length() > 38)
				{
					//Sets the small style for the Button				
					choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("choiceTitleSmall", getContext()));
				}
				
				
				if(question.isAnswered() && question.getSelectedValue().getTitle().equals(choiceBtn.getText()))
				{
					choiceBtn.setBackgroundResource(HelperUtils.getDrawableResource(selectedChoicesImagesList[i], getContext()));
					choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoiceSelected", getContext()));
				}
				choiceBtn.setTransformationMethod(null);
				choiceBtn.setPadding(choiceTmp.getPaddingLeft(), 0, choiceTmp.getPaddingRight(), 0);			
			
				buttonList.add(choiceBtn);
				choicesLayout.addView(choiceBtn);
				i++;
			}
			
			choicesLayout.removeViewAt(0);
		}
}

	public void setQuestion(ChoiceQuestion question) {
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


	public List<Button> getButtonList() {
		return buttonList;
	}


	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
	}


	@Override
	public void onClick(View v) {
		
		for(int i = 0; i < choiceList.size(); i++)
		{
			buttonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource(choicesImagesList[i], getContext()));
			buttonList.get(i).setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoice", getContext()));
			//Once we have selected a choice, we look for the one that was clicked	
			if( choiceList.get(i).getTitle().equals( ((Button)v).getText()))
			{
				ac.getActualQuestion().setAnswered(true);
				//Changes the background image of the button clicked
				buttonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource(selectedChoicesImagesList[i], getContext()));
				buttonList.get(i).setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoiceSelected", getContext()));
				//TODO Hacer un swtich para elegir que casting se ha de hacer a la pregunta
				((ChoiceQuestion)ac.getActualQuestion()).setSelectedValue(choiceList.get(i));
				//If our choice has tip, then set it up into the question

				if(choiceList.get(i).getTip() != null && choiceList.get(i).getTip().length() > 0)
					((ChoiceQuestion)ac.getActualQuestion()).setQuestionTip(choiceList.get(i).getTip());
			}
		}
		
	}

	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}
	
	public void addButtonsListener(OnClickListener listener){
		Iterator<Button> it = getButtonList().iterator();
		
		while (it.hasNext()) {
			Button button = (Button) it.next();
			button.setOnClickListener(listener);
		}
	}

}