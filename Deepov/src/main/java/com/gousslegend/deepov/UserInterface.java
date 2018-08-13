package com.gousslegend.deepov;

import com.gousslegend.deepov.Game.ChessModes;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.player.Player;

public interface UserInterface {

	/**
	 * This should return one of the provided values
	 * @param values a list of chess modes available
	 * @return a value from the list of values
	 */
	ChessModes getChessMode(ChessModes[] values);

	/**
	 * This will pass in the most recent version of the board and its peices.
	 * @param myBoard
	 */
	void updateBoard(Board myBoard);

	/**
	 * This is called when a new player is needed.
	 * @param playerColor This will be the players color
	 * @param myBoard This is the board they will be placed on
	 * @return This should return a non null player instance
	 */
	Player getNewPlayer(Color playerColor, Board myBoard);
	
	/**
	 * This method is called when a new turn is started.
	 * @param player This is the current players turn
	 */
	void setTurn(Player player);
	
}
