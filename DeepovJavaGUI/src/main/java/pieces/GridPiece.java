package pieces;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Color;
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
	
	@SuppressWarnings("restriction")
	public GridPiece(String fileName, Color bgColor, ChessBoard board) throws Exception{
		this.name = fileName;
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
			this.color = "#000000";
		}else if(bgColor == Color.WHITE){
			this.color = "#ffffff";
		}
		this.setStyle("-fx-background-color: "+this.color+"; -fx-border-width: 0px;");
		//Selected
		this.setOnAction((value) -> {				
			
		});
	}

	public void selected() {
		this.setStyle("-fx-border-color: #00ff00; -fx-background-color: "+this.color+";");
	}
	public void unselected() {
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
}
