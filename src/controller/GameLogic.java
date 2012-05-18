package controller;

import model.Fleet;
import model.ShotLocation;

public class GameLogic {
	private Controller main;
	private Fleet ours, theirs;
	
	public GameLogic(Controller main) {
		this.main = main;
		ours = new Fleet();
	}
	
	public void setTheirFleet(Fleet fleet) {
		theirs = fleet;
	}
	
	public Fleet getOurFleet() {
		return ours;
	}
	
	public Fleet getTheirFleet() {
		return theirs;
	}
	
	public void startGame() {
		
	}
	
	public void stopGame() {
		
	}
	
	public void recieveShot(ShotLocation shot) {
		
	}
}
