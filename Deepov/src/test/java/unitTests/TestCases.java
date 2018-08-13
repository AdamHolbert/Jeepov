package unitTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

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
		
		assertArrayEquals(expectedCharArray, getRowAsString(7, myBoard).toCharArray());
	}
	
	@Test
	public void allChess960BoardSetupCheck() {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		for(int i = 0; i < 1000000; i++) {
			ArrayBoard myBoard = new ArrayBoard();
			myBoard.setupBoard(true);
			map.put(getRowAsString(7, myBoard), false);
		}
		
		// this has the unlikely potential to throw a false fail.
		// the variable 'i' commonly peaks at 2000
		assertEquals(map.size(), 960);
	}
	
	@Test
	public void allChess960bothSideMirrored() {
		ArrayBoard myBoard = new ArrayBoard();
		myBoard.setupBoard(true);
		assertEquals(getRowAsString(7, myBoard).toLowerCase(), getRowAsString(0, myBoard).toLowerCase());
	}
		
	@Test
	public void chess960KingBettenRook() {
		ArrayBoard myBoard = new ArrayBoard();
		myBoard.setupBoard(true);
		boolean rookBeforeKing = false;
		boolean rookAfterKing = false;
		boolean foundKing = false;
		
		// The king should be between both the rooks.
		for(char peice : getRowAsString(7, myBoard).toCharArray()) {
			if(peice == (new Rook()).getChar().charAt(0)) {
				if(foundKing) {
					rookBeforeKing = true;
				} else {
					rookAfterKing = true;
				}
			}
			if(peice == (new King()).getChar().charAt(0)) {
				foundKing = true;
			}
		}
		assertTrue(rookBeforeKing && rookAfterKing);
	}
	
	@Test
	public void chess960BishupsOnDifferentColors() {
		ArrayBoard myBoard = new ArrayBoard();
		myBoard.setupBoard(true);
		boolean bishopOnOdd = false;
		boolean bishopOnEven = false;
		
		// A bishop should be both on a black square and a white square
		int i = 0;
		for(char peice : getRowAsString(7, myBoard).toCharArray()) {
			if(peice == (new Bishop()).getChar().charAt(0)) {
				if(i%2 == 0) {
					bishopOnOdd = true;
				} else {
					bishopOnEven = true;
				}
			}
			i++;
		}
		assertTrue(bishopOnEven && bishopOnOdd);
	}
	
	public String getRowAsString(int row, ArrayBoard myBoard){
		String backRow = "";
		for(int i = 0; i < 8; i++) {
			Piece piece = myBoard.getPiece(new Position(i, row));
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
