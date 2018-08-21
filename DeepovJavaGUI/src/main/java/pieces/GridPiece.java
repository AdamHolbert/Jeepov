package pieces;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.gousslegend.deepov.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GridPiece extends Button{
	private String selected = null;
	public String color;
	
	@SuppressWarnings("restriction")
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
			this.setStyle("-fx-background-color: #000000; -fx-border-width: 0px;");
			this.color = "#000000";
		}else if(bgColor == Color.WHITE){
			this.setStyle("-fx-background-color: #ffffff; -fx-border-width: 0px;");
			this.color = "#ffffff";
		}
		//Selected
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent value) {
				Button b = (Button)value.getSource();
				GridPiece gp = (GridPiece)b;
				gp.selected = "Selected";				
				b.setStyle("-fx-border-color: #00ff00; -fx-background-color: "+gp.color+";");
			}
		});
		//On Hover
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
//				GridPiece.this.setStyle("-fx-border-color: #00ff00; -fx-background-color: "+GridPiece.this.color);
			}
		});
		//Hover off
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
//				GridPiece.this.setStyle("-fx-background-color: "+GridPiece.this.color);
			}
		});
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
