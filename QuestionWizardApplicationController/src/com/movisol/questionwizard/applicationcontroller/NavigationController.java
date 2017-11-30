package com.movisol.questionwizard.applicationcontroller;

import com.movisol.questionwizard.entities.ChoiceButtonImageQuestion;
import com.movisol.questionwizard.entities.ChoiceButtonSoundQuestion;
import com.movisol.questionwizard.entities.ChoiceImageQuestion;
import com.movisol.questionwizard.entities.ChoiceImageScrollQuestion;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.entities.Question;
import com.movisol.questionwizard.entities.QuestionType;
import com.movisol.tools.HelperUtils;


//Singleton pattern
public class NavigationController{
	
	private ApplicationController ac = ApplicationController.getInstance();
	
	//Moves the actualIndexQuestions forward.
	//If possible, returns true.
	//If we have reached the last question, returns false
	public boolean nextQuestion()
	{
		boolean hasNext = false;
		int actualIndexQuestion = ac.getActualIndexFromPath();
		Question question = ac.getActualQuestion();		
		String nextQuestionKey = null;

		
		//Checks if the current question has a nextQuestionKey
		switch(question.getType().getType())
		{
			case QuestionType.CHOICE:
				ChoiceQuestion choiceQuestion = (ChoiceQuestion)question;
				//Question could be not answered if we have a count down timer question
				if(choiceQuestion.getSelectedValue() != null)
					nextQuestionKey = choiceQuestion.getSelectedValue().getNextQuestionKey();
			break;
			case QuestionType.CHOICEBUTTONIMAGE:
				ChoiceButtonImageQuestion choiceButtonImageQuestion = (ChoiceButtonImageQuestion)question;
				//Question could be not answered if we have a count down timer question
				if(choiceButtonImageQuestion.getSelectedValue() != null)
					nextQuestionKey = choiceButtonImageQuestion.getSelectedValue().getNextQuestionKey();
			break;
			case QuestionType.CHOICEIMAGE:
				ChoiceImageQuestion choiceImageQuestion = (ChoiceImageQuestion)question;
				//Question could be not answered if we have a count down timer question
				if(choiceImageQuestion.getSelectedValue() != null)
					nextQuestionKey = choiceImageQuestion.getSelectedValue().getNextQuestionKey();
			break;
			case QuestionType.CHOICEBUTTONSOUND:
				ChoiceButtonSoundQuestion choiceButtonSoundQuestion = (ChoiceButtonSoundQuestion)question;
				//Question could be not answered if we have a count down timer question
				if(choiceButtonSoundQuestion.getSelectedValue() != null)
					nextQuestionKey = choiceButtonSoundQuestion.getSelectedValue().getNextQuestionKey();
			break;
			case QuestionType.CHOICEIMAGESCROLL:
				ChoiceImageScrollQuestion choiceImageScrollQuestion = (ChoiceImageScrollQuestion)question;
				//Question could be not answered if we have a count down timer question
				if(choiceImageScrollQuestion.getSelectedValue() != null)
					nextQuestionKey = choiceImageScrollQuestion.getSelectedValue().getNextQuestionKey();
			break;
		}
		
		//If the question has nextQuestionKey, we have to find out if it is a result key, or a branch key
		if(nextQuestionKey == null)
		{
			//Checks if we have reached the last question
			if(actualIndexQuestion+1 < ac.getQuestions().size())
			{
				actualIndexQuestion++;
				hasNext = true;
			}
		}
		else
		{
			if(!nextQuestionKey.equalsIgnoreCase("result"))
			{
				//If the choice has nextQuestionKey, it looks up into the question list to find the desired question 
				for(int i = 0; i < ac.getQuestions().size(); i++)
				{
					if(ac.getQuestions().get(i).getKey().equalsIgnoreCase(nextQuestionKey))
					{
						actualIndexQuestion = i;
						hasNext = true;
						break;
					}
				}
			}
		}
		
		//Updates actual index, path list and the progessbar
		if(hasNext)
		{
			ac.addIndexToPath(actualIndexQuestion);
			ac.setProgressBarIndex(ac.getProgressBarIndex()+1);
		}
		return hasNext;
	}
	
	//Moves the actualIndexQuestions backward.
	//If possible, returns true.
	//If we have reached the first question, returns false
	public boolean previousQuestion()
	{
		boolean hasPrevious = false;
		int actualIndexQuestion = ac.getActualIndexFromPath();
		if(actualIndexQuestion-1 >= 0)
		{
			//Updates the progressbar
			ac.setProgressBarIndex(ac.getProgressBarIndex()-1);
			//Removes the last index from our path list
			ac.removeLastIndexFromPath();
			hasPrevious = true;
		}
	
		return hasPrevious;
	}

	//Checks if the showed current question has tip
	public boolean currentQuestionHasTip()
	{
		boolean hasTip = false;
		
		if(ac.getActualQuestion().getQuestionTip() != null && ac.getActualQuestion().getQuestionTip().length() > 0)
			hasTip = true;

		return hasTip;
	}
	public boolean currenQuestionHasPreTip(){
		
		if (HelperUtils.getConfigParam("QWReadPreTips", ac.getContext())!=null && HelperUtils.getConfigParam("QWReadPreTips", ac.getContext()).equalsIgnoreCase("false") )
			return false;
		
		if (ac.getActualQuestion().getPreQuestionTip()!=null && ac.getActualQuestion().getPreQuestionTip().getTitle().length() > 0) 
			return true;
		else
			return false;
		
	}

}
