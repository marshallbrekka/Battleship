package view;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.ViewController;

public class Window extends JFrame {
	private ViewController controller;
	private JMenuItem server, client, exit;
	private JMenu menu;
	private JMenuBar menuBar;

	JLayeredPane container;
	JPanel overlay;
	
	public final static int WIDTH = 600, HEIGHT = 400;
	
	public Window(ViewController controller) {
		this.controller = controller;
		setName("Battleship");
        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        container = new JLayeredPane();
        overlay = new JPanel();
        overlay.setBounds(0, 0, WIDTH, HEIGHT);
        overlay.setBackground(new Color(255, 255, 255, 200)); //r,g,b,alpha
        container.add(overlay,1);
        getContentPane().add(container);   
	}
	
	public void resize() {
		Insets size = getInsets();
        setSize(WIDTH, HEIGHT + size.top + menuBar.getHeight());
	}
	
	public void setExitMode(boolean mode) {
		exit.setEnabled(mode);
		client.setEnabled(!mode);
		server.setEnabled(!mode);
	}
	
	public void showOverlay() {
		overlay.setVisible(true);
		overlay.repaint();
	}
	
	public void hideOverlay() {
		overlay.setVisible(false);
		overlay.repaint();
	}
	
	public void addGameView(JPanel view) {
		container.add(view, 2);
	}
	
	private void createMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuBar.add(menu);
		server = new JMenuItem("Create Game");
		server.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.createServerCallback();
			}
		});
		menu.add(server);
		
		client = new JMenuItem("Join Game");
		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.createClientCallback();
			}
		});
		menu.add(client);
		
		exit = new JMenuItem("Exit Game");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.exitGameCallback();
			}
		});
		menu.add(exit);
		exit.setEnabled(false);

		setJMenuBar(menuBar);
	}

	
	
}
