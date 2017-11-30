package com.stmarys.quiz.views;
/* 
* File Name:            ResultsView.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.entities.Question;
import com.movisol.questionwizard.entities.QuestionType;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.stmarys.learning.CppSyntaxScreenActivity;
import com.stmarys.learning.ProgramActivity;
import com.stmarys.learning.R;

public class ResultsView extends RelativeLayout implements OnClickListener, ScreenViewable{
	private ApplicationController ac = ApplicationController.getInstance();
	public static final String MY_PREFS_NAME = "y";
	private Button progButton;
	private String topicCode;
	//private boolean unlockLevel;
	private boolean  unlockLevel;
	private int levelUnlockCode;
	//private  Context context;
	private SharedPreferences sharedPrefs,levelLockSharePref;
	public ResultsView(Context context) {
		
		super(context);
		loadResultView(context);
		
	}
	
	private void loadResultView(Context context)
	{
		int result=calculateResults();
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		  
		LayoutInflater inflator = (LayoutInflater) context.getSystemService(infService);
		inflator.inflate(R.layout.resultview, this,true);
		
		topicCode = "shared"+ ac.getTopicCode();
		//To match with our own enumeration
		sharedPrefs = context.getSharedPreferences("pointsSP" , Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putInt(topicCode, result);
		editor.commit();
//		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		levelUnlockCode = Integer.parseInt(ac.getTopicCode());
		levelUnlockCode++;
		levelLockSharePref = context.getSharedPreferences("levelSP" , Context.MODE_PRIVATE);
		SharedPreferences.Editor levelEditor = levelLockSharePref.edit();
		levelEditor.putBoolean(Integer.toString(levelUnlockCode), true);
		levelEditor.commit();
//		
		
		//String common = "R"+String.valueOf(result);
		
		TextView tvTitle =  (TextView) findViewById(R.id.tvTitle);
		
		

		TextView tvScore =  (TextView) findViewById(R.id.tvScore);
		
		tvScore.setText(Integer.toString(result));
		progButton = (Button)findViewById(R.id.progButton);
		progButton.setOnClickListener(this);
		
		ac.setImagePerCategory(false);
		ac.setResultId("R"+result);

		
	}
	
public int calculateResults() {
		
		List<Question> questionList = ac.getQuestions();
		Iterator<Question> it = questionList.iterator();
		
		int correctas = 0;
		int bonus=0;
		int score = 0;
		while (it.hasNext()) {
			
			Question q = it.next();
			//try{
			switch(q.getType().getType())
			{
				
			case QuestionType.CHOICE:
				ChoiceQuestion actualChoiceQuestion = (ChoiceQuestion) q;
				if(actualChoiceQuestion.isAnswered()){
				//	bonus=(int) actualChoiceQuestion.getRemainingBonus();
				//	Log.e("Bonus:" ,Integer.toString(bonus));
				correctas += Integer.parseInt(actualChoiceQuestion.getSelectedValue().getValue());
				//correctas = correctas+ bonus;
				}else
				{
					correctas=correctas+0;
				}
				break;
				}
			/*}
			catch(Exception e){
				System.out.println("error:::" + e);
				}*/ 
		}
		
		
	//	score += correctas;
		return correctas;
	}
	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intentProgram;
		switch(v.getId()){
		
		case R.id.progButton:
			
			 intentProgram = new Intent(getContext(), ProgramActivity.class);
			 intentProgram.putExtra("topicCode",ac.getTopicCode());
			 intentProgram.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 getContext().startActivity(intentProgram);
			 			
		break;
		default:
			break;
	
		
	}

}
}