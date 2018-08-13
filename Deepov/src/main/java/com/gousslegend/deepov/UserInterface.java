package com.gousslegend.deepov;

import com.gousslegend.deepov.Game.ChessModes;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.player.Player;

public interface UserInterface {

	ChessModes getChessMode(ChessModes[] values);

	void updateBoard(Board myBoard);

	Player getNewPlayer(Color playerColor, Board myBoard);
	
	void setTurn(Player player);
	
}
