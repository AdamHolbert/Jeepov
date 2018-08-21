package com.gousslegend.player;

public class Human extends Player
{

	public Human(String name)
	{
		super(name);
	}

	@Override
	public String toString()
	{
		return "Human " + myName;
	}
}
