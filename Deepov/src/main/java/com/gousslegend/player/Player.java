package com.gousslegend.player;

import java.util.List;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.pieces.Piece;

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

	public Move takeTurn() {
		// TODO Auto-generated method stub
		return null;
	}
}