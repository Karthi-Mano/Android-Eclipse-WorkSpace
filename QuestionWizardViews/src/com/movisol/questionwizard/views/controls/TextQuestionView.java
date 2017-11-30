package com.movisol.questionwizard.views.controls;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.TextQuestion;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.tools.HelperUtils;


public class TextQuestionView extends LinearLayout implements OnEditorActionListener, ScreenViewable {	
	private TextQuestion question;
	private EditText etName;

	private ApplicationController ac = ApplicationController.getInstance();
	CompositeListener compositeListener;
	
	public TextQuestionView(Context context) {
		super(context);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.textquestion, this, true);

		
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", context));
		getBackground().setDither(true);
}
		

	public void initialize (CompositeListener cl)
	{
		compositeListener = cl;
		
		//Gets the actual question from the Singleton Application Controller
		question = (TextQuestion) ac.getActualQuestion();

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
			TextView title = (TextView) findViewById(R.id.tvTitle);
			title.setText(question.getTitle());
			title.setMovementMethod(new ScrollingMovementMethod());
			//Loads the predefined style for the title of the question
			title.setTextAppearance(getContext(), HelperUtils.getStyleResource("questionTitle", getContext()));
			
			etName = (EditText) findViewById(R.id.etName);
			etName.setOnEditorActionListener(compositeListener);
			
			InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	        in.hideSoftInputFromWindow(etName.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			
			TextWatcher tWatcher = new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) 
				{
					if(s.length() == 0)
						ac.getActualQuestion().setAnswered(false);
					else
					{
						ac.getActualQuestion().setAnswered(true);
						((TextQuestion)ac.getActualQuestion()).setSelectedValue(s.toString());
						int cont = 0;
						for(int i = s.length()-1; i >= 0;--i)
							if(s.charAt(i) == ' ')
								cont++;
						if(cont == s.length())
						{
							((TextQuestion)ac.getActualQuestion()).setSelectedValue(null);
							ac.getActualQuestion().setAnswered(false);
						}
					}
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			};
			
			etName.addTextChangedListener(tWatcher);
			
			if(question.getSelectedValue() != null)
				etName.setText(question.getSelectedValue());
			
		}
}

	public void setQuestion(TextQuestion question) {
		this.question = question;
	}

	
	public ViewGroup getLayout() {
		return this;
	}

	@Override
	public void initializeControls() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
	{
		InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(etName.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		
        if(v.getText() != null && !v.getText().toString().trim().equalsIgnoreCase(""))
		{
            ac.getActualQuestion().setAnswered(true);
            ((TextQuestion)ac.getActualQuestion()).setSelectedValue(v.getText().toString());
		}
		else
		{
			ac.getActualQuestion().setAnswered(false);
			((TextQuestion)ac.getActualQuestion()).setSelectedValue(null);
		}
        return true;

	}

}