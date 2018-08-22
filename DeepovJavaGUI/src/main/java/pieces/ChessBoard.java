package pieces;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Player;

import app.App;
import javafx.animation.PauseTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ChessBoard extends GridPane {
	GridPiece[][] list = new GridPiece[8][8];
	private Game game = null;
	private GridPiece selectedGridPiece = null;
	private Color computerColor;
	private App app = null;
	
	public ChessBoard(Game g, Color pcColor, App app) throws Exception{
		this.game = g;
		this.app = app;
		update();
	}
	
	@SuppressWarnings("restriction")
	private void update() throws Exception{
		this.getChildren().clear();
		if(game.isCheckmate() || game.isStalemate()) {
//			app.setScene(SceneName.EndScreen);
			return;
		}
		int i = 0;
		boolean flip = false;
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				Color bgColor = null;
				//Empty squares between teams' pieces
				if((a - b) % 2 == 0) bgColor = Color.BLACK;
				else bgColor = Color.WHITE;
				
				GridPiece tempPiece = new GridPiece("", bgColor, this, null);
				list[a][b] = tempPiece;
				//The list of pieces on board
				
				//List is from 0 - 31. Flip boolean on multiples of 4
				if((i / 4) % 2 == 1) flip = true; //i == 4 || i == 12 || i == 20 || i == 28
				else if(i % 8 == 0) flip = false; //i == 8 || i == 16 || i == 24
			}
		}
		while(i < game.getBoardPieces().size()){
			
			Piece currentPiece = game.getBoardPieces().get(i);
			int x = currentPiece.getPosition().getX();
			int y = currentPiece.getPosition().getY();
			GridPiece tempPiece = null;
			Color bgColor = list[x][y].bgColor.equals("#000") ? Color.BLACK : Color.WHITE;
			
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
			list[x][y] = tempPiece;
			i++;
		}
		for(int a = 0; a < 8; a++){
			for(int b = 0; b < 8; b++){
				this.add(list[a][b], a, b, 1, 1);
			}
		}
		Player currentPlayer = game.getPlayer(game.getCurrentTurnColor());
		if(currentPlayer instanceof Deepov) {
			@SuppressWarnings("restriction")
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished( event -> {
				game.makeMove(((Deepov) currentPlayer).takeTurn());
				try {
					update();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			list[x][y].select();
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
					GridPiece current = list[x][y];
					current.setMove(move);
					current.highlight();
				}
			}
		}
	}
	
	public void makeMove(Move move) throws Exception{
		game.makeMove(move);
		update();
	}
}
