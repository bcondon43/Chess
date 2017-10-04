package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import back.GameState;

import front.Board;

public class Client implements Runnable,Network {
	private DataOutputStream out;
	private DataInputStream in;
	private Thread thread;
	private boolean isRunning;
	private Board board;
	private Socket server;
	
	public Client() throws UnknownHostException, IOException{
		String ip = JOptionPane.showInputDialog("Enter ip of host...");
		int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number..."));
		server = new Socket(ip,port);
		
		out = new DataOutputStream(server.getOutputStream());
		in = new DataInputStream(server.getInputStream());
		
		GameState.init(true, true);
		JFrame frame = new JFrame("Client");
		
		board = new Board(this);
		frame.add(board);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		board.update();
	}
	
	public static void main(String[] args) throws IOException{
		Client client = new Client();
		
		client.start();
	}
	
	private void start(){
		thread = new Thread(this,"Client");
		isRunning = true;
		thread.start();
	}


	@Override
	public void run() {
		while(isRunning){
			System.out.println(GameState.isMyTurn());
			try {
				if(!GameState.isMyTurn() && in.available() > 0){
					try {
						System.out.println("READING");
						int[] params = new int[4];
						for(int i = 0; i < 4; i++){
							params[i] = in.readInt();
						}
						board.doMove(params[0],params[1],params[2],params[3]);
						board.update();
						GameState.endTurn();
						System.out.println("My turn: " + GameState.isMyTurn());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendBoardState(int x_i,int y_i,int x,int y) throws IOException {
		System.out.println(GameState.isMyTurn());
		out.writeInt(x_i);
		out.writeInt(y_i);
		out.writeInt(x);
		out.writeInt(y);
		out.flush();
		GameState.endTurn();
	}
	
}
