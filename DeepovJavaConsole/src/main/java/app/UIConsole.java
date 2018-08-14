package app;

import java.util.ArrayList;
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

	Scanner sc = new Scanner(System.in);
	public ChessModes getChessMode(ChessModes[] values) {
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
		System.out.println(message);
		return null;
	}

	public Move getMove(List<Piece> pieces) {
		List<Piece> movable = new ArrayList<Piece>(); 
		for(Piece p : pieces) {
			if(p.getLegalMoves().size() != 0) {
				movable.add(p);
			}
		}
		boolean moveFound = false;
		while(!moveFound) {
		Piece startingPiece = getPiece(movable);
		
		List<Move> moves = startingPiece.getLegalMoves().getList();
		for(int i = 0; i < moves.size(); i++) {
			sendMessage((i + 1) + ". " + startingPiece.toString() +  " to " + moves.get(i).getDestination().toString());
		}
		sendMessage("\n0. Select new piece.");
		
		boolean found = false;
		String input;
		int num;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num == 0) {
					found = true;
				} else if(num >= 1 && num <= moves.size()) {
					return moves.get(num - 1);
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		}
		return null;
	}
	
	Piece getPiece(List<Piece> movable) {
		sendMessage("Select a piece from below to move: ");
		for(int i = 0; i < movable.size(); i++) {
			sendMessage((i + 1) + ". " + movable.get(i).toString());
		}
		boolean found = false;
		String input;
		int num;
		Piece p = null;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num >= 1 && num <= movable.size()) {
					return movable.get(num - 1);
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		
		return p;
	}
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
}
