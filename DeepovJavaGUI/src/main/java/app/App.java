package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Game.GameMode;
import com.gousslegend.player.Human;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import pieces.ChessBoard;
import javafx.geometry.Orientation;
import javafx.geometry.HPos;

@SuppressWarnings("restriction")
public class App extends Application {
	Game g;
	Stage stage;
	Color computerColor;
	final double WIDTH = 500;
	final double HEIGHT = 500;
	final String PANE_STYLE = "-fx-background-color: linear-gradient(#4578ee, #12349d);";
	final String BUTTON_STYLE = "-fx-background-color: linear-gradient(#77a9ff, #ffffff);";
	

	public App() {}
	
	public App(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage pStage) throws Exception {
		g = new Game();
		stage = new Stage();
		setScene(SceneName.Loading);
		stage.show();
	}
	
	public void resetVariables() {
		g = new Game();
		computerColor = null;
		setScene(SceneName.MainMenu);
	}
	
	public void setScene(SceneName sn) {
		Scene scene = new Scene(new GridPane());
		if(sn == SceneName.Loading) {
			stage.setTitle("Loading Screen...");
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ImageView iv = new ImageView();
			ImageView iv1 = new ImageView();
			try {
				iv.setImage(new Image(new FileInputStream("src/main/resources/wq.png")));
				iv1.setImage(new Image(new FileInputStream("src/main/resources/bq.png")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		    FadeTransition ft1 = new FadeTransition(Duration.millis(3000), iv1);
		    ft1.setFromValue(0);
		    ft1.setToValue(1.0);
		    ft1.setCycleCount(Integer.MAX_VALUE);
		    ft1.setAutoReverse(true);
		    ft1.play();
			
		    gp.getChildren().addAll(iv, iv1);
		    gp.setPadding(new Insets((WIDTH - 284) / 2, (WIDTH - 284) / 2, (WIDTH - 284) / 2, (WIDTH - 284) / 2));
		    scene = new Scene(gp, WIDTH, HEIGHT);
			
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished( event -> setScene(SceneName.MainMenu));
			delay.play();
		} else if(sn == SceneName.MainMenu) {
			stage.setTitle("Menu Screen");
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(60);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(40);
			gp.getColumnConstraints().addAll(col1, col2);
			RowConstraints row1 = new RowConstraints();
			row1.setPercentHeight(100 / (GameMode.values().length + 1));
			
			int i = 0;
			for(GameMode mode: GameMode.values()) {
				Button newGame = new Button();
				newGame.setText("Start new " + mode.toString() + " game");
				newGame.setPrefSize((WIDTH * .4) - 50, (HEIGHT / (GameMode.values().length + 1)) / 2);
				newGame.setWrapText(true);
				newGame.setTextAlignment(TextAlignment.CENTER);
				newGame.setStyle(BUTTON_STYLE);
				newGame.setOnAction((event) -> {
					g.setGameMode(mode);
					setScene(SceneName.PlayerMenu);
				});
				gp.getRowConstraints().add(row1);
				gp.add(newGame, 1, i++);
				GridPane.setHalignment(newGame, HPos.CENTER);
			}
			
			Button exitGame = new Button();
			exitGame.setText("Exit the Application");
			exitGame.setPrefSize((WIDTH * .4) - 50, (HEIGHT / (GameMode.values().length + 1)) / 2);
			exitGame.setWrapText(true);
			exitGame.setTextAlignment(TextAlignment.CENTER);
			exitGame.setStyle(BUTTON_STYLE);
			exitGame.setOnAction((event) -> {
				System.exit(0);
			});
			gp.getRowConstraints().add(row1);
			gp.add(exitGame, 1, GameMode.values().length);
			GridPane.setHalignment(exitGame, HPos.CENTER);
			
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
			GridPane.setHalignment(iv, HPos.CENTER);
			GridPane.setHalignment(iv1, HPos.CENTER);
		    
		    FadeTransition ft1 = new FadeTransition(Duration.millis(3000), iv1);
		    ft1.setFromValue(0);
		    ft1.setToValue(1.0);
		    ft1.setCycleCount(Integer.MAX_VALUE);
		    ft1.setAutoReverse(true);
		    ft1.play();
			
		    scene = new Scene(gp, WIDTH, HEIGHT);
		} else if(sn == SceneName.PlayerMenu) {
			stage.setTitle("Player Select");
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(50);
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(50);
			gp.getColumnConstraints().addAll(col, col);
			gp.getRowConstraints().addAll(row, row);
			
			Label label = new Label();
			label.textProperty().set("How many players would you like to play?");
			label.setStyle("-fx-font-size: 40;");
			label.setWrapText(true);
			label.setTextAlignment(TextAlignment.CENTER);
			Button pvp = new Button();
			pvp.textProperty().set("2 Players");
			pvp.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			pvp.setOnAction((event) -> {
				setScene(SceneName.TwoPlayer);
			});
			Button pvc = new Button();
			pvc.textProperty().set("1 Player");
			pvc.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			pvc.setOnAction((event) -> {
				setScene(SceneName.OnePlayer);
			});
			
			gp.add(label, 0, 0);
			gp.add(pvc, 0, 1);
			gp.add(pvp, 1, 1);
			GridPane.setColumnSpan(label, 2);
			GridPane.setHalignment(label, HPos.CENTER);
			GridPane.setHalignment(pvp, HPos.CENTER);
			GridPane.setHalignment(pvc, HPos.CENTER);
			
			scene = new Scene(gp, WIDTH, HEIGHT);
		} else if(sn == SceneName.OnePlayer) {
			stage.setTitle("Player Setup");
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(50);
			RowConstraints row1 = new RowConstraints();
			row1.setPercentHeight(45);
			RowConstraints row2 = new RowConstraints();
			row2.setPercentHeight(5);
			RowConstraints row3 = new RowConstraints();
			row3.setPercentHeight(50);
			gp.getColumnConstraints().addAll(col, col);
			gp.getRowConstraints().addAll(row1, row2, row3);
			
			Label label = new Label();
			label.textProperty().set("What do you want your name to be?");
			label.setStyle("-fx-font-size: 40;");
			label.setWrapText(true);
			label.setTextAlignment(TextAlignment.CENTER);
			
			TextField tf = new TextField();
			tf.setMaxWidth(WIDTH - 300);
			tf.setText("Player 1");
			tf.setFocusTraversable(false);
			
			Button b = new Button();
			b.textProperty().set("White");
			b.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			b.setOnAction((event) -> {
				g.setWhitePlayer(new Human(tf.getText()));
				g.setBlackPlayer(g.getNewComputerPlayer());
				computerColor = Color.BLACK;
				setScene(SceneName.ChessGame);
			});
			Button w = new Button();
			w.textProperty().set("Black");
			w.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			w.setOnAction((event) -> {
				g.setBlackPlayer(new Human(tf.getText()));
				g.setWhitePlayer(g.getNewComputerPlayer());
				computerColor = Color.WHITE;
				setScene(SceneName.ChessGame);
			});
			
			gp.add(label, 0, 0);
			gp.add(tf, 0, 1);
			gp.add(b, 0, 2);
			gp.add(w, 1, 2);
			GridPane.setColumnSpan(label, 2);
			GridPane.setColumnSpan(tf, 2);
			GridPane.setHalignment(label, HPos.CENTER);
			GridPane.setHalignment(tf, HPos.CENTER);
			GridPane.setHalignment(b, HPos.CENTER);
			GridPane.setHalignment(w, HPos.CENTER);
			
			scene = new Scene(gp, WIDTH, HEIGHT);
		} else if(sn == SceneName.TwoPlayer) {
			stage.setTitle("Player Setup");
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(50);
			RowConstraints row1 = new RowConstraints();
			row1.setPercentHeight(45);
			RowConstraints row2 = new RowConstraints();
			row2.setPercentHeight(5);
			RowConstraints row3 = new RowConstraints();
			row3.setPercentHeight(50);
			gp.getColumnConstraints().addAll(col, col);
			gp.getRowConstraints().addAll(row1, row2, row3);
			
			Label label = new Label();
			label.textProperty().set("White name?");
			label.setStyle("-fx-font-size: 40;");
			label.setTextAlignment(TextAlignment.CENTER);
			
			Label label1 = new Label();
			label1.textProperty().set("Black name?");
			label1.setStyle("-fx-font-size: 40;");
			label1.setTextAlignment(TextAlignment.CENTER);
			
			TextField tf = new TextField();
			tf.setMaxWidth(WIDTH - 300);
			tf.setText("Player 1");
			tf.setFocusTraversable(false);
			
			TextField tf1 = new TextField();
			tf1.setMaxWidth(WIDTH - 300);
			tf1.setText("Player 2");
			tf1.setFocusTraversable(false);
			
			Button b = new Button();
			b.textProperty().set("Done");
			b.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			b.setOnAction((event) -> {
				g.setBlackPlayer(new Human(tf1.getText()));
				g.setWhitePlayer(new Human(tf.getText()));
				setScene(SceneName.ChessGame);
			});
			
			gp.add(label, 0, 0);
			gp.add(label1, 1, 0);
			gp.add(tf, 0, 1);
			gp.add(tf1, 1, 1);
			gp.add(b, 0, 2);
			GridPane.setColumnSpan(b, 2);
			GridPane.setHalignment(label, HPos.CENTER);
			GridPane.setHalignment(label1, HPos.CENTER);
			GridPane.setHalignment(tf, HPos.CENTER);
			GridPane.setHalignment(tf1, HPos.CENTER);
			GridPane.setHalignment(b, HPos.CENTER);
			
			scene = new Scene(gp, WIDTH, HEIGHT);
		} else if(sn == SceneName.ChessGame) {
			stage.setTitle("Game");
			
			Label turnLabel = new Label();
			turnLabel.setStyle("-fx-font-size: 25;");
			turnLabel.setWrapText(true);
			turnLabel.setTextAlignment(TextAlignment.CENTER);
			
			Label moveLabel = new Label();
			moveLabel.setWrapText(true);
			moveLabel.setTextAlignment(TextAlignment.CENTER);
			
			Button resetBtn = new Button();
			resetBtn.setStyle("-fx-font-size: 18; " + BUTTON_STYLE);
			
			ChessBoard gridPane = null;
			try { gridPane = new ChessBoard(g, this, turnLabel, moveLabel, resetBtn);
			} catch (Exception e) { e.printStackTrace(); }
			
			FlowPane flowPane = new FlowPane();
			flowPane.orientationProperty().set(Orientation.VERTICAL);
			flowPane.getChildren().addAll(resetBtn, turnLabel, moveLabel);
			
			TilePane tilePane = new TilePane(Orientation.VERTICAL);
			tilePane.getChildren().addAll(gridPane, flowPane);

	        scene = new Scene(tilePane, 600, 400);
		} else if(sn == SceneName.EndScreen) {
			String endString = "";
			if(g.isStalemate()) {
				endString = "It's a draw.";
			} else {
				if((g.getPlayer(Color.BLACK) instanceof Human) && (g.getPlayer(Color.WHITE) instanceof Human)) {
					endString = g.getWinner().getName() + " won!";
				} else {
					endString = g.getWinner() instanceof Human ? "You won!" : "You lost...";
				}
			}
			stage.setTitle(endString);
			
			GridPane gp = new GridPane();
			gp.setStyle(PANE_STYLE);
			
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(50);
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(50);
			gp.getColumnConstraints().addAll(col, col);
			gp.getRowConstraints().addAll(row, row);
			
			Label label = new Label();
			label.textProperty().set(endString);
			label.setStyle("-fx-font-size: 40;");
			label.setWrapText(true);
			label.setTextAlignment(TextAlignment.CENTER);
			Button playagain = new Button();
			playagain.textProperty().set("Replay");
			playagain.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			playagain.setOnAction((event) -> {
				resetVariables();
			});
			Button end = new Button();
			end.textProperty().set("Exit Game");
			end.setStyle("-fx-font-size: 30; " + BUTTON_STYLE);
			end.setOnAction((event) -> {
				System.exit(0);
			});
			
			gp.add(label, 0, 0);
			gp.add(playagain, 0, 1);
			gp.add(end, 1, 1);
			GridPane.setColumnSpan(label, 2);
			GridPane.setHalignment(label, HPos.CENTER);
			GridPane.setHalignment(playagain, HPos.CENTER);
			GridPane.setHalignment(end, HPos.CENTER);
			
			scene = new Scene(gp, WIDTH, HEIGHT);
		}
		stage.setScene(scene);
	}

}
