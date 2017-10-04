package back;

import java.util.ArrayList;
import java.util.Stack;

public class ActionChecker {
	
	public static boolean check(Piece piece, int x, int y, ArrayList<Piece> pieces){
		return check_piece_movement(piece,x,y,pieces) && check_killing_own_team(piece,x,y,pieces) && check_king_danger(piece,x,y,pieces);
	}
	
	private static boolean check_king_danger(Piece piece, int x, int y, ArrayList<Piece> pieces){
		ArrayList<Piece> new_board_state = new ArrayList<Piece>(pieces);
		for(Piece o_piece: new_board_state){
			if(o_piece.getX() == x && o_piece.getY() == y){
				new_board_state.remove(o_piece);
				break;
			}
		}
		new_board_state.remove(piece);
		Piece temp = new Piece(piece);
		temp.setXY(x, y);
		new_board_state.add(temp);
		Piece king = null;
		Stack<Piece> enemy_pieces = new Stack<Piece>();
		for(Piece o_piece: new_board_state){
			if(piece.isWhite() != o_piece.isWhite()){
				enemy_pieces.push(o_piece);
			}
			if(o_piece.getType() == 5 && o_piece.isWhite() == piece.isWhite()){
				king = o_piece;
			}
		}
		
		for(Piece enemy: enemy_pieces){
			if(check_piece_movement(enemy,king.getX(),king.getY(),new_board_state))
				return false;
		}
		return true;
		
	}
	
	private static boolean check_killing_own_team(Piece piece, int x, int y, ArrayList<Piece> pieces){
		for(Piece o_piece: pieces){
			if(x == o_piece.getX() && y == o_piece.getY()){
				return o_piece.isWhite() != piece.isWhite();
			}
		}
		return true;
	}
	
	private static boolean check_piece_movement(Piece piece, int x, int y, ArrayList<Piece> pieces){
		switch(piece.getType()){
		case 0:
			return check_pawn_move(piece,x,y,pieces);
		case 1:
			return check_castle_move(piece,x,y,pieces);
		case 2: 
			return check_knight_move(piece,x,y);
		case 3:
			return check_bishop_move(piece,x,y,pieces);
		case 4: 
			return check_castle_move(piece,x,y,pieces) || check_bishop_move(piece,x,y,pieces);
		case 5: 
			return check_king_move(piece,x,y,pieces);
		}
		return false;
	}

	private static boolean check_pawn_move(Piece piece, int x, int y, ArrayList<Piece> pieces){
		for(Piece temp: pieces){
			if(temp.getX() == x && temp.getY() == y && piece.getX() == x){
				return false;
			}
		}
		if(piece.isWhite()){
			if(y == piece.getY() - 1 && x == piece.getX()){
				return true;
			}
			else if(y == piece.getY() - 2 && x == piece.getX()){
				return piece.getY() == 6;
			}
			else if(y == piece.getY() - 1 && (x == piece.getX() + 1 || x == piece.getX() - 1)){
				for(Piece other_pieces: pieces){
					if(other_pieces.getX() == x && other_pieces.getY() == y){
						if(other_pieces.isWhite() != piece.isWhite())
							return true;
					}
				}
			}
		}
		else{
			if(y == piece.getY() + 1 && x == piece.getX()){
				return true;
			}
			else if(y == piece.getY() + 2 && x == piece.getX()){
				return piece.getY() == 1;
			}
			else if(y == piece.getY() + 1 && (x == piece.getX() + 1 || x == piece.getX() - 1)){
				for(Piece other_pieces: pieces){
					if(other_pieces.getX() == x && other_pieces.getY() == y){
						if(other_pieces.isWhite() != piece.isWhite())
							return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean check_castle_move(Piece piece, int x, int y, ArrayList<Piece> pieces){
		if(piece.getX() != x && piece.getY() != y){
			return false;
		}
		if(piece.getX() == x){
			if(y > piece.getY()){
				for(int i = piece.getY() + 1; i < y; i++){
					for(Piece o_piece: pieces){
						if(o_piece.getX() == x && o_piece.getY() == i)
							return false;
					}
				}
			}
			else if(y < piece.getY()){
				for(int i = piece.getY() - 1; i > y; i--){
					for(Piece o_piece: pieces){
						if(o_piece.getX() == x && o_piece.getY() == i)
							return false;
					}
				}
			}
			else{
				return false;
			}
		}
		else if(piece.getY() == y){
			if(x > piece.getX()){
				for(int i = piece.getX() + 1; i < x; i++){
					for(Piece o_piece: pieces){
						if(o_piece.getY() == y && o_piece.getX() == i)
							return false;
					}
				}
			}
			else if(x < piece.getX()){
				for(int i = piece.getX() - 1; i > x; i--){
					for(Piece o_piece: pieces){
						if(o_piece.getY() == y && o_piece.getX() == i)
							return false;
					}
				}
			}
			else{
				return false;
			}
		}
		return true;
	}
	
	
	private static boolean check_knight_move(Piece piece, int x, int y){
		if(((int)Math.abs(piece.getX() - x)) == 2 && (Math.abs(piece.getY() - y)) == 1){
			return true;
		}
		else if(((int)Math.abs(piece.getY() - y)) == 2 && (Math.abs(piece.getX() - x)) == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static boolean check_bishop_move(Piece piece, int x, int y, ArrayList<Piece> pieces){
		System.out.println(" ");
		if(Math.abs(piece.getY() - y) != Math.abs(piece.getX() - x)){
			return false;
		}
		if(piece.getX() == x || piece.getY() == y){
			return false;
		}
		int x_offset = -(piece.getX() - x) / Math.abs(piece.getX() - x);
		int y_offset = -(piece.getY() - y) / Math.abs(piece.getY() - y);
		int x_i = piece.getX() + x_offset;
		int y_i = piece.getY() + y_offset;
		while(x_i != x && y_i != y){
			for(Piece o_piece: pieces){
				if(o_piece.getX() == x_i && o_piece.getY() == y_i){
					return false;
				}
			}
			x_i += x_offset;
			y_i += y_offset;
		}
		return true;
	}
	
	
	private static boolean check_king_move(Piece piece, int x, int y, ArrayList<Piece> pieces){
		if(Math.abs(piece.getX() - x) > 1 ||Math.abs(piece.getY() - y) > 1) 
			return false;
		return true;
	}
	
}


