package app;

import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game.ChessModes;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.UserInterface;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Player;

public class GUIInterface implements UserInterface {

	public ChessModes getChessMode(ChessModes[] values) {
		
		return null;
	}

	public void updateBoard(Board myBoard) {

	}

	public Player getNewPlayer(Color playerColor, Board myBoard) {
		
		return null;
	}

	public void setTurn(Player player) {

	}

	@Override
	public String sendMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move getMove(List<Piece> pieces) {
		// TODO Auto-generated method stub
		return null;
	}

}
