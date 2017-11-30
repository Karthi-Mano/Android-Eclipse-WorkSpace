package com.movisol.questionwizard.entities;

import java.util.List;


public class Options {
	
	private String title;
	private int width;
	private String style;
	private String defaultValue;
	private List<Option> option;
	private Range range;
	private String selectedValue;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<Option> getOption() {
		return option;
	}
	public void setOption(List<Option> option) {
		this.option = option;
	}
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	@Override
	public String toString() {
		return "Picker [title=" + title + ", width=" + width + ", style="
				+ style + ", defaultValue=" + defaultValue + ", range=" + range
				+ "]";
	}
	
	
	

}
