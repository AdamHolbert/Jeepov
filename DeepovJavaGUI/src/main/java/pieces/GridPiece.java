package pieces;
import java.io.FileInputStream;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Piece.ChessPieceType;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("restriction")
public class GridPiece extends Button{
	public String bgColor;
	public Color pieceColor;
	private String name;
	public ChessBoard board;
	private Piece piece;
	public Position pos = new Position();
	private boolean isSelected = false;
	private Move move;
	
	public GridPiece(String fileName, Color bgColor, ChessBoard board, Piece currentPiece) throws Exception{
		this.name = fileName;
		this.board = board;
		this.piece = currentPiece;
		if(!fileName.equalsIgnoreCase("")){
			pieceColor = (fileName.charAt(0) == 'b') ? Color.BLACK : Color.WHITE;
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
			this.bgColor = "#000";
		}else if(bgColor == Color.WHITE){
			this.bgColor = "#fff";
		}
		this.setStyle("-fx-background-color: "+this.bgColor+"; -fx-border-width: 0px; -fx-border-radius: 0px;");
		//On Click
		this.setOnAction((value) -> {
			if(!this.isSelected && this.piece != null)
				board.selectedPiece(this);
			if(this.getMove() != null){
				try { board.makeMove(this.getMove());
				} catch (Exception e) { e.printStackTrace(); } }
		});
	}

	public void highlight() {
		isSelected = true;
		if(this.piece != null){
			this.setStyle("-fx-background-color: "+(this.bgColor.equals("#000") ? "#600" : "#faa")+"; -fx-opacity: 0.7;");
		} else {
			this.setStyle("-fx-background-color: "+(this.bgColor.equals("#000") ? "#060" : "#afa")+"; -fx-opacity: 0.7;");
		}
	}
	public void unhighlight() {
		isSelected = false;
		this.setStyle("-fx-background-color: "+this.bgColor+";");
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
		return (this.bgColor.equals("#000")) ? Color.BLACK : Color.WHITE;
	}
	public void setColor(String bgColor){
		this.bgColor = bgColor;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Move getMove() {
		return move;
	}

	public void unselect() {
		this.setStyle("-fx-background-color: " + this.bgColor +";");
	}

	public void select() {
		this.setStyle("-fx-background-color: "+(this.bgColor.equals("#000") ? "#002" : "#ddf")+"; -fx-opacity: 0.7; -fx-border-color: #00f;");
		
	}
	
}
