package com.movisol.questionwizard.entities;

import java.util.List;

public class ChoiceButtonSoundQuestion extends Question {

	public List<Choice> choices;
	private Choice selectedValue;
	private String sound;
	private double remainingTime;
	private double remainingBonus;

	
	public ChoiceButtonSoundQuestion() throws Exception
	{
		setType(new QuestionType(QuestionType.CHOICEBUTTONSOUND));
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

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
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
		return "ChoiceButtonSoundQuestion [choices=" + choices + ", selectedValue="
				+ selectedValue + ", sound=" + sound + ", remainingTime="
				+ remainingTime + ", remainingBonus=" + remainingBonus + "]";
	}
	
	
}


