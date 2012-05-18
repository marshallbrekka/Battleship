package model;
/**
 * submarine
 * @author Marshall
 */
public class Submarine extends Ship {
    public final static int SIZE = 3, INDEX = 2;

    /**
     * creates the submarine object
     */
    public Submarine() {
        this.index = INDEX;
        setSize(SIZE);
        setName("Submarine");
        this.health = this.size;
    }
}