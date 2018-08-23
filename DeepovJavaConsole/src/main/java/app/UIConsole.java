package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Game.GameMode;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.player.Deepov;
import com.gousslegend.player.Human;
import com.gousslegend.player.Player;

public class UIConsole  {

	static Scanner sc = new Scanner(System.in);
	boolean forfeit;
	Game g;
	
	public GameMode getChessMode(GameMode[] values) {
		sendMessage("Select a a game mode from below: ");
		for(int i = 0; i < values.length; i++) {
			sendMessage((i + 1) + ". " + values[i].toString());
		}
		boolean found = false;
		String input;
		int num;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num >= 1 && num <= values.length) {
					return values[num - 1];
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		
		return GameMode.STANDARD;
	}

	public void updateBoard(Board myBoard) {
		System.out.println(myBoard);
	}
	
	public Game buildGame() {
		g = new Game();
		forfeit = false;
		g.setGameMode(getChessMode(new GameMode[] {GameMode.STANDARD, GameMode.CHESS960}));
		if(getOnePlayer()) {
			if(getPlayingWhite()) {
				g.setWhitePlayer(getNewPlayer(getPlayerName(Color.WHITE)));
				g.setBlackPlayer(getNewComputerPlayer(g.getBoard()));
			}else {
				g.setBlackPlayer(getNewPlayer(getPlayerName(Color.BLACK)));
				g.setWhitePlayer(getNewComputerPlayer(g.getBoard()));
			}
		} else {
			g.setWhitePlayer(getNewPlayer(getPlayerName(Color.WHITE)));
			g.setBlackPlayer(getNewPlayer(getPlayerName(Color.BLACK)));
		}
		return g;
	}

	private Player getNewComputerPlayer(Board board) {
		return new Deepov(board);
	}

	public Player getNewPlayer(String name) {
		return new Human(name);
	}
	
	public void setTurn(Player player) {
		System.out.println("It's " + player.getName() +"'s turn!");
	}

	public static String sendMessage(String message) {
		System.out.println(message);
		return null;
	}

	public Move getMove(List<Piece> pieces, Player player) {
		if(player instanceof Human) {
			return getMove(pieces);
		}else {
			return ((Deepov) player).takeTurn();
		}
	}
		
	public Move getMove(List<Piece> pieces){
		List<Piece> movable = new ArrayList<Piece>(); 
		for(Piece p : pieces) {
			if(p.getLegalMoves().size() != 0) {
				movable.add(p);
			}
		}
		boolean moveFound = false;
		while(!moveFound) {
		Piece startingPiece = getPiece(movable);
		
		if(startingPiece == null) {
			return null;
		}
		
		List<Move> moves = startingPiece.getLegalMoves().getList();
		for(int i = 0; i < moves.size(); i++) {
			sendMessage((i + 1) + ". " + startingPiece.toString() +  " to " + moves.get(i).getDestination().toShortString());
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
		sendMessage("\n0. Forfeit.");
		boolean found = false;
		String input;
		int num;
		Piece p = null;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num == 0) {
					forfeit();
					break;
				}else if(num >= 1 && num <= movable.size()) {
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
	
	static boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	public void forfeit() {
		forfeit = true;
	}
	
	public Player getWinner() {
		if(forfeit) {
			return g.getPlayer(g.getBoard().getColorToPlay().getOppositeColor());
		}else {
			return g.getWinner();
		}
	}
	
	public boolean getPlayingWhite() {
		sendMessage("Would you like to play White or Black?");
		sendMessage("1. White");
		sendMessage("2. Black");
		boolean found = false;
		String input;
		int num;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num >= 1 && num <= 2) {
					return num == 1;
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		
		return false;
	}

	public boolean getOnePlayer() {
		sendMessage("One Player or Two Players?");
		sendMessage("1. One Player");
		sendMessage("2. Two Players");
		boolean found = false;
		String input;
		int num;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num >= 1 && num <= 2) {
					return num == 1;
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		
		return false;
	}

	public String getPlayerName(Color color) {
		sendMessage("Enter a name for "+color.toString());
		return sc.nextLine();
	}

	public boolean getContinue() {
		sendMessage("Would you like to play another game?");
		sendMessage("1. Yes");
		sendMessage("2. No");
		boolean found = false;
		String input;
		int num;
		while(!found) {
			input = sc.nextLine();
			
			if(tryParseInt(input)) {
				num = Integer.parseInt(input);
				if(num >= 1 && num <= 2) {
					return num == 1;
				} else {
					sendMessage("Please enter a number within the range.");
				}
			} else {
				sendMessage("Please enter a number");
			}
		}
		
		return false;
	}

	public void printBoard(Board board) {
		setTurn(g.getCurrentPlayer());
		updateBoard(board);
	}
}
