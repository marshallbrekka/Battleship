

/**
 *
 * @author Marshall
 */
public class NoCellSelected extends Exception {

    /**
     * no cell selected
     */
    public NoCellSelected() {
        super("No cell selected");
        System.out.println("Error");
    }


    /**
     * no cell selected with custome mesasage
     * @param msg the detail message.
     */
    public NoCellSelected(String msg) {
        super(msg);
    }
}
