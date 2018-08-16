package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Game.GameMode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

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
	public void start(Stage pStage) {
		g = new Game();
		stage = new Stage();
		setScene(SceneName.Loading);
		stage.show();
	}

	private void setGameMode(GameMode mode) {
		
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
					setGameMode(mode);
					g.setGameMode(mode);
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
		}
		stage.setScene(scene);
	}
}
