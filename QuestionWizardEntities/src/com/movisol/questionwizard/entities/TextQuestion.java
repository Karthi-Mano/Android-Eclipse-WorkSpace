package com.movisol.questionwizard.entities;


public class TextQuestion extends Question{
	
	private String selectedValue;

	public TextQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.TEXT));
	}

	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	@Override
	public String toString() {
		return "TextQuestion [selectedValue=" + selectedValue + "]";
	}
		

}
