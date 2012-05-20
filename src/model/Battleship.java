package model;

/**
 * battleship
 * 
 * @author Marshall
 */
public class Battleship extends Ship {
    public final static int SIZE = 4, INDEX = 3;

    /**
     * creates the battleship object
     */
    public Battleship() {
        this.index = INDEX;
        setSize(SIZE);
        setName("Battleship");
        this.health = this.size;
    }
}