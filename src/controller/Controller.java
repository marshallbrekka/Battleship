package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import view.GameView;
import view.Window;

import model.Fleet;
import model.ShotLocation;
import networking.Channel;
import networking.Client;
import networking.Server;

public class Controller {
	private Channel channel;
	private boolean gameStarted = false;
	private boolean serverRunning = false, clientMode = false;
	private String name = "Marshall";
	private GameLogic gameLogic;
	private Fleet ours, theirs;
	private ViewController view;
	
	private Server server = null;
	
	public int port = 5558;
	
	
	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		view = new ViewController(this);
		loadName();
	}
	
	public boolean setChannel(Channel channel) {
		System.out.println("channel was set");
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
			serverRunning = true;
		} catch (IOException e) {
			view.showErrorAlert("failed at starting server");
			view.startupMenu();
			e.printStackTrace();
		}
		
	}
	
	public void startServerError(String msg) {
		view.showErrorAlert(msg);
		view.startupMenu();
	}
	
	public void stopServer() {
		server.stop();
		server = null;
	}
	
	public void startClient(String host) {
		clientMode = true;
		new Thread(new Client(host, port, this)).start();
	}
	
	public void startClientError(String msg) {
		view.startupMenu();
		view.showErrorAlert(msg);
	}
	
	public void stopChannel() {
		channel.stop();
		channel = null;
	}
	
	public void setFleet(Fleet fleet) {
		ours = fleet;
		startGameIfHaveBothFleets();
	}
	
	public Fleet getEnemyFleet() {
		return theirs;
	}
	
	public void startGameIfHaveBothFleets() {
		if(ours != null && theirs != null) {
			if(clientMode) {
				view.waitingForShotView();
			} else {
				view.chooseShotView();
			}
		}
	}
	
	
	public void newGame() {
		System.out.println("COntroller.newGame");
		view.positionFleetView();
	}
	
	
	
	public void endGame() {
		if(serverRunning) {
			stopServer();
		} else {
			
		}
	}
	
	public void fireShot(ShotLocation shot) {
		//if(ours.)
	}
	
	public void recieveFleetCallback(Fleet fleet) {
		System.out.println("controller.recieveFlletCallback");
		theirs = fleet;
		startGameIfHaveBothFleets();
	}
	
	public void recieveShotCallback(ShotLocation shot) {
		gameLogic.recieveShot(shot);
		System.out.println("recieved shot");
	}
	
	public void joinGameCallback(String name) {
		boolean response = view.confirmDialog(name + " would like to play, accept game?");
		if(response) {
			channel.gameJoined();
			newGame();
		} else {
			channel.gameRejected();
			channel.exitGame();
			stopChannel();
			stopServer();
			startServer();
		}
	}
	
	public void exitGameCallback() {
		System.out.println("exit game");
	}
	
	public void playAgainCallback() {
		System.out.println("play again");
	}
	
	public void gameJoinedCallback() {
		newGame();
	}
	
	public void gameRejectedCallback() {
		stopChannel();
		view.showErrorAlert("The other player rejected your request");
		view.startupMenu();
	}
	
	/**
     * loads the players name from the text file
     */
    public void loadName() {
        try {
            File data = new File("name.txt");
            Scanner in = new Scanner(
                         new BufferedReader(
                         new FileReader(data)));
            name = in.nextLine();
            System.out.println(name);
            in.close();
        }
        catch (IOException e) {
            System.out.println(e);
            String newName;
            do {
                newName = JOptionPane.showInputDialog("Enter your  name:");
            } while(newName == null);
            name = newName;

            try {
                File data = new File("name.txt");
                PrintWriter out = new PrintWriter(
                              new BufferedWriter(
                              new FileWriter(data)));
                out.println(newName);
               
                out.println();
                out.close();
                System.out.println("Finished writing file");
            }
            catch (IOException p) {
                System.out.println("error writing file");
            }
        }
    }
}
