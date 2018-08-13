package pieces;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Color;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GridPiece extends Button{

	public GridPiece(String fileName, Color bgColor) throws Exception{
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
			this.setStyle("-fx-background-color: #000000");
		}else if(bgColor == Color.WHITE){
			this.setStyle("-fx-background-color: #ffffff");
		}
	}
}
