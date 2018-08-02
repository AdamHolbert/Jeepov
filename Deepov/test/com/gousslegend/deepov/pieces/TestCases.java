package com.gousslegend.deepov.pieces;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.ArrayBoard;

public class TestCases {

	@Test
	public void normalChessBoardSetupCheck() {
		char[] expectedCharArray = new char[] {
				(new Rook()).getChar().charAt(0),
				(new Knight()).getChar().charAt(0),
				(new Bishop()).getChar().charAt(0),
				(new Queen()).getChar().charAt(0),
				(new King()).getChar().charAt(0),
				(new Bishop()).getChar().charAt(0),
				(new Knight()).getChar().charAt(0),
				(new Rook()).getChar().charAt(0)
		};

		ArrayBoard myBoard = new ArrayBoard();
		myBoard.setupBoard(false);
		
		assertArrayEquals(expectedCharArray, getBackRow(myBoard).toCharArray());
	}
	
	@Test
	public void allChess960BoardSetupCheck() {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		int i = 0;
		do {
			ArrayBoard myBoard = new ArrayBoard();
			myBoard.setupBoard(true);
			map.put(getBackRow(myBoard), false);
			if(i++ > 1000000) {
				// this has the unlikely potential to throw a false fail.
				// the variable 'i' commonly peaks at 2000
				fail("The setupBoard function does not cover all possible chess 960 board configurations");
				
			}
		} while(map.size() < 960);
		
		// Success if test makes it this far
		assertTrue(true);
	}
	
	public String getBackRow(ArrayBoard myBoard){
		String backRow = "";
		for(int i = 0; i < 8; i++) {
			Piece piece = myBoard.getPiece(new Position(i, 7));
			if(piece != null) {
				backRow += piece.getChar().charAt(0);
			} else {
				fail("The location (0," + i + ") should not be null");
				return null;
			}
		}
		return backRow;
	}
}
