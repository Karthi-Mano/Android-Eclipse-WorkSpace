package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoiceButtonImageQuestion extends Question{
	
	public List<Choice> choices;
	private Choice selectedValue;
	private double remainingBonus; 

	public ChoiceButtonImageQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICEBUTTONIMAGE));
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
	
	public double getRemainingBonus() {
		return remainingBonus;
	}

	public void setRemainingBonus(double remainingBonus) {
		this.remainingBonus = remainingBonus;
	}

	@Override
	public String toString() {
		return "ChoiceButtonImageQuestion [choices=" + choices
				+ ", selectedValue=" + selectedValue + ", remainingBonus="
				+ remainingBonus + "]";
	}

}
