package app;

import com.gousslegend.deepov.Game;

public class Driver {

	public static void main(String[] args) {
		System.out.println("In console project");
		Game g = new Game(new UIConsole());
		g.play();
	}
}
