/**
 * abstract ship class
 * @author Marshall
 */
public abstract class Ship implements Ships {

    protected int size = 0;
    protected int index = 0;

    protected String name;

    protected int health;

    private boolean[] hits = new boolean[size];

    private int x = 0;

    private int y = 0;

    /** 
     * the posible values for the ships rotated state
     */
    public enum Rotation { HORIZONTAL, VERTICAL };

    private Rotation rotation;

    /**
     * gets the name
     * @return String
     */
    public String getName() {
        String newName = new String(this.name);
        return newName;
    }

    /**
     * gets the ships size
     * @return int
     */
    public int getSize() {
        return size;
    }

    /**
     * decreases the ships health by 1
     */
    public void hit() {
        this.health--;
    }

    /**
     * gets the ships health
     * @return int
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * sets the ships position
     * @param newX int
     * @param newY int
     */
    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * sets the ships rotation
     * @param newRotation Rotation
     */
    public void setRotation(Rotation newRotation) {
        this.rotation = newRotation;
    }

    /**
     * gets the ships position
     * @return int[]
     */
    public int[] getPosition() {
        int[] pos = {this.x, this.y};
        return pos;
    }

    /**
     * gets the ships rotation
     * @return Rotation
     */
    public Rotation getRotation() {
        return this.rotation;
    }

    /**
     * sets the ships name
     * @param newName String
     */
    protected void setName(String newName) {
        this.name = newName;
    }

    /**
     * sets the ships size
     * @param newSize int
     */
    protected void setSize(int newSize) {
        this.size = newSize;
        this.health = size;
    }

    /**
     * gets the ships index
     * @return int
     */
    public int getIndex() {
        return this.index;
    }

   
}