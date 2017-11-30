package com.movisol.questionwizard.entities;

public class Option {
	
	private String title;
	private int value;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Option [title=" + title + ", value=" + value + "]";
	}
	
	

}
