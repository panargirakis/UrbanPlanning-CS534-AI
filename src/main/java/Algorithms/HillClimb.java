package Algorithms;

import Buildings.*;
import Map.UrbanMap;
import java.util.Random;

public class HillClimb {

    UrbanMap startMap;
    UrbanMap bestMap;
    int bestValue;
    double bestTime;
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
        this.bestTime = 0.0;
        this.temperature = temp;
        this.decreaseRatio = decrease;
        this.maxRestart = restart;
        this.minWorseMoves = worse;
    }

    /*
    * run the hill climb algorithm
    * return the best valued map
     */
    public Metrics runHillClimb() {
        UrbanMap currentMap = UrbanMap.randomBuildingsMap(startMap);
        long startTime = System.currentTimeMillis(); //start the time
        int count = 0; //counts number of consecutive sideways/worse moves
        int restarts = 0; //counts the number of times algorithm has restarted
        while(System.currentTimeMillis() - startTime < 2000) {
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
                bestTime = ((double)(System.currentTimeMillis()-startTime))/1000;
            }
        }
        System.out.println(bestMap);
        System.out.println(bestTime);
        return new Metrics(bestValue, bestTime, bestMap.getStringRepresentation());
    }

    /*
    * probability function for simulated annealing
    * returns true if algorithm should make the proposed new move
     */
    private boolean probability(int newValue, int currentValue) {
//        if (newValue > currentValue) { //take the move if it is better
//            temperature *= decreaseRatio; //if we make a move, decrease the temp
//            return true;
//        }
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
    * moves, adds, or removes a single building
     */
    private UrbanMap generateMove(UrbanMap map) {
        Random rand = new Random();
        boolean valid = false;
        int buildings = map.getBuildingTotalCount();
        int choice = 0;
        while (!valid) { //determine what type of new move to take
            choice = rand.nextInt(3);
            if (choice == 0) { //0 is move building, should be at least one building
                if (buildings > 0) valid = true;
            }
            if (choice == 1) { //1 is add building, should be under limit
                if (buildings < map.getMaxCommercial() + map.getMaxIndustrial() + map.getMaxResidential()) valid = true;
            }
            if (choice == 2) { //2 is remove building, should be at least one building
                if (buildings > 0) valid = true;
            }
        }
        if (choice == 0) return moveBuilding(map);
        if (choice == 1) return addBuilding(map);
        return removeBuilding(map);
    }

    /*
    * moves a building on the map to an empty space
     */
    private UrbanMap moveBuilding(UrbanMap map) {
        UrbanMap newMove = new UrbanMap(map);
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

    /*
    * adds a new building to the map
     */
    private UrbanMap addBuilding(UrbanMap map) {
        UrbanMap newMove = new UrbanMap(map);
        Random rand = new Random();
        int row = 0; int col = 0;
        boolean valid = false;
        //find an empty spot
        while (!valid) {
            row = rand.nextInt(map.getRows());
            col = rand.nextInt(map.getCols());
            if (map.getTerrainAt(row, col).getBuildingType() == BuildingType.EMPTY) valid = true;
        }
        int choice = 0;
        valid = false;
        //determine which type of building to add
        while (!valid) {
            choice = rand.nextInt(3);
            switch (choice) {
                case 0:
                    if (map.getBuildingTypeCount(BuildingType.INDUSTRIAL) < map.getMaxIndustrial()) valid = true;
                    break;
                case 1:
                    if (map.getBuildingTypeCount(BuildingType.COMMERCIAL) < map.getMaxCommercial()) valid = true;
                    break;
                case 2:
                    if (map.getBuildingTypeCount(BuildingType.RESIDENTIAL) < map.getMaxResidential()) valid = true;
                    break;
            }
        }
        //set the new building
        if (choice == 0) newMove.setBuildingAt(new IndustrialTile(), row, col);
        if (choice == 1) newMove.setBuildingAt(new CommercialTile(), row, col);
        if (choice == 2) newMove.setBuildingAt(new ResidentialTile(), row, col);
        return newMove;
    }

    /*
    * removes a building from the map
     */
    private UrbanMap removeBuilding(UrbanMap map) {
        UrbanMap newMove = new UrbanMap(map);
        Random rand = new Random();
        int row = 0; int col = 0;
        boolean valid = false;
        //find a position with a building
        while (!valid) {
            row = rand.nextInt(map.getRows());
            col = rand.nextInt(map.getCols());
            if (map.getTerrainAt(row, col).getBuildingType() != BuildingType.EMPTY) valid = true;
        }
        newMove.setBuildingAt(new NoBuildingTile(), row, col); //remove the building
        return newMove;
    }

}
