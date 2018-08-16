package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.UserInterface;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Player;
import com.gousslegend.deepov.Game.ChessModes;
import com.gousslegend.deepov.Move;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import pieces.ChessBoard;
import pieces.GridPiece;

@SuppressWarnings("restriction")
public class App extends Application implements UserInterface {
	ChessModes gameMode;
	Game g;
	Scene scene;
	Stage stage;

	public App() {}
	
	public App(String[] args) {
		Application.launch(args);
	}
	
	void run(Game g) {
		this.g = g;
	}

	@Override
	public void start(Stage pStage) throws Exception{
		
	}


	@Override
	public ChessModes getChessMode(ChessModes[] values) {
		setChessModeView(values);
		gameMode = null;
		while(gameMode == null);
		ChessModes set = gameMode;
		gameMode = null;
		return set;
	}
	
	public void setChessModeView(ChessModes[] values) {
		stage = new Stage();
		stage.setTitle("Menu Screen");
		
		GridPane gp = new GridPane();
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(60);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(40);
		gp.getColumnConstraints().addAll(col1, col2);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(100 / (values.length + 1));
		
		int i = 0;
		for(ChessModes mode: values ) {
			Button newGame = new Button();
			newGame.setText("Start new " + mode.toString() + " game");
			newGame.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
			newGame.setOnAction((event) -> {
				gameMode = mode;
			});
			gp.getRowConstraints().add(row1);
			gp.add(newGame, 1, i++);
		}
		
		Button exitGame = new Button();
		exitGame.setText("Exit the Application");
		exitGame.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
		exitGame.setOnAction((event) -> {
			System.exit(0);
		});
		
		gp.getRowConstraints().add(row1);
		
		gp.add(exitGame, 1, values.length);
		
		ImageView iv = new ImageView();
		ImageView iv1 = new ImageView();
		try {
			iv.setImage(new Image(new FileInputStream("src/main/resources/wq.png")));
			iv1.setImage(new Image(new FileInputStream("src/main/resources/bq.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		gp.add(iv, 0, 0);
		gp.add(iv1, 0, 0);
		GridPane.setRowSpan(iv, 3);
		GridPane.setRowSpan(iv1, 3);
		
		FadeTransition ft = new FadeTransition(Duration.millis(3000), iv);
	    ft.setFromValue(1.0);
	    ft.setToValue(0);
	    ft.setCycleCount(Integer.MAX_VALUE);
	    ft.setAutoReverse(true);
	    ft.play();
	    
	    FadeTransition ft1 = new FadeTransition(Duration.millis(3000), iv1);
	    ft1.setFromValue(0);
	    ft1.setToValue(1.0);
	    ft1.setCycleCount(Integer.MAX_VALUE);
	    ft1.setAutoReverse(true);
	    ft1.play();
		
	    scene = new Scene(gp, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void updateBoard(Board myBoard) {
		
	}

	@Override
	public Player getNewPlayer(Color playerColor, Board myBoard) {
		return null;
	}

	@Override
	public void setTurn(Player player) {
		
	}

	@Override
	public String sendMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move getMove(List<Piece> pieces) {
		// TODO Auto-generated method stub
		return null;
	}

}
