package com.movisol.questionwizard.entities;

import java.util.Date;

public class DateQuestion extends Question{
	
	private Date selectedDate;

	public DateQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.DATE));
	}

	public Date getSelectedDate() 
	{
		if(selectedDate == null)
		{
			selectedDate = new Date();
		}

		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	

}
