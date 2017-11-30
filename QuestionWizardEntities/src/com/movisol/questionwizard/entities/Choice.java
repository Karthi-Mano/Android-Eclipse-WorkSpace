package com.movisol.questionwizard.entities;

public class Choice {

	private String title;
	private String value;
	private String tip;
	private String nextQuestionKey;
	private String image;
	private String sound;
	private double remainingTime;
	
	public Choice()
	{
		
	}
	
	public Choice(String title, String value, String tip, String nextQuestionKey) {
		super();
		this.title = title;
		this.value = value;
		this.tip = tip;
		this.nextQuestionKey = nextQuestionKey;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getNextQuestionKey() {
		return nextQuestionKey;
	}
	public void setNextQuestionKey(String nextQuestionKey) {
		this.nextQuestionKey = nextQuestionKey;
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
	
	
	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	@Override
	public String toString() {
		return "Choice [title=" + title + ", value=" + value + ", tip=" + tip
				+ ", nextQuestionKey=" + nextQuestionKey + ", image=" + image
				+ ", sound=" + sound + ", remainingTime=" + remainingTime + "]";
	}

	/*@Override
	public String toString() {
		return "Choice [title=" + title + ", value=" + value + ", tip=" + tip
				+ ", nextQuestionKey=" + nextQuestionKey + ", remainingTime="
				+ remainingTime + ", image=" + image + "]";
	}*/

	
	
}
