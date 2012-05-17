/**
 * An AI class with methods to place ships,
 * validate ship positions, and fire on enemy ships
 * @author Marshall
 */
public class AI {
    public final static int RANGE = 11;


    /**
     * creates an AI
     * @param fleet Fleet
     */
    public void placeShips(Fleet fleet) {



        
        boolean[][] locations = new boolean[RANGE - 1][RANGE - 1];

        int rotation;
        Ship ship;
        int size;
        int xRange = RANGE - 1;
        int yRange = RANGE - 1;


        int xPos;
        int yPos;

        Ship.Rotation rot = Ship.Rotation.HORIZONTAL;

        int[] pos = new int[2];


        // fill array with numbers
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j] = true;
            }
        }

        // position each ship
        for (int p = 0; p < Fleet.FLEET_SIZE; p++) {
            xRange = RANGE;
            yRange = RANGE;
            xPos = 0;
            yPos = 0;
            
            rotation = (int) Math.round(Math.random());
            ship = fleet.getShip(p);
            size = ship.getSize();
            boolean ok = true;

            
            if (rotation == 0) {
                xRange -= size + 1;
            } else {
                yRange -= size + 1;
            }

            while (ok) {
                xPos = (int) (Math.random() * xRange - 1) + 1;
                yPos = (int) (Math.random() * yRange - 1) + 1;


                if (xPos == xRange) xPos -= 1;
                else if (xPos == 0) xPos += 1;
                if (yPos == yRange) yPos -= 1;
                else if (yPos == 0) yPos += 1;
                
                xPos--;
                yPos--;


                ok = false;
                
                for (int c = 0; c < size; c++) {
                    if (rotation == 0) {
                        if (!locations[xPos + c][yPos]) {
                            ok = true;
                        }
                    } else {
                        if (!locations[xPos][yPos + c]) {
                            ok = true;
                        }
                    }

                }

                if (!ok) {
                    for (int c = 0; c < size; c++) {
                        if (rotation == 0) {
                            rot = Ship.Rotation.HORIZONTAL;
                            locations[xPos + c][yPos] = false;
                        } else {
                            rot = Ship.Rotation.VERTICAL;
                            locations[xPos][yPos + c] = false;
                        }

                    }
                }
                
                
            }

            ship.setPosition(xPos, yPos);
            ship.setRotation(rot);

        }

    }

    /**
     * checks the fleet positions to make sure they are valid
     *
     * @param fleet Fleet
     * @return boolean
     */
    public boolean isFleetPositionValid(Fleet fleet) {
        boolean[][] locations = new boolean[RANGE - 1][RANGE - 1];

        int rotation;
        Ship ship;
        int size;
        int xRange = RANGE - 1;
        int yRange = RANGE - 1;


        int xPos;
        int yPos;

        Ship.Rotation rot = Ship.Rotation.HORIZONTAL;

        int[] pos = new int[2];


        // fill array with numbers
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j] = true;
            }
        }

        // position each ship
        for (int p = 0; p < Fleet.FLEET_SIZE; p++) {
            xRange = RANGE;
            yRange = RANGE;
            xPos = 0;
            yPos = 0;


            ship = fleet.getShip(p);
            size = ship.getSize();
            if (ship.getRotation() == Ship.Rotation.HORIZONTAL) {
                rotation = 0;
            } else rotation = 1;
            boolean ok = true;


            if (rotation == 0) {
                xRange -= size + 1;
            } else {
                yRange -= size + 1;
            }


            xPos = ship.getPosition()[0];
            yPos = ship.getPosition()[1];



            for (int c = 0; c < size; c++) {
                if (rotation == 0) {
                    if (!locations[xPos + c][yPos]) {
                        return false;
                    }
                } else {
                    if (!locations[xPos][yPos + c]) {
                        return false;
                    }
                }

            }


            for (int c = 0; c < size; c++) {
                if (rotation == 0) {
                    locations[xPos + c][yPos] = false;
                } else {
                    locations[xPos][yPos + c] = false;
                }

            }







        }
        return true;

    }
    
    /**
     * tells the ai to take a choose a cell to shoot at
     *
     * @param grid Board
     * @return int[]
     */
    public int[] takeShot(Board grid) {
        int xRange = RANGE;
        int yRange = RANGE;
        int xPos = 0;
        int yPos = 0;
        int[] coordinates = new int[2];
        
        boolean shot = true;
        
        while (shot) {
            
            xPos = (int) (Math.random() * xRange - 1) + 1;
            yPos = (int) (Math.random() * yRange - 1) + 1;


            if (xPos == xRange) xPos -= 1;
            else if (xPos == 0) xPos += 1;
            if (yPos == yRange) yPos -= 1;
            else if (yPos == 0) yPos += 1;

            xPos--;
            yPos--;
            
            if (grid.isEmpty(xPos, yPos) == null) {
                shot = false;
            }
            
        }
        coordinates[0] = xPos;
        coordinates[1] = yPos;

        return coordinates;
        
        
    }
}