package view;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Board;
import model.Fleet;
import model.NoCellSelected;
import model.Ship;
import model.ShotLocation;
import controller.AI;
import controller.ViewController;



/**
 *
 * @author Marshall
 */
public class GameView extends JPanel {
    public final static int X_LOC = 0, Y_LOC = 0, A_X = 321, A_Y = 63,
    L_X = 30, WIDTH = 600, HEIGHT = 400, F_Y = 320, F_H = 50;

    private AI ai = new AI();
    private Fleet fleet;
    private Fleet fleet2;

    private Arena arena;
    private Arena arena2;


    private Board board;
    private Board board2;

    private Arrows arrows;
    private Fire fire;

    private Ship ship;
    

    private String name;
    
    private ViewController controller;
    


    /** 
     * creates the main window for the battleship game and runs logic
     */
    public GameView(ViewController controller) {
    	this.controller = controller;
    	setSize(WIDTH, HEIGHT);
    	setLayout(null);
    	
    	fire = new Fire(this);
        fire.setBounds(A_X, F_Y, Arena.ARENA_SIZE * Arena.CELL, F_H);
        
        add(fire);
        
        arrows = new Arrows(this);
        arrows.setBounds(A_X, A_Y, Arena.ARENA_SIZE * Arena.CELL,
                Arena.ARENA_SIZE * Arena.CELL);
        
        add(arrows);
        
        resetView();
   
/*
        ai.placeShips(fleet);
        ai.placeShips(fleet2);
        arena2 = new Arena(fleet2, false);
        arena.setPosition(L_X, A_Y);
        arena2.setPosition(A_X, A_Y);
        add(arena);
        add(arena2);
        arena2.setVisible(false);
*/
    }
    
    public void resetView() {
    	fleet = new Fleet();
    	ai.placeShips(fleet);
    	arena = new Arena(fleet, true);
    	fire.setVisible(false);
    	arrows.setVisible(true);
    	arrows.disable();
    	arrows.setArena(arena);
    	if(arena != null) {
    		this.remove(arena);
    	}
    	arena.setPosition(L_X, A_Y);
    	arena.disableButtons();
    	add(arena);
    	repaint();
    }
    
    public void placeShipsView() {
    	arena.enableButtons();
    	arrows.enable();
    	repaint();
    }
    
    public void waitingForShipsView() {
    	arrows.disable();
    	arena.disableButtons();
    	repaint();
    }
    
    public void playView(Fleet opponent) {
    	arena.commitShips(fleet);
    	fleet2 = opponent;
    	arena2 = new Arena(fleet2, false);
    	arena2.setPosition(A_X, A_Y);
    	arena2.setVisible(true);
    	add(arena2);
    	arrows.setVisible(false);
    	fire.setVisible(true);
    	fire.setEnabled(false);
    	repaint();
    }
    
    public void chooseShotView() {
    	fire.setEnabled(true);
    }
    
    public void waitingForShotView() {
    	fire.setEnabled(false);
    }

    /**
     * gets the currently selected cell and fires on it,
     * and then allows the ai to fire as well
     */
    public void fireCallback() {
    	ShotLocation shot;
        try {
            shot = arena2.getSelectedCell();
            if (shot.x == -1) {
                throw new NoCellSelected();
            } else {
            	controller.fireCallback(shot);
            	displayShot(shot, board2, arena2, fleet2);      
            }
        }
        catch (NoCellSelected e) {
            System.out.println("No cell selected");
        }   
    }
    
    public void recieveShot(ShotLocation shot) {
    	displayShot(shot, board, arena, fleet);
    }
    
    
    /**
     * starts the game after the player has positioned their ships
     */
    public void startGameCallback() {
        if (ai.isFleetPositionValid(fleet)) {
        	controller.playCallback(fleet);
        }
    }
    
    private void displayShot(ShotLocation shot, Board board, Arena arena, Fleet fleet) {
    	ship = fleet.isHit(shot);
    	if (ship != null) {
            board.setHit(shot);
            arena.setHit(shot);
            if (ship.getHealth() == 0) {
                arena.showShip(ship.getIndex());
            }
    	} else {
    		board.setMiss(shot);
    		arena.setMiss(shot);
    	}         
    }


    /**
     * paints elements onto the canvas
     *
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw an image
        BufferedImage image = loadImage("background.gif");
        g2.drawImage(image, 0, 0, null);
    }

    /**
     * loads the image as a buffered image
     * @param fileName String
     * @return BufferedImage
     */
    private BufferedImage loadImage(String fileName) {
        BufferedImage im = null;
        fileName = "../img/" + fileName;
        try {
            im = ImageIO.read(getClass().getResource(fileName));
        } catch (IOException e) {
            System.out.println("Error loading " + fileName);
        }
        return im;
    }
}
