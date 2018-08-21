package com.gousslegend.player;

public abstract class Player
{
	protected String myName = "Human";
	
	public Player(String name)
	{
		myName = name;
	}
	
	public String getName()
	{
		return myName;
	}

	public void setName(String myName)
	{
		this.myName = myName;
	}
}