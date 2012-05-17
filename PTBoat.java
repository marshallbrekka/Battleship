/**
 * PTBoat
 * @author Marshall
 */
public class PTBoat extends Ship {
    public final static int SIZE = 2, INDEX = 0;

    /**
     * creates the ptboat object
     */
    public PTBoat() {
        this.index = INDEX;
        setSize(SIZE);
        setName("PT Boat");
        this.health = this.size;
    }
}