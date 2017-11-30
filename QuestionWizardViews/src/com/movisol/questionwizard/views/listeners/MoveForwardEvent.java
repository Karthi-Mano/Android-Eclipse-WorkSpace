package com.movisol.questionwizard.views.listeners;

import java.util.EventObject;

public class MoveForwardEvent extends EventObject{

	private String data;
	private static final long serialVersionUID = 3653637778302928192L;

	public MoveForwardEvent(Object source, String data) {
		super(source);
		this.data = data;
	}
	
	public String getData()
	{
		return data;
	}

}
