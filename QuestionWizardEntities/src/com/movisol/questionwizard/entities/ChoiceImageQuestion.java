package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoiceImageQuestion extends Question{
	
	public List<Choice> choices;
	private Choice selectedValue;
	private String image;
	private double remainingTime;
	private double remainingBonus;

	
	public ChoiceImageQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICEIMAGE));
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
	public double getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}
	
	public double getRemainingBonus() {
		return remainingBonus;
	}

	public void setRemainingBonus(double remainingBonus) {
		this.remainingBonus = remainingBonus;
	}

	@Override
	public String toString() {
		return "ChoiceImageQuestion [choices=" + choices + ", selectedValue="
				+ selectedValue + ", image=" + image + ", remainingTime="
				+ remainingTime + ", remainingBonus=" + remainingBonus + "]";
	}
	
	
}
