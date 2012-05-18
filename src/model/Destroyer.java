package model;
/**
 * destroyer
 * @author Marshall
 */
public class Destroyer extends Ship {
    public final static int SIZE = 3, INDEX = 1;

    /**
     * creates the destroyer object
     */
    public Destroyer() {
        this.index = INDEX;
        setSize(SIZE);
        setName("Destroyer");
    }
}