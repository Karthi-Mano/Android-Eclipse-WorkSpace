package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoiceQuestion extends Question{
	
	public List<Choice> choices;
	private Choice selectedValue;
	private double remainingTime;
	private double remainingBonus;

	public ChoiceQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICE));
		setRemainingBonus(0);
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
		return "ChoiceQuestion [choices=" + choices + ", selectedValue="
				+ selectedValue + ", remainingTime=" + remainingTime
				+ ", remainingBonus=" + remainingBonus + "]";
	}

		

}
