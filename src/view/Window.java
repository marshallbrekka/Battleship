package view;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.Controller;

public class Window extends JFrame {
	private Controller main;
	private JMenuItem server, client, exit;
	private JMenu menu;
	private boolean clientRunning = false, serverRunning = false;
	
	public final static int X_LOC = 0, Y_LOC = 0, A_X = 321, A_Y = 63,
    L_X = 30, WIDTH = 600, HEIGHT = 400, F_Y = 320, F_H = 50;
	
	
	public static void main(String[] args) {
		new Window(null);
	}
	
	private Window(Controller main) {
		this.main = main;
		setName("Battleship");
        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        
        Insets size = getInsets();
        setSize(WIDTH, HEIGHT + size.top);
        // Set the frame size and make it visible

        setResizable(false);
        setVisible(true);
        
	}
	
	
	private void createMenu() {
		
		JMenuBar menuBar = new JMenuBar();

		menu = new JMenu("Game");
		menuBar.add(menu);

		
		server = new JMenuItem("Create Game");
		server.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				menu.remove(server);
				menu.remove(client);
				menu.add(exit);
				serverRunning = true;
				//main.startServer();
			}
		});
		menu.add(server);
		
		
		client = new JMenuItem("Join Game");
		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				String host = (String)JOptionPane.showInputDialog(
	                    Window.this,
	                    "Enter the hostname of the player you want to play against",
	                    "Join Game",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "localhost");

				
				if (host != null && host.length() != 0) {
					
					menu.remove(server);
					menu.remove(client);
					menu.add(exit);
					clientRunning = true;
					System.out.println(host);
					//main.startClient(host);
				} 
			}
		});
		menu.add(client);
		
		exit = new JMenuItem("Exit Game");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				internalExitGame();
			}
		});

		
		setJMenuBar(menuBar);
	}
	
	public void exitGame() {
		menu.remove(exit);
		menu.add(server);
		menu.add(client);
		//main.startServer();
		serverRunning = false;
		clientRunning = false;
	}
	
	private void internalExitGame() {
		
		if(serverRunning) {
			//main.stopServer();
		}
		//main.endGame();
		exitGame();
	}
	
	
}
