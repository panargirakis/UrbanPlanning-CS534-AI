package Algorithms;

import Buildings.BuildingType;
import Buildings.BuildingTile;
import Buildings.NoBuildingTile;
import Map.UrbanMap;
import java.util.Random;

public class HillClimb {

    UrbanMap startMap;
    UrbanMap bestMap;
    int bestValue;
    double temperature;
    double decreaseRatio;
    int maxRestart;
    int minWorseMoves;

    /*
    * Constructor
     */
    public HillClimb(UrbanMap map, double temp, double decrease, int restart, int worse) {
        this.startMap = new UrbanMap(map);
        this.bestMap = new UrbanMap(map);
        this.bestValue = bestMap.getValueOfMap();
        this.temperature = temp;
        this.decreaseRatio = decrease;
        this.maxRestart = restart;
        this.minWorseMoves = worse;
    }

    /*
    * run the hill climb algorithm
    * return the best valued map
     */
    public UrbanMap runHillClimb() {
        UrbanMap currentMap = UrbanMap.randomBuildingsMap(startMap);
        long startTime = System.currentTimeMillis();
        int count = 0; //counts number of consecutive sideways/worse moves
        int restarts = 0; //counts the number of times algorithm has restarted
        while(System.currentTimeMillis() - startTime < 10000) {
            if (count > minWorseMoves && restarts < maxRestart) { //if we reach the minimum worse move count, do a restart if we are within limit
                currentMap = UrbanMap.randomBuildingsMap(startMap);
                count = 0;
                restarts++;
            }
            UrbanMap move = generateMove(currentMap);
            if (move.getValueOfMap() < currentMap.getValueOfMap()) count++; //if the move we find is worse, increment worse move count
            else count = 0; //otherwise reset count to zero
            if (probability(move.getValueOfMap(), currentMap.getValueOfMap())) currentMap = move;
            if (currentMap.getValueOfMap() > bestValue) {
                bestMap = currentMap;
                bestValue = currentMap.getValueOfMap();
                System.out.println(bestMap);
            }
        }
        System.out.println(bestMap);
        return bestMap;
    }

    /*
    * probability function for simulated annealing
    * returns true if algorithm should make the proposed new move
     */
    private boolean probability(int newValue, int currentValue) {
        //System.out.println(newValue + " " + currentValue);
        if (newValue > currentValue) { //take the move if it is better
            temperature *= decreaseRatio; //if we make a move, decrease the temp
            return true;
        }
        double prob = Math.pow(Math.E, (newValue-currentValue)/temperature); //probability function
        double value = Math.random();
        if (value <= prob)  { //take the move according to probability function
            temperature *= decreaseRatio; //if we make a move, decrease the temp
            return true;
        }
        return false;
    }

    /*
    * given a map generates a next possible move
    * currently only moves buildings, does not add or remove
     */
    private UrbanMap generateMove(UrbanMap map) {
        UrbanMap newMove = new UrbanMap(map);
        //first check if the map has any buildings
        int buildings = 0;
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                if (map.getTerrainAt(i, j).getBuildingType() != BuildingType.EMPTY) buildings++;
            }
        }
        //if the map has no buildings, just return the map or algorithm gets stuck in a loop
        if (buildings == 0) return newMove;
        Random rand = new Random();
        int startRow = 0; int startCol = 0; int endRow = 0; int endCol = 0;
        boolean valid = false;
        //find a position with a building
        while (!valid) {
            startRow = rand.nextInt(map.getRows());
            startCol = rand.nextInt(map.getCols());
            if (map.getTerrainAt(startRow, startCol).getBuildingType() != BuildingType.EMPTY) valid = true;
        }
        //find a position to move building to
        valid = false;
        while (!valid) {
            endRow = rand.nextInt(map.getRows());
            endCol = rand.nextInt(map.getCols());
            if (map.getTerrainAt(endRow, endCol).getBuildingType() == BuildingType.EMPTY) valid = true;
        }
        BuildingTile b = map.getTerrainAt(startRow, startCol).getBuildingTile();
        newMove.setBuildingAt(new NoBuildingTile(), startRow, startCol); //removes building froms start location
        newMove.setBuildingAt(b, endRow, endCol); //puts a building on end location
        return newMove;
    }

}
