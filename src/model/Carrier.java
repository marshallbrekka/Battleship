package model;

/**
 * carrier
 * 
 * @author Marshall
 */
public class Carrier extends Ship {
    public final static int SIZE = 5, INDEX = 4;

    /**
     * creates the carrier object
     */
    public Carrier() {
        this.index = INDEX;
        setSize(SIZE);
        setName("Aircraft Carrier");
        this.health = this.size;
    }
}