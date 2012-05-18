package controller;

import java.io.IOException;

import model.Fleet;
import model.ShotLocation;
import networking.Channel;
import networking.Client;
import networking.Server;

public class Controller {
	private Channel channel;
	private boolean gameStarted = false;
	private boolean clientMode = false;
	private String name = "Marshall";
	private GameLogic gameLogic;
	
	private Server server = null;
	
	public int port = 5558;
	
	
	
	public boolean setChannel(Channel channel) {
		if(this.channel == null) {
			this.channel = channel;
			if(clientMode) {
				channel.joinGame(name);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void startServer() {
		
		
		try {
			server = new Server(port, this);
			new Thread(server).start();
			clientMode = false;
		} catch (IOException e) {
			System.out.println("failed at starting server");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(server != null) {
			// switch out view
			System.out.println("switch out view");
		}
	}
	
	public void startServerError(String msg) {
		// change view
		// show alert with this msg
		System.out.println(msg);
	}
	
	public void stopServer() {
		server.stop();
		server = null;
		// switch out view;
	}
	
	public void startClient(String host) {
		
		host = "localhost";
		clientMode = true;
		new Client(host, port, this);
		
		
	}
	
	public void startClientError(String msg) {
		// change view
		// show alert with the msg
		System.out.println(msg);
	}
	
	
	public void newGame() {
		gameLogic = new GameLogic(this);
		
		
	}
	
	public void startGame() {
		
	}
	
	public void endGame() {
		
	}
	
	public void recieveFleet(Fleet fleet) {
		gameLogic.setTheirFleet(fleet);
		System.out.println("recieved fleet");
	}
	
	public void recieveShot(ShotLocation shot) {
		gameLogic.recieveShot(shot);
		System.out.println("recieved shot");
	}
	
	public void joinGame(String name) {
		// alert the user of the name, let them say yes or no
		System.out.println("join game " + name);
	}
	
	public void exitGame() {
		System.out.println("exit game");
	}
	
	public void playAgain() {
		System.out.println("play again");
	}
	
	public void gameJoined() {
		System.out.println("game joined");
		// open view for fleet positioning
	}
	
	public void gameRejected() {
		System.out.println("game rejected");
	}
}
