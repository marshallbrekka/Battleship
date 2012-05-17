
/**
 *
 * @author Marshall
 */
public class Board {

    private Boolean grid[][] = new Boolean[Arena.ARENA_SIZE][Arena.ARENA_SIZE];

    /**
     * creates a board array for the program to keep track
     * of hits, misses, and empty cells
     */
    public Board() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = null;
            }
        }
    }

    /**
     * returns the value of te cell, null, true, false
     * @param x int
     * @param y int
     * @return Boolean
     */
    public Boolean isEmpty(int x, int y) {
        return grid[x][y];
    }

    /**
     * sets the cell to true
     * @param x int
     * @param y int
     */
    public void setHit(int x, int y) {
        grid[x][y] = true;
    }

    /**
     * sets the cell to false
     * @param x int
     * @param y int
     */
    public void setMiss(int x, int y) {
        grid[x][y] = false;
    }

}
