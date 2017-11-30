package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoicePickerQuestion extends Question{
	
	private List<Options> options;

	public ChoicePickerQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICEPICKER));
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}


}
