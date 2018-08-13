package com.gousslegend.deepov;

import java.security.InvalidParameterException;

import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Human;
import com.gousslegend.player.Player;

public class Game
{
	private Board myBoard;
	private Player whitePlayer;
	private Player blackPlayer;
	private UserInterface ui;

	public enum ChessModes {
			STANDARD,
			CHESS960
	}
	
	public Game(UserInterface ui)
	{
		setUI(ui);
		setMyBoard(new ArrayBoard());
		
		ChessModes mode = null;
		while(mode == null) {
			mode = this.ui.getChessMode(ChessModes.values());
		}
		myBoard.setupBoard(mode);

		setWhitePlayer(this.ui.getNewPlayer(Color.WHITE, myBoard));
		setBlackPlayer(this.ui.getNewPlayer(Color.BLACK, myBoard));

		this.ui.updateBoard(myBoard);
	}
	
	public Game(UserInterface ui, ChessModes mode, Player whitePlayer, Player blackPlayer) {
		setUI(ui);
		setMyBoard(new ArrayBoard());
		myBoard.setupBoard(mode);
		setBlackPlayer(blackPlayer);
		setWhitePlayer(whitePlayer);
	}

	public void play()
	{
		while(getWinner() == null)
		{
			Player playerToPLay = getPlayer(myBoard.getColorToPlay());
			ui.setTurn(getPlayer(myBoard.getColorToPlay()));
			ui.updateBoard(myBoard);
			
			Move move = playerToPLay.takeTurn();
			myBoard.executeMove(move);
		}

		System.out.println("CHECKMATE");
	}
	
	public Player getPlayer(Color color)
	{
		return color == Color.WHITE ? whitePlayer : blackPlayer;
	}

	public Player getWinner()
	{
		if (myBoard.isCheckmate())
		{
			return getPlayer(myBoard.getColorToPlay());
		}
		else
		{
			return null;
		}
	}

	public int divide(int depth)
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

	public int perft(int depth)
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

	public int[] perftWithDataCheck(int depth)
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

	public int[] perftWithData(int depth)
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
	
	private void setUI(UserInterface ui) {
		if(ui == null) {
			throw new InvalidParameterException("The user interface passed in can't be null");
		}
		this.ui = ui;
	}

	private void setMyBoard(Board myBoard) {
		if(myBoard == null) {
			throw new InvalidParameterException("The board passed in can't be null");
		}
		this.myBoard = myBoard;
		
	}

	public Player getWhitePlayer()
	{
		return whitePlayer;
	}

	public void setWhitePlayer(Player whitePlayer)
	{
		this.whitePlayer = whitePlayer;
	}

	public Player getBlackPlayer()
	{
		return blackPlayer;
	}

	public void setBlackPlayer(Player blackPlayer)
	{
		this.blackPlayer = blackPlayer;
	}

	public Board getBoard()
	{
		return myBoard;
	}

	public void setBoard(Board board)
	{
		myBoard = board;
	}

}