package com.gousslegend.player;

import java.util.List;
import java.util.Scanner;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.UserInterface;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;

public class Human extends Player
{
	UserInterface ui;
	public Human(String name, Board board, UserInterface ui)
	{
		super(name, board);
		this.ui = ui;
	}

	@Override
	public String toString()
	{
		return "Human " + myName;
	}

	public Move takeTurn()
	{
		Move move = null;
		List<Move> moves = myBoard.getLegalMoves().getList();

		while(true)
		{
			move = ui.getMove(myBoard.getPieces(myBoard.getColorToPlay()));
			
			for(Move legalmove : moves)
			{
				if(move.partialEquals(legalmove))
				{
					return legalmove;
				}
			}
			
			ui.sendMessage("This move is not legal. Enter a new move");
		}

	}
	
	
}
