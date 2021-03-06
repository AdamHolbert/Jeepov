package pieces;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Player;

import app.App;
import app.SceneName;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class ChessBoard extends GridPane {
	GridPiece[][] list = new GridPiece[8][8];
	private Game game = null;
	private GridPiece selectedGridPiece = null;
	private App app = null;
	private Label turnLabel;
	private Label moveLabel;
	private Label move2Label;
	private String moves = "";
	private String moves2 = "";
	
	public ChessBoard(Game g, App app, Label t, Label m, Label m2, Button b) throws Exception{
		this.game = g;
		this.app = app;
		this.turnLabel = t;
		this.moveLabel = m;
		this.move2Label = m2;
		this.moveLabel.setText(g.getPlayer(Color.WHITE).getName());
		this.move2Label.setText(g.getPlayer(Color.BLACK).getName());
		this.moves = g.getPlayer(Color.WHITE).getName() + "\n";
		this.moves2 = g.getPlayer(Color.BLACK).getName() + "\n";
		update();
		b.textProperty().set("Reset");
		b.setOnAction((event) -> {
			this.moves = "";
			this.moves2 = "";
			this.moveLabel.setText(g.getPlayer(Color.WHITE).getName());
			this.move2Label.setText(g.getPlayer(Color.BLACK).getName());
			game.resetGame();
			try { update(); } catch (Exception e) { e.printStackTrace(); }
		});
	}
	
	private void update() throws Exception{
		Player currentPlayer = game.getPlayer(game.getCurrentTurnColor());
		turnLabel.textProperty().set(currentPlayer.getName()+"'s Turn");
		
		this.getChildren().clear();
		if(game.isCheckmate() || game.isStalemate()) {
			app.setScene(SceneName.EndScreen);
			return;
		}
		int i = 0;
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				Color bgColor = null;
				//Empty squares between teams' pieces
				if((a - b) % 2 == 0) bgColor = Color.WHITE;
				else bgColor = Color.BLACK;
				
				list[a][b] = new GridPiece("", bgColor, this, null);
			}
		}
		//The list of pieces on board
		while(i < game.getBoardPieces().size()){
			Piece currentPiece = game.getBoardPieces().get(i);
			int x = currentPiece.getPosition().getX();
			int y = currentPiece.getPosition().getY();
			String tempPiece = "";
			Color bgColor = list[7 - x][7 - y].bgColor.equals("#000") ? Color.BLACK : Color.WHITE;
			
			String simpleName = currentPiece.getClass().getSimpleName();
			Color color = currentPiece.getColor();
			
			if(simpleName.equals("Pawn") && color == Color.BLACK){
				tempPiece = "bp";
			}else if(simpleName.equals("Pawn") && color == Color.WHITE){
				tempPiece = "wp";
			}
			if(simpleName.equals("Rook") && color == Color.BLACK){
				tempPiece = "br";
			}else if(simpleName.equals("Rook") && color == Color.WHITE){
				tempPiece = "wr";
			}
			if(simpleName.equals("Knight") && color == Color.BLACK){
				tempPiece = "bn";
			}else if(simpleName.equals("Knight") && color == Color.WHITE){
				tempPiece = "wn";
			}
			if(simpleName.equals("Bishop") && color == Color.BLACK){
				tempPiece = "bb";
			}else if(simpleName.equals("Bishop") && color == Color.WHITE){
				tempPiece = "wb";
			}
			if(simpleName.equals("Queen") && color == Color.BLACK){
				tempPiece = "bq";
			}else if(simpleName.equals("Queen") && color == Color.WHITE){
				tempPiece = "wq";
			}
			if(simpleName.equals("King") && color == Color.BLACK){
				tempPiece = "bk";
			}else if(simpleName.equals("King") && color == Color.WHITE){
				tempPiece = "wk";
			}
			list[7 - x][7 - y] = new GridPiece(tempPiece, bgColor, this, currentPiece);
			i++;
		}
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				this.add(list[7 - a][7 - b], 7 - a, 7 - b, 1, 1);
			}
		}
		if(currentPlayer instanceof Deepov) {
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished( event -> {
				Move move = ((Deepov) currentPlayer).takeTurn();
				GridPiece help = list[7 - move.getOrigin().getX()][7 - move.getOrigin().getY()];
				if(game.getCurrentTurnColor() == Color.WHITE) {
					moves += convertMoveText(move.toShortString(), help);
					moveLabel.textProperty().set(moves);
				} else {
					moves2 += convertMoveText(move.toShortString(), help);
					move2Label.textProperty().set(moves2);
				}
				game.makeMove(move);

				try { update();
				} catch (Exception e) { e.printStackTrace(); }
			});
			delay.play();
		} else {			
			highlightSelectable();
		}
	}

	private void highlightSelectable() {
		int i = 0;
		while(i < game.getSelectable().size()){
			Piece currentPiece = game.getSelectable().get(i);
			int x = currentPiece.getPosition().getX();
			int y = currentPiece.getPosition().getY();
			list[7 - x][7 - y].select();
			i++;
		}
	}

	public void selectedPiece(GridPiece gridPiece){
		boolean validSelection = false;
		if(gridPiece == selectedGridPiece){
			selectedGridPiece = null;
			
			//deselect any selected pieces
			for(int i = 0; i < list.length; i++){
				for(int j = 0; j < list[i].length; j++){
					list[i][j].unhighlight();
					list[i][j].unselect();
				}
			}
			highlightSelectable();
		} else {
			if(!(game.getPlayer(game.getCurrentTurnColor()) instanceof Deepov)) {
				for(Piece piece : game.getSelectable()) {
					if(piece == gridPiece.getPiece()){
						
						validSelection = true;
						selectedGridPiece = gridPiece;
					}
				}
			}
			if(validSelection){
				//deselect any selected pieces
				for(int i = 0; i < list.length; i++){
					for(int j = 0; j < list[i].length; j++){
						list[i][j].unhighlight();
						list[i][j].unselect();
					}
				}
				//highlight possible move areas
				selectedGridPiece.select();
				MoveList ml = gridPiece.getPiece().getLegalMoves();
				for(int i = 0; i < ml.size(); i++){
					Move move = ml.getList().get(i);
					int x = move.getDestination().getX();
					int y = move.getDestination().getY();
					GridPiece current = list[7 - x][7 - y];
					current.setMove(move);
					current.highlight();
				}
			}
		}
	}
	
	public void makeMove(Move move) throws Exception{
		if(game.getCurrentTurnColor() == Color.WHITE) {
			moves += convertMoveText(move.toShortString(), selectedGridPiece);
			moveLabel.textProperty().set(moves);
		} else {	
			moves2 += convertMoveText(move.toShortString(), selectedGridPiece);
			move2Label.textProperty().set(moves2);
		}
		game.makeMove(move);
		update();
	}
	
	public String convertMoveText(String bad, GridPiece gp) {
		String notation = "";
		switch (gp.getType()) {
			case BISHOP:
				notation += "B";
				break;
			case ROOK:
				notation += "R";
				break;
			case KNIGHT:
				notation += "N";
				break;
			case QUEEN:
				notation += "Q";
				break;
			case KING:
				notation += "K";
				break;
			case PAWN:
				break;
			default:
				System.out.println("Type is '" + gp.getType() +"'");
				break;
		}
		char change = bad.charAt(2);
		char changed = (char)(97 + (104 - (int)change));
		notation += changed + bad.substring(3, 4) + "\n";
		return notation;
	}
}
