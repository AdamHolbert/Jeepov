package pieces;

import javafx.scene.control.*;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChessBoard extends GridPane {
	
	public ChessBoard(Game g) throws Exception{
		displayChessBoard(g);
	}
	
	private void displayChessBoard(Game g) throws Exception{
		int i = 0;
		boolean flip = false;
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				Color bgColor = null;
				//Empty squares between teams' pieces
				if((a - b) % 2 == 0) bgColor = Color.BLACK;
				else bgColor = Color.WHITE;
				
				if(b == 2 || b == 3 || b == 4 || b == 5)
					this.add(new GridPiece("", bgColor), a, b, 1, 1);
				//The list of pieces on board
				if(i < 32){
					//Background colors alternate through list
					if(i % 2 == 0 && flip == false) bgColor = Color.BLACK;
					else if(i % 2 == 1 && flip == true) bgColor = Color.BLACK;
					else bgColor = Color.WHITE;
					
					int x = g.getBoard().getPieces().get(i).getPosition().getX();
					int y = g.getBoard().getPieces().get(i).getPosition().getY();
					String simpleName = g.getBoard().getPieces().get(i).getClass().getSimpleName();
					Color color = g.getBoard().getPieces().get(i).getColor();
					
					if(simpleName.equals("Pawn") && color == Color.BLACK){
						this.add(new GridPiece("bp", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("Pawn") && color == Color.WHITE){
						this.add(new GridPiece("wp", bgColor), x, y, 1, 1);
					}
					if(simpleName.equals("Rook") && color == Color.BLACK){
						this.add(new GridPiece("br", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("Rook") && color == Color.WHITE){
						this.add(new GridPiece("wr", bgColor), x, y, 1, 1);
					}
					if(simpleName.equals("Knight") && color == Color.BLACK){
						this.add(new GridPiece("bn", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("Knight") && color == Color.WHITE){
						this.add(new GridPiece("wn", bgColor), x, y, 1, 1);
					}
					if(simpleName.equals("Bishop") && color == Color.BLACK){
						this.add(new GridPiece("bb", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("Bishop") && color == Color.WHITE){
						this.add(new GridPiece("wb", bgColor), x, y, 1, 1);
					}
					if(simpleName.equals("Queen") && color == Color.BLACK){
						this.add(new GridPiece("bq", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("Queen") && color == Color.WHITE){
						this.add(new GridPiece("wq", bgColor), x, y, 1, 1);
					}
					if(simpleName.equals("King") && color == Color.BLACK){
						this.add(new GridPiece("bk", bgColor), x, y, 1, 1);
					}else if(simpleName.equals("King") && color == Color.WHITE){
						this.add(new GridPiece("wk", bgColor), x, y, 1, 1);
					}
					i++;
				}
				//List is from 0 - 31. Flip boolean on multiples of 4
				if((i / 4) % 2 == 1) flip = true; //i == 4 || i == 12 || i == 20 || i == 28
				else if(i % 8 == 0) flip = false; //i == 8 || i == 16 || i == 24
			}
		}

	}
}
