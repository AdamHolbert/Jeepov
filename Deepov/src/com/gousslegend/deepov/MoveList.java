package com.gousslegend.deepov;

import java.util.ArrayList;
import java.util.List;

public class MoveList
{

	private List<Move> list;
	private Board myBoard;

	public MoveList(Board board)
	{
		list = new ArrayList<Move>();
		myBoard = board;
	}
	
	public MoveList()
	{
		list = new ArrayList<Move>();
	}

	public void addMove(Move move)
	{
		list.add(move);
	}

	public Board getBoard()
	{
		return myBoard;
	}

	public void setBoard(Board myBoard)
	{
		this.myBoard = myBoard;
	}
	
	public List<Move> getList()
	{
		return list;
	}
	
	public Move getFistMove()
	{
		return list.get(0);
	}
	
	public void setList(List<Move> myList)
	{
		this.list = myList;
	}

	public boolean add(Move move)
	{
		return list.add(move);
	}

	public int size()
	{
		return list.size();
	}

	@Override
	public String toString()
	{
		return "MoveList [myList=" + list + "]";
	}
}
