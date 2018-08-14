package app;

import java.util.List;
import java.util.Scanner;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game.ChessModes;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.UserInterface;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Human;
import com.gousslegend.player.Player;

public class UIConsole implements UserInterface {

	public ChessModes getChessMode(ChessModes[] values) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Click enter if you want to play normal chess.");
		System.out.println("If you want to play Chess 960, type anything, then click enter.");
		String input = sc.nextLine();
		
		boolean chess960 = !input.isEmpty();
		
		return chess960 ? ChessModes.CHESS960 : ChessModes.STANDARD;
	}

	public void updateBoard(Board myBoard) {
		System.out.println(myBoard);
	}

	public Player getNewPlayer(Color playerColor, Board myBoard) {
		Player p = new Human("Human" + playerColor.toString(), myBoard, this);
		if(playerColor == Color.BLACK) {
			p = new Deepov(myBoard);
		}
		return p;
	}
	
	public void setTurn(Player player) {
		System.out.println("It's " + player.getName() +"'s turn!");
	}

	public String sendMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public Move getMove(List<Piece> pieces) {
		// TODO Auto-generated method stub
		return null;
	}
}
