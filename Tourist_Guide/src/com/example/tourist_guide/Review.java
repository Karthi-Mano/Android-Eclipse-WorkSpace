package com.example.tourist_guide;

public class Review {

	
	String name;
	String comment;
	String rate;
	
	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(String name, String comment, String rate) {
		super();
		this.name = name;
		this.comment = comment;
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Review [name=" + name + ", comment=" + comment + ", rate="
				+ rate + "]";
	}
	
	
}
