package com.gousslegend.player;

import com.gousslegend.deepov.board.Board;

public class Human extends Player
{

	public Human(String name, Board board)
	{
		super(name, board);
	}

	@Override
	public String toString()
	{
		return "Human " + myName;
	}
}
