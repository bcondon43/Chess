package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import back.GameState;

import front.Board;

public class Server implements Runnable,Network{
	private boolean isRunning;
	private Socket client;
	private Thread thread;
	private DataOutputStream out;
	private DataInputStream in;
	public Board board;
	
	public Server() throws IOException, ClassNotFoundException{
		int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number..."));
		
		ServerSocket server = new ServerSocket(port);
		client = server.accept();
		
		out = new DataOutputStream(client.getOutputStream());
		in = new DataInputStream(client.getInputStream());
		
		
		//ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		GameState.init(false, false);
		JFrame frame = new JFrame("Server");
		
		board = new Board(this);
		frame.add(board);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		board.update();
		
		server.close();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException{
		Server serv = new Server();
		serv.start();
	}
	
	public void start(){
		thread = new Thread(this,"Server");
		isRunning = true;
		thread.start();
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(isRunning){
			System.out.println(GameState.isMyTurn());
			try {
				if(!GameState.isMyTurn() && in.available() > 0){
						try {
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
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendBoardState(int x_i,int y_i,int x,int y) throws IOException {
		System.out.println("SENDING");
		out.writeInt(x_i);
		out.writeInt(y_i);
		out.writeInt(x);
		out.writeInt(y);
		System.out.println("start size " +out.size());
		out.flush();
		GameState.endTurn();
	}

	
}

