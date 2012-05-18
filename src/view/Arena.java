package view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Fleet;
import model.Ship;


/**
 * the arena class, creates the cells and the ships
 * @author Marshall
 */
public class Arena extends JPanel {
    public final static int X_LOC = 0, Y_LOC = 0, ARENA_SIZE = 10,
    WIDTH = 250, HEIGHT = 250, RANGE = 9, CELL = 25, SELECTED = 5,
    ROTATED = 10, ROTATED_SELECTED = 15;

    

    private ImageIcon open = new ImageIcon(loadImage("open.png"));

    private ImageIcon selected = new ImageIcon(loadImage("selected.png"));
    private ImageIcon hit = new ImageIcon(loadImage("hit.png"));
    private ImageIcon miss = new ImageIcon(loadImage("miss.png"));
    private ImageIcon oceanImg = new ImageIcon(loadImage("ocean.png"));


    //private Image[] baseImg = new Image[10];
    
    private ImageIcon[] icon = new ImageIcon[ARENA_SIZE * 2];

    private JLabel ocean = new JLabel(oceanImg);

    //private JLabel[] boats = new JLabel[5];
    private JRadioButton[] boats = new JRadioButton[Fleet.FLEET_SIZE];
    

    private JRadioButton[][] grid = new JRadioButton[ARENA_SIZE][ARENA_SIZE];

    private JRadioButton hidden = new JRadioButton();

    private ButtonGroup group = new ButtonGroup();
    private ButtonGroup ships = new ButtonGroup();

    private Fleet fleet;

    /**
     * creates the arena
     * @param newFleet Fleet
     * @param positionShips boolean
     */
    public Arena(Fleet newFleet, boolean positionShips) {
        //super("Hello Frame Application");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        hidden.setBounds(WIDTH * ARENA_SIZE, 0, 0, 0);
        group.add(hidden);

        this.fleet = newFleet;

        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setBounds(X_LOC, Y_LOC, WIDTH, HEIGHT);

        ocean.setBounds(X_LOC, Y_LOC, WIDTH, HEIGHT);

        this.makeGrid(positionShips);
        this.makeShips(positionShips);
        this.positionShips(fleet);

    
        int z = -1;
        if (positionShips) {
            boats[0].setSelected(true);
            z = 0;
        }

        for (int i = 0; i < boats.length; i++) {
            add(boats[i], z);
        }
          
        add(ocean);
    }

    /**
     * loads an image as a bufferedimage
     * @param fileName String
     * @return BufferedImage
     */
    private BufferedImage loadImage(String fileName) {
    	fileName = "../img/" + fileName;
        BufferedImage im = null;
        try {
            im = ImageIO.read(getClass().getResource(fileName));
        } catch (IOException e) {
            System.out.println("Error loading " + fileName);
        }
        return im;
    }
    /**
     * loads  the ship icons
     * @param positionShips boolean true if the board is for positionion the
     * ships not fighting
     */
    private void makeShips(boolean positionShips) {
        String[] paths = {"ptboat.png", "destroyer.png",
            "submarine.png", "battleship.png", "carrier.png",
            "ptboat_s.png", "destroyer_s.png", "submarine_s.png",
            "battleship_s.png", "carrier_s.png", "ptboat_r.png",
            "destroyer_r.png", "submarine_r.png",
            "battleship_r.png", "carrier_r.png",
            "ptboat_s_r.png", "destroyer_s_r.png",
            "submarine_s_r.png", "battleship_s_r.png",
            "carrier_s_r.png"};


        //create ship images
        for (int i = 0; i < icon.length; i++) {
            icon[i] = new ImageIcon(loadImage(paths[i]));
        }

        
        // make each ship and apply the icon and set if visible and add to group
        for (int p = 0; p < boats.length; p++) {
            boats[p] = new JRadioButton(icon[p]);
            boats[p].setSelectedIcon(icon[p + SELECTED]);
            boats[p].setVisible(positionShips);
            ships.add(boats[p]);
        }

    }

    /**
     * makes the grid
     * @param positionShips boolean true if the board is for positionion the
     * ships not fighting
     */
    private void makeGrid(boolean positionShips) {
        // draw hit points
        for (int i = 0; i < ARENA_SIZE; i++) {
            for (int  y = 0; y < ARENA_SIZE; y++) {
                grid[i][y] = new JRadioButton(open);
                grid[i][y].setBounds(i * CELL, y * CELL, CELL, CELL);
                grid[i][y].setSelectedIcon(selected);
                grid[i][y].setDisabledIcon(open);
                grid[i][y].setEnabled(!positionShips);
                group.add(grid[i][y]);
                add(grid[i][y]);
            }
        }

    }

    /**
     * positions the ships based on their positions in the fleet object
     * @param newFleet Fleet
     */
    private void positionShips(Fleet newFleet) {
        Ship ship;
        Ship.Rotation rot;
        int width;
        int height;
        int[] pos;
        
        // place fleet
        for (int s = 0; s < Fleet.FLEET_SIZE; s++) {
            ship = newFleet.getShip(s);
            pos = ship.getPosition();
            rot = ship.getRotation();
            if (rot == Ship.Rotation.HORIZONTAL) {
                boats[s].setIcon(icon[s + ROTATED]);
                boats[s].setSelectedIcon(icon[s + ROTATED_SELECTED]);
                width = CELL * ship.getSize();
                height = CELL;
            } else {
                width = CELL;
                height = CELL * ship.getSize();
            }
            
            boats[s].setBounds(pos[0] * CELL, pos[1] * CELL, width, height);
            

        }

        
    }

    /**
     * commits the ships to their current positions
     * @param newFleet Fleet
     */
    public void commitShips(Fleet newFleet) {
        this.removeAll();
        this.fleet = newFleet;
        this.makeGrid(true);
        this.makeShips(true);
        this.positionShips(newFleet);

        int i = 0;
        commitShips(i);
        
    }

    /**
     * commits the ships to their current positions
     * @param i int the counter variable set to 0 by defualt
     */
    private void commitShips(int i) {

        int z = -1;
        if (i < boats.length) {
            add(boats[i], z);
            commitShips(++i);
        } else {
            add(ocean);
        }


    }

    /**
     * sets the cell to a miss
     * @param x int
     * @param y int
     */
    public void setMiss(int x, int y) {
        grid[x][y].setDisabledIcon(miss);
        grid[x][y].setSelectedIcon(miss);
        grid[x][y].setEnabled(false);
        hidden.setSelected(true);
        repaint();
    }

    /**
     * sets the cell to a hit
     * @param x int
     * @param y int
     */
    public void setHit(int x, int y) {
        grid[x][y].setDisabledIcon(hit);
        grid[x][y].setSelectedIcon(hit);
        grid[x][y].setEnabled(false);
        hidden.setSelected(true);
        repaint();
    }

    /**
     * shows the ship at the index specified
     * @param index int
     */
    public void showShip(int index) {
        boats[index].setVisible(true);
        repaint();
    }

    /**
     * gets the selected cell
     * @return int[]
     */
    public int[] getSelectedCell() {
        int[] pos = {-1, -1};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isSelected()) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        
        return pos;
    }

    /**
     * gets the selected ship
     * @return int
     */
    private int getSelectedShip() {
        for (int i = 0; i < boats.length; i++) {
            if (boats[i].isSelected()) {
                return i;
            }
        }

        return -1;
    }

    /**
     * moves the selected ship by the ammount specified
     *
     * @param x int
     * @param y int
     */
    private void moveShip(int x, int y) {
        int ship = getSelectedShip();
        int height, width;
        Ship shipObj = fleet.getShip(ship);
        int[] coordinates = shipObj.getPosition();
       
        if (this.isInRange(shipObj, coordinates[0] + x, coordinates[1] + y)) {
            shipObj.setPosition(coordinates[0] + x, coordinates[1] + y);
            width = (int) boats[ship].getBounds().getWidth();
            height = (int) boats[ship].getBounds().getHeight();
            boats[ship].setBounds((coordinates[0] + x)
                * CELL, (coordinates[1] + y) * CELL, width, height);
        }
  
        repaint();

        
    }

    /**
     * move ship left
     */
    public void moveShipLeft() {
        moveShip(-1, 0);
    }

    /**
     * move ship right
     */
    public void moveShipRight() {
        moveShip(1, 0);
    }

    /**
     * moves the ship up
     */
    public void moveShipUp() {
        moveShip(0, -1);
    }

    /**
     * moves the selected ship down
     */
    public void moveShipDown() {
        moveShip(0, 1);
    }

    /**
     * rotates the ship, depending on what direction it currently is
     */
    public void rotateShip() {
        int ship = getSelectedShip();
        int width, height;
        Ship shipObj = fleet.getShip(ship);
        int[] offset = {0, 0};
        int pos[] = {0, 0};
        if (shipObj.getRotation() == Ship.Rotation.HORIZONTAL) {
            shipObj.setRotation(Ship.Rotation.VERTICAL);
            width = CELL;
            height = CELL * shipObj.getSize();
            offset[1] = -1;
            boats[ship].setIcon(icon[ship]);
            boats[ship].setSelectedIcon(icon[ship + SELECTED]);

            
        } else {
            shipObj.setRotation(Ship.Rotation.HORIZONTAL);
            width = CELL * shipObj.getSize();
            height = CELL;
            boats[ship].setIcon(icon[ship + ROTATED]);
            boats[ship].setSelectedIcon(icon[ship + ROTATED_SELECTED]);
            offset[0] = -1;
            
        }

        boolean ok = true;
        while (ok) {
            pos = shipObj.getPosition();
            if (this.isInRange(shipObj, pos[0], pos[1])) {
                ok = false;
            } else {
                shipObj.setPosition(pos[0] + offset[0], pos[1] + offset[1]);
            }
        }
       
        boats[ship].setBounds((pos[0] * CELL), (pos[1] * CELL), width, height);
        
        repaint();
    }

    /**
     * checks if the ship is in the board range
     *
     * @param ship Ship
     * @param x int
     * @param y int
     * @return boolean
     */
    private boolean isInRange(Ship ship, int x, int y) {
        int xRange = RANGE;
        int yRange = RANGE;
        int rotation;

        if (ship.getRotation() == Ship.Rotation.HORIZONTAL) {
            rotation = 0;
        } else rotation = 1;

        int size = ship.getSize();

        if (rotation == 0) {
            xRange -= size + -1;
        } else {
            yRange -= size + -1;
        }

        if (x <= xRange && x >= 0 && y <= yRange && y >= 0) {
            return true;
        } else return false;
 
    }

    /**
     * sets the position of the arena
     *
     * @param x int
     * @param y int
     */
    public void setPosition(int x, int y) {
        int width = getWidth();
        int height = getHeight();
        setBounds(x, y, width, height);
    }

   
}