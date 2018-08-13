package pieces;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GridPiece extends Button{

	public GridPiece(String filePath, String bgColor) throws Exception{
		if(!filePath.equalsIgnoreCase("")){
			ImageView imageView = new ImageView(new Image(new FileInputStream(filePath)));
			imageView.setFitWidth(30);
			imageView.setFitHeight(30);
			this.setGraphic(imageView);
			this.setMinSize(50, 50);
		}
		else{
			this.setMinSize(50, 50);
			this.setMaxSize(50, 50);
		}
		if(bgColor.equalsIgnoreCase("B")){
			this.setStyle("-fx-background-color: #000000");
		}else if(bgColor.equalsIgnoreCase("W")){
			this.setStyle("-fx-background-color: #ffffff");
		}
	}
}
