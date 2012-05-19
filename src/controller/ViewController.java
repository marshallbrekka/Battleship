package controller;

import javax.swing.JOptionPane;

import model.Fleet;
import model.ShotLocation;
import view.GameView;
import view.Window;

public class ViewController {
	private Window window;
	private GameView gameView;
	private Controller main;
	
	public ViewController(Controller main) {
		this.main = main;
		window = new Window(this);
		gameView = new GameView(this);
		
		window.addGameView(gameView);
        window.setVisible(true);
        window.resize();
        window.repaint();
	}
	
	
	/**
	 * blank view, shows overlay, displays randomly setup arena, and move arrows
	 */
	public void startupView() {
		gameView.resetView();
		window.setExitMode(false);
		window.showOverlay();
	}
	
	public void startupMenu() {
		window.setExitMode(false);
	}
	
	/**
	 * same as overlayView, except menu items are changed
	 */
	public void waitingForPlayerView() {
		window.setExitMode(true);
	}
	
	/**
	 * same same as startupView
	 */
	public void waitingForFleetView() {
		gameView.waitingForShipsView();
		window.showOverlay();
	}
	
	/**
	 * overlay is not present, arrows are enabled, ships are enabled
	 */
	public void positionFleetView() {
		gameView.placeShipsView();
		window.hideOverlay();
	}
	
	/** 
	 * both arenas are visible, fire button is visible but disabled
	 */
	public void bothFleetsRecievedView() {
		gameView.playView(main.getEnemyFleet());
	}
	
	/**
	 * fire button is enabled
	 */
	public void chooseShotView() {
		gameView.chooseShotView();
	}
	
	/**
	 * fire button is disabled
	 */
	public void waitingForShotView() {
		gameView.waitingForShotView();
	}
	
	public void showErrorAlert(String msg) {
		JOptionPane.showMessageDialog(window, msg);
	}
	
	public boolean confirmDialog(String msg) {
		int response = JOptionPane.showConfirmDialog(window, msg, "", JOptionPane.YES_NO_OPTION);
		return response == JOptionPane.YES_OPTION;
	}
	
	
	/////////// button callbacks ////////////
	
	public void createServerCallback() {
		main.startServer();
		window.setExitMode(true);
	}
	
	public void createClientCallback() {
		String host = (String)JOptionPane.showInputDialog(
                window,
                "Enter the hostname of the player you want to play against",
                "Join Game",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "localhost");		
		if (host != null && host.length() != 0) {
			main.startClient(host);
			window.setExitMode(true);
		} 
	}
	
	public void exitGameCallback() {
		main.endGame();
		startupView();
	}
	
	public void playCallback(Fleet fleet) {
		waitingForFleetView();
		main.setFleet(fleet);
	}
	
	public void fireCallback(ShotLocation shot) {
		waitingForShotView();
	}
	
	
}
