package model;

import java.io.Serializable;

/**
 * fleet of ships
 * 
 * @author Marshall
 */
public class Fleet implements Serializable {
    public static final int FLEET_SIZE = 5;
    private int health = FLEET_SIZE;

    private Ship[] fleet = { new PTBoat(), new Destroyer(), new Submarine(),
            new Battleship(), new Carrier() };

    private Board board = new Board();

    /**
     * creates the fleet
     */
    public Fleet() {

    }

    /**
     * returns the ship that was at the specified coordinates
     * 
     * @param x
     *            int
     * @param y
     *            int
     * @return Ship
     */
    public Ship isHit(ShotLocation shot) {
        int x = shot.x, y = shot.y;
        Ship hit = null;
        Ship.Rotation rotation;
        int[] position;
        int direction;
        int[] coordinates = { x, y };

        for (int i = 0; i < fleet.length; i++) {
            rotation = fleet[i].getRotation();
            position = fleet[i].getPosition();
            if (rotation == Ship.Rotation.HORIZONTAL) {
                direction = 0;
            } else {
                direction = 1;
            }

            if (coordinates[Math.abs(direction - 1)] == position[Math
                    .abs(direction - 1)]) {
                for (int b = 0; b < fleet[i].getSize(); b++) {
                    if (x == position[0] && y == position[1]) {
                        hit = fleet[i];
                        fleet[i].hit();
                        if (fleet[i].getHealth() == 0) {
                            this.health--;
                        }
                    }
                    position[direction]++;
                }
            }
        }

        return hit;
    }

    /**
     * sets the position of the ship
     * 
     * @param ship
     *            int
     * @param x
     *            int
     * @param y
     *            int
     * @param rotation
     *            Ship.Rotation
     */
    public void positionShip(int ship, int x, int y, Ship.Rotation rotation) {
        fleet[ship].setPosition(x, y);
        fleet[ship].setRotation(rotation);
    }

    /**
     * gets the ship at the specified index
     * 
     * @param index
     *            int
     * @return Ship
     */
    public Ship getShip(int index) {
        return fleet[index];
    }

    /**
     * gets the health of the fleet
     * 
     * @return int
     */
    public int getHealth() {
        return this.health;
    }
}