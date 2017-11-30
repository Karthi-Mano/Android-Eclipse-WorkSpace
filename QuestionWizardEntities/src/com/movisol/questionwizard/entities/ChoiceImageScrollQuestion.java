package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoiceImageScrollQuestion extends Question{
	
	public List<Choice> choices;
	private Choice selectedValue;
	private String image;

	public ChoiceImageScrollQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICEIMAGESCROLL));
	}

	public List<Choice> getChoices() {
		return choices;
	}
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	public Choice getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(Choice selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ChoiceImageQuestion [choices=" + choices + ", selectedValue="
				+ selectedValue + ", image=" + image + "]";
	}	
	

}
