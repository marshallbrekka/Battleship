
import java.awt.*;
import javax.swing.*;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.*;
import java.io.*;



/**
 *
 * @author Marshall
 */
public class BattleshipGame extends JPanel {
    public final static int X_LOC = 0, Y_LOC = 0, A_X = 321, A_Y = 63,
    L_X = 30, WIDTH = 600, HEIGHT = 400, F_Y = 320, F_H = 50;

    private AI ai = new AI();
    private Fleet fleet = new Fleet();
    private Fleet fleet2 = new Fleet();

    private Arena arena;
    private Arena arena2;
    private Arena arena3;

    private Board board = new Board();
    private Board board2 = new Board();

    private Arrows a;
    private Fire f;

    private int[] shot;
    private Ship ship;
    private JFrame frame;

    private String name;
    


    /** 
     * creates the main window for the battleship game and runs logic
     */
    public BattleshipGame() {

        ai.placeShips(fleet);
        ai.placeShips(fleet2);

        arena = new Arena(fleet, true);
        arena2 = new Arena(fleet2, false);

        a = new Arrows(arena, this);
        f = new Fire(this);
        f.setBounds(A_X, F_Y, Arena.ARENA_SIZE * Arena.CELL, F_H);
        f.setVisible(false);

        arena.setPosition(L_X, A_Y);
        arena2.setPosition(A_X, A_Y);

        // Setup JFrame
        frame = new JFrame();
        frame.setName("Battleship");
        frame.setTitle("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        a.setBounds(A_X, A_Y, Arena.ARENA_SIZE * Arena.CELL,
            Arena.ARENA_SIZE * Arena.CELL);

        frame.add(arena);
        frame.add(arena2);
        arena2.setVisible(false);


        frame.add(a);
        frame.add(f);

        frame.add(this);



        Insets size = frame.getInsets();
        frame.setSize(WIDTH, HEIGHT + size.top);
        // Set the frame size and make it visible

        frame.setResizable(false);
        frame.setVisible(true);


    }

    /**
     * gets the currently selected cell and fires on it,
     * and then allows the ai to fire as well
     */
    public void fire() {
        try {
            shot = arena2.getSelectedCell();
            if (shot[0] == -1) {
                throw new NoCellSelected();
            }
            String message;
            ship = fleet2.isHit(shot[0], shot[1]);
            if (ship != null) {
                board2.setHit(shot[0], shot[1]);
                arena2.setHit(shot[0], shot[1]);
                if (ship.getHealth() == 0) {
                    arena2.showShip(ship.getIndex());
                    if (fleet2.getHealth() == 0) {
                        message = name + " Won!";
                        JOptionPane.showMessageDialog(null, message);
                    }
                }
            } else {
                board2.setMiss(shot[0], shot[1]);
                arena2.setMiss(shot[0], shot[1]);
            }

            shot = ai.takeShot(board);
            ship = fleet.isHit(shot[0], shot[1]);
            if (ship != null) {
                board.setHit(shot[0], shot[1]);
                arena.setHit(shot[0], shot[1]);
                if (ship.getHealth() == 0) {

                    arena.showShip(ship.getIndex());
                    if (fleet.getHealth() == 0) {
                        message = name + " Lost :(";
                        JOptionPane.showMessageDialog(null, message);
                    }
                }
            } else {
                board.setMiss(shot[0], shot[1]);
                arena.setMiss(shot[0], shot[1]);
            }
        }
        catch (NoCellSelected e) {
            System.out.println("No cell selected");
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
        BufferedImage image = loadImage("img/background.gif");
        g2.drawImage(image, 0, 0, null);


    }

    /**
     * loads the image as a buffered image
     * @param fileName String
     * @return BufferedImage
     */
    private BufferedImage loadImage(String fileName) {
        BufferedImage im = null;
        try {
            im = ImageIO.read(getClass().getResource(fileName));
        } catch (IOException e) {
            System.out.println("Error loading " + fileName);
        }
        return im;
    }

    /**
     * starts the game after the player has positioned their ships
     */
    public void startGame() {
        if (ai.isFleetPositionValid(fleet)) {

            arena.commitShips(fleet);
            arena2.setVisible(true);
            a.setVisible(false);
            f.setVisible(true);

            arena.repaint();

            frame.repaint();
        }
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

    /**
     * the main method of the battleshipgame program
     * @param args String[]
     */
    public static void main(String[] args) { 
        BattleshipGame window = new BattleshipGame();
        window.loadName();       
    }

}
