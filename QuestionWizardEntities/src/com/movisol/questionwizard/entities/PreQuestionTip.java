package com.movisol.questionwizard.entities;

public class PreQuestionTip {
	
	private String title;
	private int time;
	
	public PreQuestionTip()
	{
		title = null;
		time = 0;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "PreQuestionTip [title=" + title + ", time=" + time + "]";
	}
	
	
}
