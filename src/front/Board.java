package front; 

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import back.ActionChecker;
import back.GameState;
import back.Piece;
import main.Network;


public class Board extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	private Dimension dim = new Dimension(384,384);
	private boolean targeted = false;
	private JLabel[] stateImages;
	private Piece clicked_piece;
	private Network network;
	
	public Board(Network network) throws UnknownHostException, IOException{
		this.network = network;
		setPreferredSize(dim);
		setLayout(new GridLayout(8,8));
		stateImages = new JLabel[64];
		for(int i = 0; i < 64;i++){
			stateImages[i] = new JLabel();
			this.add(stateImages[i]);
		}
		this.addMouseListener(this);
	}
	
	
	//Paints the basic chess grid
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(y % 2 == 0){
					if(x % 2 == 0){
						g.setColor(Color.WHITE);
						g.fillRect(x * 48, y * 48, 48, 48);
					}
					else{
						g.setColor(Color.GRAY);
						g.fillRect(x * 48, y * 48, 48, 48);
					}
				}
				else{
					if(x % 2 == 0){
						g.setColor(Color.GRAY);
						g.fillRect(x * 48, y * 48, 48, 48);
					}
					else{	
						g.setColor(Color.WHITE);
						g.fillRect(x * 48, y * 48, 48, 48);
					}
				}
			}
		}
	}
	
	public void update(){
		for(int i = 0; i < 64; i++){
			stateImages[i].setIcon(null);
		}
		stateImages[0].setIcon(null);
		ArrayList<Piece> pieces = GameState.getState();
		for(Piece piece: pieces){
			switch(piece.getType()){
			case 0:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load()); 
				break;
			case 1:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load());
				break;
			case 2:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load());
				break;
			case 3:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load());
				break;
			case 4:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load());
				break;
			case 5:
				stateImages[(8*piece.getY()) + piece.getX()].setIcon(piece.load());
				break;
			default:
				System.out.println("Problem has occurred");
				break;
			}
		}
	}
	
	
	public void doMove(int x_i, int y_i, int x, int y){
		System.out.println("Moving piece from x : " + x_i + " to " + x +" , and y : " + y_i + " to " + y);
		Piece toMove = null;
		for(Piece o_piece: GameState.pieces){
			if(o_piece.getX() == x_i && o_piece.getY() == y_i){
				toMove = o_piece;
				break;
			}
		}
		if(toMove == null){
			throw new NullPointerException("Piece to move was not found");
		}
		for(Piece o_piece: GameState.pieces){
			if(o_piece.getX() == x && o_piece.getY() == y){
				GameState.pieces.remove(o_piece);
				break;
			}
		}

		toMove.setXY(x, y);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(GameState.isMyTurn()){
			if(!targeted){
				int x = (int) (e.getX() / (dim.getWidth()/8));
				int y = (int) (e.getY() / (dim.getHeight()/8));
				//System.out.println(x + " " + y);
				clicked_piece = null;
				for(Piece piece: GameState.getState()){
					if(piece.getX() == x && piece.getY() == y){
						if(piece.isWhite() != GameState.getTeamColor())
							return;
						clicked_piece = piece;
						break;
					}
				}
				if(clicked_piece == null){
					System.out.println("empty");
					return;
				}
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				
				try {
					setCursor(toolkit.createCustomCursor(clicked_piece.getImage(), new Point(x,y), "img"));
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IndexOutOfBoundsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				targeted = true;
			}
			else{
				int x = (int) (e.getX() / (dim.getWidth()/8));
				int y = (int) (e.getY() / (dim.getHeight()/8));
				setCursor(Cursor.getDefaultCursor());
				if(ActionChecker.check(clicked_piece, x, y, GameState.getState())){
					int x_i = clicked_piece.getX();
					int y_i = clicked_piece.getY();
					doMove(x_i,y_i,x,y);
					
					try {
						network.sendBoardState(x_i,y_i,x,y);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				targeted = false;
				update();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
