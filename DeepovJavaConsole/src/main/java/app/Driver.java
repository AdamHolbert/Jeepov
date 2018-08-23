package app;

import com.gousslegend.deepov.Game;

public class Driver {

	public static void main(String[] args) {
		System.out.println("In console project");
		Game g = new Game();
		do {
			g.buildGame();
			while(g.getWinner() == null) {
				g.printBoard();
				g.makeMove(UIConsole.getMove(g.getSelectable(),g.getCurrentPlayer()));
			}
		} while(UIConsole.getContinue());
	}
}
