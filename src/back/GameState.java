package back;

import java.util.ArrayList;

public class GameState {
	public static ArrayList<Piece> pieces;
	private static boolean myTurn;
	private static boolean teamColor;
	
	public static void init(boolean turn, boolean team){
		teamColor = team;
		myTurn = turn;
		pieces = new ArrayList<Piece>();
		
		//black castles starting pieces
		pieces.add(new Piece(1,false,0,0,"Sprites/BlackCastle.png"));
		pieces.add(new Piece(1,false,7,0,"Sprites/BlackCastle.png"));
		
		//black knight starting pieces
		pieces.add(new Piece(2,false,1,0,"Sprites/BlackKnight.png"));
		pieces.add(new Piece(2,false,6,0,"Sprites/BlackKnight.png"));
		
		//black bishop starting pieces
		pieces.add(new Piece(3,false,2,0,"Sprites/BlackBishop.png"));
		pieces.add(new Piece(3,false,5,0,"Sprites/BlackBishop.png"));
		
		//black queen starting piece
		pieces.add(new Piece(4,false,3,0,"Sprites/BlackQueen.png"));
		
		//black king starting piece
		pieces.add(new Piece(5,false,4,0,"Sprites/BlackKing.png"));
		
		//black pawn starting pieces
		for(int i = 0; i < 8; i++){
			pieces.add(new Piece(0,false,i,1,"Sprites/BlackPawn.png"));
		}
		
		//white castles starting pieces
		pieces.add(new Piece(1,true,0,7,"Sprites/WhiteCastle.png"));
		pieces.add(new Piece(1,true,7,7,"Sprites/WhiteCastle.png"));
		
		//white knight starting pieces
		pieces.add(new Piece(2,true,1,7,"Sprites/WhiteKnight.png"));
		pieces.add(new Piece(2,true,6,7,"Sprites/WhiteKnight.png"));
		
		//white bishop starting pieces
		pieces.add(new Piece(3,true,2,7,"Sprites/WhiteBishop.png"));
		pieces.add(new Piece(3,true,5,7,"Sprites/WhiteBishop.png"));
		
		//white queen starting piece
		pieces.add(new Piece(4,true,3,7,"Sprites/WhiteQueen.png"));
		
		//white king starting piece
		pieces.add(new Piece(5,true,4,7,"Sprites/WhiteKing.png"));
		
		//white pawn starting pieces
		for(int i = 0; i < 8; i++){
			pieces.add(new Piece(0,true,i,6,"Sprites/WhitePawn.png"));
		}	
	}
	
	
	public static ArrayList<Piece> getState(){
		return pieces;
	}
	
	public static boolean isMyTurn(){
		//TODO: fix this
		return myTurn;
	}
	
	public static void endTurn(){
		myTurn = !myTurn;
	}
	
	public static boolean getTeamColor(){
		return teamColor;
	}
}
