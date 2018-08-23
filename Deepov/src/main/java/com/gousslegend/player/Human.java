package com.gousslegend.player;

import java.util.List;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.pieces.Piece;

import app.UIConsole;

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
