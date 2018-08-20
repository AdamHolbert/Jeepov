package pieces;

import javafx.scene.control.*;

import java.util.ArrayList;

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
//	GridPiece[][] list = new GridPiece[8][8];
	ArrayList<GridPiece> list = new ArrayList<>();
	private Game game = null;
	public ChessBoard(Game g) throws Exception{
		this.game = g;
		addToGridPane(g);
	}
	
	private void addToGridPane(Game g) throws Exception{
		int i = 0;
		boolean flip = false;
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				Color bgColor = null;
				//Empty squares between teams' pieces
				if((a - b) % 2 == 0) bgColor = Color.BLACK;
				else bgColor = Color.WHITE;
				
				if(b == 2 || b == 3 || b == 4 || b == 5){
					this.add(new GridPiece("", bgColor, this), a, b, 1, 1);
//					list[a][b] = new GridPiece("", bgColor, this);
					list.add(new GridPiece("", bgColor, this));
				}
				//The list of pieces on board
				if(i < g.getBoard().getNumberOfPieces()){
					//Background colors alternate through list
					if(i % 2 == 0 && flip == false) bgColor = Color.BLACK;
					else if(i % 2 == 1 && flip == true) bgColor = Color.BLACK;
					else bgColor = Color.WHITE;
					
					int x = g.getBoard().getPieces().get(i).getPosition().getX();
					int y = g.getBoard().getPieces().get(i).getPosition().getY();
					String simpleName = g.getBoard().getPieces().get(i).getClass().getSimpleName();
					Color color = g.getBoard().getPieces().get(i).getColor();
					GridPiece tempPiece = null;
					
					if(simpleName.equals("Pawn") && color == Color.BLACK){
						tempPiece = new GridPiece("bp", bgColor, this);
					}else if(simpleName.equals("Pawn") && color == Color.WHITE){
						tempPiece = new GridPiece("wp", bgColor, this);
					}
					if(simpleName.equals("Rook") && color == Color.BLACK){
						tempPiece = new GridPiece("br", bgColor, this);
					}else if(simpleName.equals("Rook") && color == Color.WHITE){
						tempPiece = new GridPiece("wr", bgColor, this);
					}
					if(simpleName.equals("Knight") && color == Color.BLACK){
						tempPiece = new GridPiece("bn", bgColor, this);
					}else if(simpleName.equals("Knight") && color == Color.WHITE){
						tempPiece = new GridPiece("wn", bgColor, this);
					}
					if(simpleName.equals("Bishop") && color == Color.BLACK){
						tempPiece = new GridPiece("bb", bgColor, this);
					}else if(simpleName.equals("Bishop") && color == Color.WHITE){
						tempPiece = new GridPiece("wb", bgColor, this);
					}
					if(simpleName.equals("Queen") && color == Color.BLACK){
						tempPiece = new GridPiece("bq", bgColor, this);
					}else if(simpleName.equals("Queen") && color == Color.WHITE){
						tempPiece = new GridPiece("wq", bgColor, this);
					}
					if(simpleName.equals("King") && color == Color.BLACK){
						tempPiece = new GridPiece("bk", bgColor, this);
					}else if(simpleName.equals("King") && color == Color.WHITE){
						tempPiece = new GridPiece("wk", bgColor, this);
					}
					this.add(tempPiece, x, y, 1, 1);
//					list[x][y] = tempPiece;
					list.add(tempPiece);
					i++;
				}
				//List is from 0 - 31. Flip boolean on multiples of 4
				if((i / 4) % 2 == 1) flip = true; //i == 4 || i == 12 || i == 20 || i == 28
				else if(i % 8 == 0) flip = false; //i == 8 || i == 16 || i == 24
			}
		}
	}


}
