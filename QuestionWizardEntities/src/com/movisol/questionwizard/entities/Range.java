package com.movisol.questionwizard.entities;

public class Range {
	
	private int from;
	private int to;
	private int interval;
	private int decimalDigits;
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	
	@Override
	public String toString() {
		return "Range [from=" + from + ", to=" + to + ", interval=" + interval
				+ ", decimalDigits=" + decimalDigits + "]";
	}

}
