package com.movisol.questionwizard.views.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceButtonSoundQuestion;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.questionwizard.views.listeners.MoveForwardEvent;
import com.movisol.questionwizard.views.listeners.MoveForwardListener;
import com.movisol.tools.HelperUtils;

public class ChoiceButtonSoundQuestionView extends LinearLayout implements OnClickListener, ScreenViewable {

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
	private ChoiceButtonSoundQuestion question;
	private  List<Choice> choiceList;

	private List<Button> buttonList;
	private List<ImageButton> imageButtonList;
	private ApplicationController ac = ApplicationController.getInstance();
	CompositeListener compositeListener;
	private ProgressBar pbChoiceImage;
	private CountDownTimer countDown;
	private long millisToFinish;
	protected List<MoveForwardListener> listeners = new ArrayList<MoveForwardListener>();
	private boolean canceled = false;
	private Context context; 
	public  MediaPlayer mp;
	public ChoiceButtonSoundQuestionView(Context context) {
		super(context);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.choicebuttonsoundquestion, this, true);
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
		this.context = context;
	}
		
	public synchronized void addMoveForwardListener(MoveForwardListener listener){
		listeners.add(listener);
	}
	public synchronized void removeMoveForwardListener(MoveForwardListener listener){
		listeners.remove(listener);
	}
	
	public void fireTimeExpiredEvent(){
		MoveForwardEvent mfe = null;
		mfe = new MoveForwardEvent(this, "move_forward");
		Iterator<MoveForwardListener> it = listeners.listIterator();
		while (it.hasNext()){
			((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setQuestionTip(ac.getLiteralsValueByKey("noAnswer"));
			//ac.set
			 it.next().onTimeExpired(mfe);
		}
	}

	@SuppressWarnings("rawtypes")
	public void initialize (CompositeListener cl)
	{
		buttonList = new ArrayList<Button>();
		imageButtonList = new ArrayList<ImageButton>();
		choiceList = new ArrayList<Choice>();
		compositeListener = cl;
		
		//Gets the actual question from the Singleton Application Controller
		question = (ChoiceButtonSoundQuestion) ac.getActualQuestion();

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
			TextView title = (TextView) findViewById(R.id.choiceIQtvTitle);
			title.setTextColor(Color.parseColor("#A9A9A9"));			
			if(question.getTitle() != null && !question.getTitle().equals(""))
				title.setText(question.getTitle());
			else
			{ 
				title.setVisibility(View.GONE);
				setPadding(0,(int) (20*ac.getDisplayMetrics().density), 0, 0);
			}
			
			int resID=HelperUtils.getDrawableResource(question.getSound(), getContext());
			Log.e("SOUND FILE NAME","sound:"+question.getSound());
		//	Resources res=getContext().getResources().getResourceName(resID);
		//	Drawable imgDrawable = getContext().getResources().getDrawable(HelperUtils.getDrawableResource(question.getSound(), getContext()));
		//	int resID=Integer.parseInt(imgDrawable.toString());
			
			 mp = MediaPlayer.create(getContext(),resID );
			
			//Loads the predefined style for the title of the question
			//title.setTextAppearance(getContext(), HelperUtils.getStyleResource("questionTitle", getContext()));
			Button playButton = (Button) findViewById(R.id.playButton);
			playButton.setSoundEffectsEnabled(canceled);
			playButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mp.start();
				}
			});
		/*	try {
				Drawable imgDrawable = getContext().getResources().getDrawable(HelperUtils.getDrawableResource(question.getSound(), getContext()));
				image.setImageDrawable(imgDrawable);
			} catch (NotFoundException e) {
				Log.e("QuestionWizardViews", "ChoiceButtonSoundQuestionView: "+question.getSound()+" --> Not Found Exception");
			}*/
			
			
			pbChoiceImage = (ProgressBar) findViewById(R.id.pbChoiceImage);
			
			if(question.getAnswerTime() != -1 && Boolean.parseBoolean(HelperUtils.getConfigParam("QWQuestionTimeEnabled", context)))
			{
				pbChoiceImage.setProgressDrawable(ac.getContext().getResources().getDrawable(HelperUtils.getDrawableResource("progressbar_color", ac.getContext())));
				//Convert to msecs
				pbChoiceImage.setMax(question.getAnswerTime()*1000);
				pbChoiceImage.setProgress(0);
				millisToFinish = question.getAnswerTime()*1000L;
			
				newCountDown();
			}
			else
				pbChoiceImage.setVisibility(View.GONE);
			
			
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
				
				choiceBtn.setPadding(choiceTmp.getPaddingLeft(), 0, choiceTmp.getPaddingRight(), 0);			
			
				buttonList.add(choiceBtn);
				choicesLayout.addView(choiceBtn);
				i++;
			}
			
			choicesLayout.removeViewAt(0);
		}
}
			/*LinearLayout choicesLayout = (LinearLayout) findViewById(R.id.relativeLayout1);
			TableRow tableRow = (TableRow) findViewById(R.id.tableRow1);
			while (it.hasNext()) 
			{
				choiceList.add((Choice) it.next());
				
				ImageButton imgBtn = null;
				if(choiceList.get(i).getImage() != null)
				{
					imgBtn = new ImageButton(ac.getContext());
					
					imgBtn.setImageResource(HelperUtils.getDrawableResource(choiceList.get(i).getImage(), ac.getContext()));
					imgBtn.setTag(choiceList.get(i).getImage());
					imgBtn.setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimage", ac.getContext()));
					imgBtn.setPadding(choiceTmp.getPaddingLeft(), choiceTmp.getPaddingTop(), choiceTmp.getPaddingRight(), choiceTmp.getPaddingBottom());
					imageButtonList.add(imgBtn);
					
					if(question.isAnswered() && question.getSelectedValue().getImage().equals(imgBtn.getTag()))
					{
						imgBtn.setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimagepressed", getContext()));
					}
				}
				
				Button choiceBtn = new Button(getContext());
				LinearLayout layout = new LinearLayout(getContext());
			    
				choiceBtn.setGravity(choiceTmp.getGravity());
				choiceBtn.setText(choiceList.get(choiceList.size()-1).getTitle());
				
				choiceBtn.setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimage", getContext()));
				
				//Sets the predefined style for the Button
				choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("choiceImageTitleNormal", getContext()));
				
				if(choiceBtn.getText().length() > 38)
				{
					//Sets the small style for the Button				
					choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("choiceImageTitleSmall", getContext()));
				}
				
				
				if(question.isAnswered() && question.getSelectedValue().getTitle().equals(choiceBtn.getText()))
				{
					choiceBtn.setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimagepressed", getContext()));
					choiceBtn.setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoiceSelected", getContext()));
				}
				
				choiceBtn.setPadding(choiceTmp.getPaddingLeft(), choiceTmp.getPaddingTop(), choiceTmp.getPaddingRight(), choiceTmp.getPaddingBottom());			
			
				buttonList.add(choiceBtn);
				
				if(choiceList.get(i).getImage() != null)
					layout.addView(imgBtn, choiceTmp.getLayoutParams());
				else
					layout.addView(choiceBtn, choiceTmp.getLayoutParams());
				
				layout.setOrientation(choicesLayout.getOrientation());
				layout.setPadding(choicesLayout.getPaddingLeft(), choicesLayout.getPaddingTop(), choicesLayout.getPaddingRight(), choicesLayout.getPaddingBottom());
				layout.setLayoutParams(choicesLayout.getLayoutParams());
				layout.setGravity(Gravity.CENTER);
				tableRow.addView(layout, choicesLayout.getLayoutParams());
				i++;
			}
			
			tableRow.removeViewAt(0);
			
		}
	}*/
	
	private void newCountDown() {
		countDown = new CountDownTimer(millisToFinish, 1) {

		     public void onTick(long millisUntilFinished) {
		    	 millisToFinish = millisUntilFinished; 
		    	 pbChoiceImage.setProgress((int)(question.getAnswerTime()*1000-millisUntilFinished));
		    
		    	 }

		     public void onFinish() {
		    	 if(!canceled)
		    		 fireTimeExpiredEvent();
		     }
		  };
		
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		switch(visibility)
		{
			case View.VISIBLE:
				if(pbChoiceImage.getVisibility() == View.VISIBLE)
				{Log.e("Inside Visible State","power key pressed");
					if(countDown == null)
						newCountDown();
					canceled = false;
					countDown.start();
				}
				
			break;
		
			case View.GONE:
				if(pbChoiceImage.getVisibility() == View.VISIBLE && countDown != null)
				{
					Log.e("Inside the GOne","power key pressed");
					countDown.cancel();
					canceled = true;
					countDown = null;
				}
			break;
			
		}
	}
	
	public void cancelTimer() {
		if(countDown != null)
			countDown.cancel();
		canceled  = true;
		
	}

	public void setQuestion(ChoiceButtonSoundQuestion question) {
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
	
	public List<ImageButton> getImageButtonList() {
		return imageButtonList;
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
			//	mp.release();
				//Changes the background image of the button clicked
				buttonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource(selectedChoicesImagesList[i], getContext()));
				buttonList.get(i).setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoiceSelected", getContext()));
				//TODO Hacer un swtich para elegir que casting se ha de hacer a la pregunta
				((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setSelectedValue(choiceList.get(i));
				//If our choice has tip, then set it up into the question

				if(choiceList.get(i).getTip() != null && choiceList.get(i).getTip().length() > 0)
					((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setQuestionTip(choiceList.get(i).getTip());
			}
		}
	/*	for(int i = 0; i < choiceList.size(); i++)
		{
			if(choiceList.get(i).getImage() != null)
			{
				imageButtonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimage", getContext()));
				//Once we have selected a choice, we look for the one that was clicked	
				if( choiceList.get(i).getImage().equals( ((ImageButton)v).getTag()))
				{
					ac.getActualQuestion().setAnswered(true);
					//Changes the background image of the button clicked
					imageButtonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimagepressed", getContext()));
					//TODO Hacer un swtich para elegir que casting se ha de hacer a la pregunta
					((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setSelectedValue(choiceList.get(i));
					//If our choice has tip, then set it up into the question
	
					if(choiceList.get(i).getTip() != null && choiceList.get(i).getTip().length() > 0)
						((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setQuestionTip(choiceList.get(i).getTip());
				}
			}
			else
			{
				buttonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimage", getContext()));
				buttonList.get(i).setTextAppearance(getContext(), HelperUtils.getStyleResource("choiceImageTitleNormal", getContext()));
				//Once we have selected a choice, we look for the one that was clicked	
				if( choiceList.get(i).getTitle().equals( ((Button)v).getText()))
				{
					ac.getActualQuestion().setAnswered(true);
					//Changes the background image of the button clicked
					buttonList.get(i).setBackgroundResource(HelperUtils.getDrawableResource("btnchoiceimagepressed", getContext()));
					buttonList.get(i).setTextAppearance(getContext(), HelperUtils.getStyleResource("textChoiceSelected", getContext()));

					((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setSelectedValue(choiceList.get(i));
					//If our choice has tip, then set it up into the question
	
					if(choiceList.get(i).getTip() != null && choiceList.get(i).getTip().length() > 0)
					      ((ChoiceButtonSoundQuestion)ac.getActualQuestion()).setQuestionTip(choiceList.get(i).getTip());
				}
			}
		}*/
		
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
	
	public void addImageButtonsListener(OnClickListener listener){
		Iterator<ImageButton> it = getImageButtonList().iterator();
		
		while (it.hasNext()) {
			ImageButton button = (ImageButton) it.next();
			button.setOnClickListener(listener);
		}
	}

}

