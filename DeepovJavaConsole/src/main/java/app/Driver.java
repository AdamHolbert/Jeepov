package app;

import com.gousslegend.deepov.Game;

public class Driver {

	public static void main(String[] args) {
		System.out.println("In console project");
		UIConsole ui = new UIConsole();
		Game g;
		do {
			g = ui.buildGame();
			while(ui.getWinner() == null) {
				ui.printBoard();
				g.makeMove(ui.getMove(g.getSelectable(),g.getCurrentPlayer()));
			}
		} while(ui.getContinue());
	}
}
