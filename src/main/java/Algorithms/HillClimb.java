package Algorithms;

import Map.UrbanMap;

public class HillClimb {

    UrbanMap startMap;
    UrbanMap bestMap;
    int bestValue;
    int temperature;

    public HillClimb(UrbanMap map) {
        this.startMap = map;
        this.bestMap = map;
        this.bestValue = 0;
        this.temperature = 100; //SUBJECT TO CHANGE
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
        return map;
    }

}
