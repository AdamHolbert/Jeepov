package pieces;

import javafx.scene.control.*;

import java.util.ArrayList;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Piece;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChessBoard extends GridPane {
	GridPiece[][] list = new GridPiece[8][8];
//	ArrayList<GridPiece> list = new ArrayList<>();
	private Game game = null;
	private GridPiece selectedGridPiece = null;
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
				GridPiece tempPiece = null;
				//Empty squares between teams' pieces
				if((a - b) % 2 == 0) bgColor = Color.BLACK;
				else bgColor = Color.WHITE;
				
				if(b == 2 || b == 3 || b == 4 || b == 5){
					tempPiece = new GridPiece("", bgColor, this, null);
					this.add(tempPiece, a, b, 1, 1);
					list[a][b] = tempPiece;
				}
				//The list of pieces on board
				if(i < g.getBoardPieces().size()){
					//Background colors alternate through list
					if(i % 2 == 0 && flip == false) bgColor = Color.BLACK;
					else if(i % 2 == 1 && flip == true) bgColor = Color.BLACK;
					else bgColor = Color.WHITE;
					
					Piece currentPiece = g.getBoardPieces().get(i);
					int x = currentPiece.getPosition().getX();
					int y = currentPiece.getPosition().getY();
					String simpleName =  currentPiece.getClass().getSimpleName();
					Color color = currentPiece.getColor();
					
					if(simpleName.equals("Pawn") && color == Color.BLACK){
						tempPiece = new GridPiece("bp", bgColor, this, currentPiece);
					}else if(simpleName.equals("Pawn") && color == Color.WHITE){
						tempPiece = new GridPiece("wp", bgColor, this, currentPiece);
					}
					if(simpleName.equals("Rook") && color == Color.BLACK){
						tempPiece = new GridPiece("br", bgColor, this, currentPiece);
					}else if(simpleName.equals("Rook") && color == Color.WHITE){
						tempPiece = new GridPiece("wr", bgColor, this, currentPiece);
					}
					if(simpleName.equals("Knight") && color == Color.BLACK){
						tempPiece = new GridPiece("bn", bgColor, this, currentPiece);
					}else if(simpleName.equals("Knight") && color == Color.WHITE){
						tempPiece = new GridPiece("wn", bgColor, this, currentPiece);
					}
					if(simpleName.equals("Bishop") && color == Color.BLACK){
						tempPiece = new GridPiece("bb", bgColor, this, currentPiece);
					}else if(simpleName.equals("Bishop") && color == Color.WHITE){
						tempPiece = new GridPiece("wb", bgColor, this, currentPiece);
					}
					if(simpleName.equals("Queen") && color == Color.BLACK){
						tempPiece = new GridPiece("bq", bgColor, this, currentPiece);
					}else if(simpleName.equals("Queen") && color == Color.WHITE){
						tempPiece = new GridPiece("wq", bgColor, this, currentPiece);
					}
					if(simpleName.equals("King") && color == Color.BLACK){
						tempPiece = new GridPiece("bk", bgColor, this, currentPiece);
					}else if(simpleName.equals("King") && color == Color.WHITE){
						tempPiece = new GridPiece("wk", bgColor, this, currentPiece);
					}
					this.add(tempPiece, x, y, 1, 1);
					list[x][y] = tempPiece;
					i++;
				}
				//List is from 0 - 31. Flip boolean on multiples of 4
				if((i / 4) % 2 == 1) flip = true; //i == 4 || i == 12 || i == 20 || i == 28
				else if(i % 8 == 0) flip = false; //i == 8 || i == 16 || i == 24
			}
		}
	}

	public void selectedPiece(GridPiece gridPiece){
		boolean validSelection = false;
		for(Piece piece : game.getSelectable()) {
			if(piece == gridPiece.getPiece()){
				validSelection = true;
				selectedGridPiece = gridPiece;
			}
		}
		if(validSelection){
			//deselect any selected pieces
			for(int i = 0; i < list.length; i++){
				for(int j = 0; j < list[i].length; j++){
					list[i][j].unhighlight();
				}
			}
			//highlight possible move areas
			selectedGridPiece.highlight();
			MoveList ml = gridPiece.getPiece().getLegalMoves();
			for(int i = 0; i < ml.size(); i++){
				Move move = ml.getList().get(i);
				int x = move.getDestination().getX();
				int y = move.getDestination().getY();
				list[x][y].highlight();
				list[x][y].pos.setX((byte)x);
				list[x][y].pos.setY((byte)y);
			}
		}
	}
	
	public void makeMove(String newColor, Position position) throws Exception{
		//Set previous position to null
		int oldX = selectedGridPiece.getPiece().getPosition().getX();
		int oldY = selectedGridPiece.getPiece().getPosition().getY();
		Color bgColor = selectedGridPiece.getColor();
		list[oldX][oldY] = new GridPiece("", bgColor, this, null);
		//Move selected Piece to new position
		int newX = position.getX();
		int newY = position.getY();
		list[newX][newY] = selectedGridPiece;
		list[newX][newY].setColor(newColor);
		
		update();
	}

	public void update(){
		this.getChildren().clear();
		for(int i = 0; i < list.length; i++){
			for(int j = 0; j < list[i].length; j++){
				list[i][j].unhighlight();
				this.add(list[i][j], i, j, 1, 1);
			}
		}
	}
}
