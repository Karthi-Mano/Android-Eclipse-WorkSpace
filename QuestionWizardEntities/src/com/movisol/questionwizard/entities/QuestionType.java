package com.movisol.questionwizard.entities;

public class QuestionType {
	
	public static final int PREQUESTIONTIP = -1;
	public final static int CHOICE = 0;
	public static final int QUESTIONTIP = 1;
	public final static int DATE = 2; 	
	public final static int CHOICEPICKER = 3;
	public final static int CHOICEBUTTONIMAGE = 4;
	public final static int TEXT = 5;
	public final static int CHOICEIMAGE = 6;
	public static final int CHOICEIMAGESCROLL = 7;
	public static final int CHOICETIME = 8;
	public static final int CHOICEBUTTONSOUND = 9;
	
	
	private int type;
	

	public QuestionType(int tipo) throws Exception
	{
		switch(tipo)
		{
		case QuestionType.DATE:
			type = tipo;
			break;
		case QuestionType.CHOICE:
			type = tipo;
			break;
		case QuestionType.CHOICEPICKER:
			type = tipo;
			break;
		case QuestionType.CHOICEBUTTONIMAGE:
			type = tipo;
			break;
		case QuestionType.TEXT:
			type = tipo;
			break;
		case QuestionType.CHOICEIMAGE:
			type = tipo;
			break;
			
		case QuestionType.CHOICEIMAGESCROLL:
			type = tipo;
			break;			
		case QuestionType.CHOICEBUTTONSOUND:
			type = tipo;
			break;
		default:
			throw new Exception();
		}	

	}
	
	public int getType()
	{return type;}

}

