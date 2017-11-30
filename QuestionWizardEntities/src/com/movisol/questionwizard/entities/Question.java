package com.movisol.questionwizard.entities;


public class Question {

	private String title;
	private QuestionType type;
	private  boolean mandatory;
	private String category;
	private String questionTip;
	private PreQuestionTip preQuestionTip;
	private boolean isAnswered;
	private boolean isTipVisible;
	private int previousQuestionIndex;
	private int answerTime;
	private boolean isLoaded;
	private String key;
	private String filter;
	private String background;
	
	public Question()
	{
		setAnswered(false);
		setTipVisible(false);
		setLoaded(false);
		setAnswerTime(-1);
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getQuestionTip() {
		return questionTip;
	}
	public void setQuestionTip(String questionTip) {
		this.questionTip = questionTip;
	}
	public PreQuestionTip getPreQuestionTip() {
		return preQuestionTip;
	}
	public void setPreQuestionTip(PreQuestionTip preQuestionTip) {
		this.preQuestionTip = preQuestionTip;
	}
	public boolean isAnswered() {
		return isAnswered;
	}
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	public boolean isTipVisible() {
		return isTipVisible;
	}
	public void setTipVisible(boolean isTipVisible) {
		this.isTipVisible = isTipVisible;
	}
	public int getPreviousQuestionIndex() {
		return previousQuestionIndex;
	}
	public void setPreviousQuestionIndex(int previousQuestionIndex) {
		this.previousQuestionIndex = previousQuestionIndex;
	}
	public int getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}	

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	@Override
	public String toString() {
		return "Question [title=" + title + ", type=" + type + ", mandatory="
				+ mandatory + ", category=" + category + ", questionTip="
				+ questionTip + ", preQuestionTip=" + preQuestionTip
				+ ", isAnswered=" + isAnswered + ", isTipVisible="
				+ isTipVisible + ", previousQuestionIndex="
				+ previousQuestionIndex + ", answerTime=" + answerTime
				+ ", isLoaded=" + isLoaded + ", key=" + key + ", filter="
				+ filter + "]";
	}
		
	
}
