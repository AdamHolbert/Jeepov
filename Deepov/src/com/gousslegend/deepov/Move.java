package com.gousslegend.deepov;

import com.gousslegend.deepov.pieces.Piece;

public class Move
{
	private Position myOrigin;
	private Position myDestination;
	private Piece myCapturedPiece;
	private boolean myIsPromotion;
	private Piece promotedPiece;
	private Piece promotedPawn;
	private boolean myIsCastling;
	private boolean enPassant;

	public Move(Position origin, Position destination)
	{
		setOrigin(origin);
		setDestination(destination);
	}

	public Position getOrigin()
	{
		return myOrigin;
	}

	public void setOrigin(Position myOrigin)
	{
		this.myOrigin = myOrigin;
	}

	public Position getDestination()
	{
		return myDestination;
	}

	public void setDestination(Position myDestination)
	{
		this.myDestination = myDestination;
	}

	public Piece getCapturedPiece()
	{
		return myCapturedPiece;
	}

	public void setCapturedPiece(Piece myCapturedPiece)
	{
		this.myCapturedPiece = myCapturedPiece;
	}

	@Override
	public String toString()
	{
		String castling = "";
		if(isCastling()) castling = " CASTLING";
		
		String capture = "";
		if(myCapturedPiece != null) capture = " Capture: " + myCapturedPiece;
		
		String promotion = "";
		if(isPromotion()) promotion = " PROMOTION";

		String enPassant = "";
		if(isEnPassant()) enPassant = " ENPASSANT";
		
		return "Move [org=" + myOrigin + ", dest="
				+ myDestination + castling + capture + promotion + enPassant + "]" + "\n";
	}

	public String toShortString()
	{
		return myOrigin.toShortString() +  myDestination.toShortString();
	}
	
	public boolean isPromotion()
	{
		return myIsPromotion;
	}

	public void setIsPromotion(boolean isPromotion)
	{
		myIsPromotion = isPromotion;
	}

	public boolean isCastling()
	{
		return myIsCastling;
	}

	public void setisCastling(boolean isCastling)
	{
		myIsCastling = isCastling;
	}

	public Piece getPromotedPiece()
	{
		return promotedPiece;
	}

	public void setPromotedPiece(Piece promotedPiece)
	{
		this.promotedPiece = promotedPiece;
	}

	public boolean isEnPassant()
	{
		return enPassant;
	}

	public void setIsEnPassant(boolean myIsEnPassant)
	{
		this.enPassant = myIsEnPassant;
	}

	public Piece getPromotedPawn()
	{
		return promotedPawn;
	}

	public void setPromotedPawn(Piece promotedPawn)
	{
		this.promotedPawn = promotedPawn;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myDestination == null) ? 0 : myDestination.hashCode());
		result = prime * result
				+ ((myOrigin == null) ? 0 : myOrigin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (myDestination == null)
		{
			if (other.myDestination != null)
				return false;
		}
		else if (!myDestination.equals(other.myDestination))
			return false;
		if (myOrigin == null)
		{
			if (other.myOrigin != null)
				return false;
		}
		else if (!myOrigin.equals(other.myOrigin))
			return false;
		return true;
	}
}