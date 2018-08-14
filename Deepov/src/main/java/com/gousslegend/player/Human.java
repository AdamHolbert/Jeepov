package com.gousslegend.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.UserInterface;
import com.gousslegend.deepov.board.Board;

public class Human extends Player
{
	UserInterface ui;
	public Human(UserInterface ui, String name, Board board)
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
		
		List<Position> pos = new ArrayList<Position>();
		
		Position p;
		for(Move m : moves) {
			p = new Position(m.getOrigin().getX(), m.getOrigin().getY());
			if(!pos.contains(p)) {
				pos.add(p);
			}
		}
		
		

		while(true)
		{
			move = getMoveFromStdIn();
			
			for(Move legalmove : moves)
			{
				if(move.partialEquals(legalmove))
				{
					return legalmove;
				}
			}
			
				System.out.println("This move is not legal. Enter a new move");
				System.out.println("");
				System.out.println(myBoard);
		}

	}
	
	public Move getMoveFromBoard() {
		
		return null;
	}
	
	public Move getMoveFromStdIn()
	{
		Position origin = ui.getPosition();
		Position destination = ui.getPosition();

		Move move = new Move(origin, destination);
		System.out.println(move);
		
		return move;
	}
	
	
}
