package com.stmarys.quiz;
/* 
* File Name:            Quizzz.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import android.os.Bundle;

import com.movisol.questionwizard.views.MainPage;

public class Quizzz extends MainPage{
    /** Called when the activity is first created. */
	public String topicCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	topicCode = getIntent().getStringExtra("syntaxCode");
		//Indicamos a los singleton el contexto de la aplicación 
		ac.initializeParameters(getApplicationContext(), "");
		ac.setTopicCode(topicCode);
		ac.setResultClasName("com.stmarys.quiz.views.ResultsView");
    } 
}