/**
 * defines methods for the Ships
 * @author Marshall
 */
public interface Ships {
    /**
     * gets the ship size
     * @return int
     */
    public int getSize();

    /**
     * gets the ship name
     * @return String
     */
    public String getName();

    /**
     * tell the ship it is hit
     */
    public void hit();
}