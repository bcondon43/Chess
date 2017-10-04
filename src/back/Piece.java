package back;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Piece implements Serializable{
	

	
	
	private static final long serialVersionUID = 1L;
	/**
	 * Piece Types:
		Pawn = 0,
		Castle = 1,
		Knight = 2,
		Bishop = 3,
		Queen = 4,
		King = 5
	 */
	private int piece_type;
	private int x,y;
	private boolean isWhite;
	private boolean hasMoved;
	private String path;
	public Piece(int piece_type, boolean isWhite, int x, int y, String path){
		this.piece_type = piece_type;
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
		this.path = path;
		hasMoved = false;
	}
	
	public Piece(Piece old_piece){
		this.piece_type = old_piece.getType();
		this.x = old_piece.getX();
		this.y = old_piece.getY();
		this.isWhite = old_piece.isWhite();
	}
	public void setXY(int new_x, int new_y){
		x = new_x;
		y = new_y;
		hasMoved = true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	/**
	 * Piece Types:
		Pawn = 0,
		Castle = 1,
		Knight = 2,
		Bishop = 3,
		Queen = 4,
		King = 5
	 */
	public void setType(short type){
		piece_type = type;
	}
	
	/**
	 * Piece Types:
		Pawn = 0,
		Castle = 1,
		Knight = 2,
		Bishop = 3,
		Queen = 4,
		King = 5
	 */
	public int getType(){
		return piece_type;
	}
	
	public boolean isWhite(){
		return isWhite;
	}
	
	public ImageIcon load(){
		ImageIcon image = new ImageIcon(path);
		return image;
	}
	
	public Image getImage() throws Exception{
		Image actual = ImageIO.read(new File(path));
		return actual;
	}
	public Boolean hasMoved() {
		return hasMoved;
	}
}
