package Map;

import Buildings.ResidentialTile;

public class UrbanMap
{

    // Terrain 2D Array
    private Terrain terrain[][];

    /* Map Constructor */
    public UrbanMap(String mapFileToParse){
        // Right now takes a string with all the terrain. May want to update to be a different input type?
        this.generateMapTerrain(mapFileToParse);
    }

    /*
    * generateMap(String mapFileToParse)
    * Takes a text file as a string (or other format) and generates the terrain layout.
    * May belong in IOModule - just have it here temporarily
    * This should only be called once per input file. Once the terrain has been set, our algorithms can only alter buildings, not terrain.
    */
    private void generateMapTerrain(String mapFileToParse){
        // Based on Width and Height of map (extraced from file):
        terrain = new Terrain[0][0];
    }

    /*
    * setBuildingsOnMap()
    * Places (random? Simulated Annealing? Genetic?) urban layout buildings on the map. To be used/implemented in algorithms.
    * Probably a temporary method - will need to be updated and changed based on the algorithm.
    */
    public void setBuildingsOnMap(){
        // Cannot build directly on a toxic waste site

        for(int row = 0; row < this.terrain.length; row++){
            for(int col = 0; col < this.terrain[row].length; col++){

                terrain[row][col].setBuilding(new ResidentialTile());

            }
        }

    }

    /*
    * getValueOfMap()
    * Returns the value of the map with the current urban layout.
    */
    public int getValueOfMap(){
        int mapValue = 0;

        //Iterate through every Terrain eleemnt of 2D array and check its value
        for(int row = 0; row < this.terrain.length; row++){
            for(int col = 0; col < this.terrain[row].length; col++){

                // Get the value of the current terrain and tile. Add to mapValue.
                mapValue += this.terrain[row][col].getValue(this, row, col);

            }
        }

        return mapValue;
    }

    /*
    * getToxicNeighbors(row,col)
    * Returns the number of Toxic terrains within 2 Manhattan Distances.
    */
    public int toxicWithinTwoSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check its type. Increment when Toxic.

        return 0;
    }

    /*
    * getScenicNeighbors(row,col)
    * Returns the number of Scenic terrains within 2 Manhattan Distances.
    */
    public int scenicWithinTwoSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check its type. Increment when Scenic.
        // UNLESS there is a building on the scenic view. Then, the view is destroyed and it is no longer scenic.

        return 0;
    }

    /*
    * industrialWithinTwoSquares(row,col)
    * Returns the number of Industrial Building Tiles within 2 Manhattan Distances.
    */
    public int industrialWithinTwoSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Industrial.

        return 0;
    }

    /*
    * industrialWithinThreeSquares(row,col)
    * Returns the number of Industrial Building Tiles within 3 Manhattan Distances.
    */
    public int industrialWithinThreeSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Industrial.

        return 0;
    }

    /*
    * residentialWithinThreeSquares(row,col)
    * Returns the number of Residential Building Tiles within 3 Manhattan Distances.
    */
    public int residentialWithinThreeSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Residential.
        // THREE Manahattan Distances

        return 0;
    }

    /*
    * commercialWithinTwoSquares(row,col)
    * Returns the number of Commercial Building Tiles within 2 Manhattan Distances.
    */
    public int commercialWithinTwoSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Commercial.

        return 0;
    }

    /*
    * commercialWithinThreeSquares(row,col)
    * Returns the number of Commercial Building Tiles within 3 Manhattan Distances.
    */
    public int commercialWithinThreeSquares(int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Commercial.

        return 0;
    }

}