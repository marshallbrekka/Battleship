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
	private Fleet ours, theirs;
	private boolean playAgainUs = false, playAgainThem = false;
	private ViewController view;
	
	private Server server = null;
	
	public int port = 5558;
	
	
	
	
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
		serverRunning = false;
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
		clientMode = false;
	}
	
	public void setFleet(Fleet fleet) {
		ours = fleet;
		startGameIfHaveBothFleets();
		channel.sendFleet(fleet);
	}
	
	public Fleet getEnemyFleet() {
		return theirs;
	}
	
	public void startGameIfHaveBothFleets() {
		System.out.println("start game if both fleets");
		if(ours != null && theirs != null) {
			System.out.println("has both");
			view.bothFleetsRecievedView();
			if(clientMode) {
				view.waitingForShotView();
			} else {
				view.chooseShotView();
			}
		}
	}
	
	
	public void newGame() {
		ours = theirs = null;
		if(playAgainUs && playAgainThem) {
			view.startupView();
			playAgainUs = playAgainThem = false;
		}
		System.out.println("COntroller.newGame");
		gameStarted = true;
		view.positionFleetView();
	}
	
	
	
	public void endGame() {
		clientMode = false;
		if(serverRunning) {
			stopServer();
		} 
		if(gameStarted) {
			channel.exitGame();
			gameStarted = false;
			view.startupView();
		}
		stopChannel();
	}
	
	public void fireShot(ShotLocation shot) {
		channel.sendShot(shot);
		if(theirs.getHealth() == 0) {
			playAgainPrompt(true);
		}
	}
	
	public void recieveFleetCallback(Fleet fleet) {
		System.out.println("controller.recieveFlletCallback");
		theirs = fleet;
		startGameIfHaveBothFleets();
	}
	
	public void recieveShotCallback(ShotLocation shot) {
		System.out.println("recieved shot");
		view.recieveShot(shot);
		if(ours.getHealth() == 0) {
			playAgainPrompt(false);
		}
		
	}
	
	private void playAgainPrompt(boolean won) {
		String status = won ? "WON!" : "LOST.";
		boolean result = view.confirmDialog("YOU " + status + " Play again?");
		
		if(result) {
			channel.playAgain();
			playAgainUs = true;
			view.startupView();
			playAgainIfBothWantTo();
		} else {
			channel.exitGame();
			endGame();
		}
	}
	
	private void playAgainIfBothWantTo() {
		if(playAgainUs && playAgainThem) {
			newGame();
		}
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
		if(gameStarted) {
			view.showErrorAlert("The other player left the game");
			endGame();
		}
	}
	
	public void playAgainCallback() {
		playAgainThem = true;
		playAgainIfBothWantTo();
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
