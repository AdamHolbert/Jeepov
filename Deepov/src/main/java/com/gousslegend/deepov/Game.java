package com.gousslegend.deepov;

import java.security.InvalidParameterException;
import java.util.List;

import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Player;

public class Game
{
	private Board myBoard;
	private Player whitePlayer;
	private Player blackPlayer;
	private GameMode mode;

	/**
	 * This is a list of the current programmed game modes.
	 * @author Adam Holbert Neumont
	 */
	public enum GameMode {
			STANDARD,
			CHESS960
	}
	
	/**
	 * Sets up a new board with the new game mode. Sets both white and black players to null.
	 * @param mode The game mode you would like to set the board too. Can't be null.
	 * @author Adam Holbert Neumont
	 */
	public void setGameMode(GameMode mode){
		if(mode != null) {
			this.mode = mode;
			setMyBoard(new ArrayBoard());
			myBoard.setupBoard(mode);
			whitePlayer = null;
			blackPlayer = null;
		} else {
			throw new InvalidParameterException("Game mode can't be set to null.");
		}
	}
	
	/**
	 * Resets the game to the last mode set up..
	 * @author Adam Holbert Neumont
	 */
	public void resetGame() {
		if(hasValidModeConfiguration()) {
			setGameMode(mode);
		} else {
			throw new InvalidParameterException("The game can not be reset if it has no game mode set.");
		}
	}
	
	/**
	 * Will attempt to execute the move passed in.
	 * @param move This will throw an error the move is invalid in the current state of the game,
	 * or if a null is passed in.
	 * @author Adam Holbert Neumont
	 */
	public void makeMove(Move move) {
		checkValidGameConfigurationLogic();
		if(!isStalemate() && !isCheckmate()) {			
			List<Move> moves = myBoard.getLegalMoves().getList();
	
			boolean validMove = false;
			for(Move legalmove : moves)
			{
				if(move.partialEquals(legalmove))
				{
					validMove = true;
					break;
				}
			}
			
			if(validMove) {
				myBoard.executeMove(move);			
			} else {
				throw new InvalidParameterException("The move passed in was invalid.");
			}
		}
	}
	
	/**
	 * This will return an instance of the player whose color equals the color passed in.
	 * @return Will return null if no player has been given to that color.
	 */
	public Player getPlayer(Color color)
	{
		return color == Color.WHITE ? whitePlayer : blackPlayer;
	}

	/**
	 * Returns the winner of the current game.
	 * @return An instance of the winner 
	 */
	public Player getWinner()
	{
		checkValidGameConfigurationLogic();
		return isCheckmate() ? getPlayer(myBoard.getColorToPlay()) : null;
	}
	
	public boolean isStalemate() {
		checkValidGameConfigurationLogic();
		return getSelectable().size() == 0;
	}
	
	public boolean isCheckmate() {
		checkValidGameConfigurationLogic();
		return myBoard.isCheckmate();
	}
	
	public Player getNewComputerPlayer() {
		return new Deepov(myBoard);
	}

	@SuppressWarnings("unused")
	private int divide(int depth)
	{
		int nMoves, i;
		int nodes = 0;
		int nodeTotal = 0;

		if (depth == 0)
		{
			return 1;
		}

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			System.out.println("");
			myBoard.executeMove(move);
			nodes = perft(depth - 1);
			System.out.println(move.toShortString() + " " + nodes);
			nodeTotal += nodes;
			myBoard.undoMove(move);
		}

		System.out.println("Total nodes: " + nodeTotal);
		System.out.println("Total moves : " + nMoves);
		return nodes;
	}

	private int perft(int depth)
	{
		int nMoves, i;
		int nodes = 0;

		if (depth == 0)
		{
			return 1;
		}

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			myBoard.executeMove(move);
			nodes += perft(depth - 1);
			myBoard.undoMove(move);
		}

		return nodes;
	}

	@SuppressWarnings("unused")
	private int[] perftWithDataCheck(int depth)
	{
		int nMoves, i;
		int[] data = new int[7];
		int[] dataTemp = new int[7];

		if (depth == 0)
		{
			int node = 1;
			int capture = myBoard.getLastMove().getCapturedPiece() == null ? 0
					: 1;
			int castling = myBoard.getLastMove().isCastling() ? 1 : 0;
			int promotion = myBoard.getLastMove().isPromotion() ? 1 : 0;
			int enPassant = myBoard.getLastMove().isEnPassant() ? 1 : 0;
			int check = myBoard.isCheck(myBoard.getColorToPlay()) ? 1 : 0;
			int checkmate = myBoard.isCheckmate(myBoard.getColorToPlay()) ? 1
					: 0;
			return new int[] { node, capture, castling, promotion, enPassant,
					check, checkmate };
		}

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);

			myBoard.executeMove(move);

			dataTemp = perftWithDataCheck(depth - 1);
			data[0] += dataTemp[0];
			data[1] += dataTemp[1];
			data[2] += dataTemp[2];
			data[3] += dataTemp[3];
			data[4] += dataTemp[4];
			data[5] += dataTemp[5];
			data[6] += dataTemp[6];

			myBoard.undoMove(move);
		}

		return data;
	}

	@SuppressWarnings("unused")
	private int[] perftWithData(int depth)
	{
		int nMoves, i;
		int[] data = new int[5];
		int[] dataTemp = new int[5];

		if (depth == 0)
		{
			int node = 1;
			int capture = myBoard.getLastMove().getCapturedPiece() == null ? 0
					: 1;
			int castling = myBoard.getLastMove().isCastling() ? 1 : 0;
			int promotion = myBoard.getLastMove().isPromotion() ? 1 : 0;
			int enPassant = myBoard.getLastMove().isEnPassant() ? 1 : 0;
			return new int[] { node, capture, castling, promotion, enPassant };
		}

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);

			myBoard.executeMove(move);

			dataTemp = perftWithData(depth - 1);
			data[0] += dataTemp[0];
			data[1] += dataTemp[1];
			data[2] += dataTemp[2];
			data[3] += dataTemp[3];
			data[4] += dataTemp[4];

			myBoard.undoMove(move);
		}

		return data;
	}
	
	private void setMyBoard(Board myBoard) {
		if(myBoard == null) {
			throw new InvalidParameterException("The board passed in can't be null");
		}
		this.myBoard = myBoard;
	}
	
	public List<Piece> getBoardPieces() {
		return myBoard.getPieces();
	}
	
	public void setWhitePlayer(Player player) {
		if(player != null) {
			whitePlayer = player;
		} else {
			throw new InvalidParameterException("The white player can't be null");
		}
	}
	
	public void setBlackPlayer(Player player) {
		if(player != null) {
			blackPlayer = player;
		} else {
			throw new InvalidParameterException("The black player can't be null");
		}
	}
	
	public List<Piece> getSelectable() {
		checkValidGameConfigurationLogic();
		List<Piece> returnList = myBoard.getPieces();
		Color currentColor = myBoard.getColorToPlay();
		
		for(Piece p : myBoard.getPieces()) {
			if(!p.getColor().equals(currentColor) || p.getLegalMoves().size() == 0) {
				returnList.remove(p);
			}
		}
		return returnList;
		
	}
	
	public boolean hasValidPlayerConfiguration() {
		return (whitePlayer != null && blackPlayer != null); 
	}
	
	public boolean hasValidModeConfiguration() {
		return mode != null && myBoard != null;
	}
	
	public void checkValidGameConfigurationLogic(){
		if(!hasValidPlayerConfiguration()) {
			throw new IllegalStateException("The players have not been set up properly.");
		}
		if(!hasValidModeConfiguration()) {
			throw new IllegalStateException("The mode has not been set up properly.");
		}
	}
	
	public Color getCurrentTurnColor() {
		return myBoard.getColorToPlay();
	}
}