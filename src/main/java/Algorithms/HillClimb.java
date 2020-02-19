package Algorithms;

import Buildings.BuildingType;
import Buildings.BuildingTile;
import Buildings.NoBuildingTile;
import Map.TerrainType;
import Map.Terrain;
import Map.UrbanMap;
import java.util.Random;
import java.util.ArrayList;

public class HillClimb {

    UrbanMap startMap;
    UrbanMap bestMap;
    int bestValue;
    int temperature;
    double decreaseRatio;
    int maxRestart;
    int maxWorseMoves;

    /*
    * Constructor
     */
    public HillClimb(UrbanMap map, int temp, double decrease, int restart, int worse) {
        this.startMap = new UrbanMap(map);
        this.bestMap = new UrbanMap(map);
        this.bestValue = bestMap.getValueOfMap();
        this.temperature = temp;
        this.decreaseRatio = decrease;
        this.maxRestart = restart;
        this.maxWorseMoves = worse;
    }

    public UrbanMap runHillClimb() {
        UrbanMap currentMap = UrbanMap.randomBuildingsMap(startMap);
        long startTime = System.currentTimeMillis();
        int count; //counts number of consecutive sideways/worse moves
        while(System.currentTimeMillis() - startTime < 3) {
            UrbanMap move = generateMove(currentMap);
            if (probability(move.getValueOfMap(), currentMap.getValueOfMap())) {
                currentMap = move;
            }
            if (currentMap.getValueOfMap() > bestValue) {
                bestMap = currentMap;
                bestValue = currentMap.getValueOfMap();
            }
            System.out.println(bestMap);
        }
        return bestMap;
    }

    private boolean probability(int newValue, int currentValue) {
        if (newValue > currentValue) return true; //take the move if it's better
        return false;
    }

    /*
    * given a map generates a next possible move
    * currently only moves buildings, does not add or remove
     */
    private UrbanMap generateMove(UrbanMap map) {
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

}
