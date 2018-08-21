package pieces;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Piece.ChessPieceType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GridPiece extends Button{
	public String color;
	private String name;
	public ChessBoard board;
	private Piece piece;
	public Position pos = new Position();
	
	@SuppressWarnings("restriction")
	public GridPiece(String fileName, Color bgColor, ChessBoard board, Piece currentPiece) throws Exception{
		this.name = fileName;
		this.board = board;
		this.piece = currentPiece;
		if(!fileName.equalsIgnoreCase("")){
			ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/resources/"+fileName+".png")));
			imageView.setFitWidth(30);
			imageView.setFitHeight(30);
			this.setGraphic(imageView);
			this.setMinSize(50, 50);
		}
		else{
			this.setMinSize(50, 50);
			this.setMaxSize(50, 50);
		}
		if(bgColor == Color.BLACK){
			this.color = "#000";
		}else if(bgColor == Color.WHITE){
			this.color = "#fff";
		}
		this.setStyle("-fx-background-color: "+this.color+"; -fx-border-width: 0px;");
		//On Click
		this.setOnAction((value) -> {
			if(!this.getStyle().equals("-fx-background-color: #33f;") || !name.equals(""))
				board.selectedPiece(this);
			else {
				try { board.makeMove(this.color, pos);
				} catch (Exception e) { e.printStackTrace(); }
//				this.setStyle("-fx-background-color: #f33;");
			}
		});
	}

	public void highlight() {
		this.setStyle("-fx-background-color: #33f;");
	}
	public void unhighlight() {
		this.setStyle("-fx-background-color: "+this.color+";");
	}

	public ChessPieceType getType(){
		ChessPieceType type = null;
		if(name.contains("p"))
			type = ChessPieceType.PAWN;
		else if(name.equals("bb") || name.equals("wb"))
			type = ChessPieceType.BISHOP;
		else if(name.contains("r"))
			type = ChessPieceType.ROOK;
		else if(name.contains("n"))
			type = ChessPieceType.KNIGHT;
		else if(name.contains("q"))
			type = ChessPieceType.QUEEN;
		else if(name.contains("k"))
			type = ChessPieceType.KING;
		else
			type = null;
		return type;
	}

	public Piece getPiece() {
		return piece;
	}
	public Color getColor(){
		return (this.color.equals("#000")) ? Color.BLACK : Color.WHITE;
	}
	public void setColor(String bgColor){
		this.color = bgColor;
	}
}
