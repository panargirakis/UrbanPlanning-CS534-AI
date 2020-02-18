package Algorithms;

import Buildings.BuildingType;
import Buildings.BuildingTile;
import Buildings.NoBuildingTile;
import Map.TerrainType;
import Map.UrbanMap;
import java.util.Random;

public class HillClimb {

    UrbanMap startMap;
    UrbanMap bestMap;
    int bestValue;
    int temperature;

    public HillClimb(UrbanMap map) {
        this.startMap = map;
        this.bestMap = map;
        this.bestValue = 0;
        this.temperature = 5; //SUBJECT TO CHANGE
    }

    /**
     * Run the hill climbing algorithm
     * STILL NEED TO IMPLEMENT RANDOM RESTARTS
     */
    public UrbanMap run() {
        UrbanMap currentMap = this.startMap;
        currentMap.setBuildingsOnMapRandomly(0, 0,0); //fill in paramaters
        long startTime = System.currentTimeMillis(); //one way of limiting run time, can change to different method
        while(System.currentTimeMillis() - startTime < 10000) {
            UrbanMap temp = generateMove(currentMap); //generate a possible move
            if (probability(temp.getValueOfMap())) currentMap = temp; //make the move according to prob. function
            if (currentMap.getValueOfMap() > this.bestValue) { //save the map if it has best values so far
                this.bestValue = currentMap.getValueOfMap();
                this.bestMap = currentMap;
            }
        }
        return this.bestMap; //when 10 seconds have elapsed, return the highest valued map
    }

    /**
     * Probability function for simulated annealing
     * Returns true if the algorithm should make the move given map value
     */
    private boolean probability(int value) {
        //fill in probability function here
        //calculate whether algorithm should make the move
        //lower the temperature
        return true;
    }

    /**
     * Given the current working map, generate a new possible map by moving one building
     * Can also give functionality to add/remove existing buildings
     */
    private UrbanMap generateMove(UrbanMap map) {
        //generate a new map that makes random move and return it
        Random rand = new Random();

        int row = map.getRows();
        int col = map.getCols();
        int startRow = 0;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;
        boolean valid = false;
        //find position that has a building
        while(!valid) {
            startRow = rand.nextInt(row);
            startCol = rand.nextInt(col);
            if (map.getTerrain(startRow, startCol).getBuildingType() != BuildingType.EMPTY) valid = true;
        }
        //find position with no building (not toxic) to move building to
        valid = false;
        while(!valid) {
            endRow = rand.nextInt(row);
            endCol = rand.nextInt(col);
            if (map.getTerrain(endRow, endCol).getBuildingType() == BuildingType.EMPTY && map.getTerrain(endRow, endCol).getType() != TerrainType.TOXIC) valid = true;
        }
        BuildingTile b = map.getTerrain(startRow, startCol).getBuildingTile(); //get building tile of the building we're moving
        map.changeBuilding(startRow, startCol, new NoBuildingTile()); //set the location of the building we're moving to empty
        map.changeBuilding(endRow, endCol, b); //move the building
        return map;
    }

}
