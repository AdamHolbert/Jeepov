package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Game.GameMode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
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
	final double WIDTH = 500;
	final double HEIGHT = 500;

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


//		pStage.setTitle("GridPane Title");
//		g = new Game();
//		g.setGameMode(GameMode.STANDARD);
//		
//		ChessBoard gridPane = new ChessBoard(g);
//		FlowPane flowPane = new FlowPane();
//		flowPane.getChildren().addAll();
//        
//		TilePane tilePane = new TilePane(Orientation.VERTICAL);
//		tilePane.getChildren().addAll(gridPane, flowPane);
//
//        Scene scene = new Scene(tilePane, 600, 400);
//        pStage.setScene(scene);
//        pStage.show();
	}
	
	public void setScene(SceneName sn) {
		Scene scene = new Scene(new GridPane());
		if(sn == SceneName.Loading) {
			stage.setTitle("Loading Screen...");
			GridPane gp = new GridPane();
			ImageView iv = new ImageView();
			ImageView iv1 = new ImageView();
			try {
				iv.setImage(new Image(new FileInputStream("src/main/resources/wq.png")));
				iv1.setImage(new Image(new FileInputStream("src/main/resources/bq.png")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
			
		    gp.getChildren().addAll(iv, iv1);
		    gp.setPadding(new Insets((WIDTH - 284) / 2, (WIDTH - 284) / 2, (WIDTH - 284) / 2, (WIDTH - 284) / 2));
		    scene = new Scene(gp, WIDTH, HEIGHT);
			
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished( event -> setScene(SceneName.MainMenu));
			delay.play();
		} else if(sn == SceneName.MainMenu) {
			stage.setTitle("Menu Screen");
			
			GridPane gp = new GridPane();
			
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
				newGame.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
				newGame.setOnAction((event) -> {
					g.setGameMode(mode);
					setScene(SceneName.PlayerMenu);
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
			
			gp.add(exitGame, 1, GameMode.values().length);
			
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
			
		    scene = new Scene(gp, WIDTH, HEIGHT);
		} else if(sn == SceneName.PlayerMenu) {
			stage.setTitle("Player Select");
			
			GridPane gp = new GridPane();
			
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
			pvp.setStyle("-fx-font-size: 30;");
			pvp.setOnAction((event) -> {
				//set to 2 player
				//go on to game
			});
			Button pvc = new Button();
			pvc.textProperty().set("1 Player");
			pvc.setStyle("-fx-font-size: 30;");
			pvc.setOnAction((event) -> {
				//set to one player
				//go on to game
			});
			
			gp.add(label, 0, 0);
			gp.add(pvc, 0, 1);
			gp.add(pvp, 1, 1);
			GridPane.setColumnSpan(label, 2);
			GridPane.setHalignment(label, HPos.CENTER);
			GridPane.setHalignment(pvp, HPos.CENTER);
			GridPane.setHalignment(pvc, HPos.CENTER);
			
			scene = new Scene(gp, WIDTH, HEIGHT);
		}
		stage.setScene(scene);
	}
}
