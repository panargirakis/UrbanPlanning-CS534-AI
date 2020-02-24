package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Buildings.BuildingType;
import Map.UrbanMap;

public class GeneticAlgorithm {

    // Time Tracking information
    long startTime;
    long bestSolutionTime;
    int bestValue;
    Random r;


    // Default Constructor
    public GeneticAlgorithm() {
        startTime = 0;
        bestSolutionTime = 0;
        bestValue = -9999;
        r = new Random();
    }

    /*
     * runGeneticAlgorithm() Runs the genetic algorithm on the given terrain map.
     */
    public Metrics runGeneticAlgorithm(UrbanMap initMap, int numGenerations, int generationSizeElitism, int numParents, int numChildren, int numNew, int mutationChance, long millisecondsToRun) {

        // 1: Create the initial population of x maps with random buildings
        List<UrbanMap> initialPopulation = this.generateRandomPopulation(initMap, generationSizeElitism);

        /* RUN GENERATIONS */
        startTime = System.currentTimeMillis();
        List<UrbanMap> finalGeneration = runGenerations(initMap, initialPopulation, numGenerations, generationSizeElitism, numChildren, numParents, numNew, mutationChance, millisecondsToRun);

        /* CHOOSE BEST MAP FROM FINAL GENERATION */
        Collections.sort(finalGeneration);
        UrbanMap finalMap = finalGeneration.get(0);
        System.out.println(finalMap);
        System.out.println("Best Map Found At: " + this.bestSolutionTime + " milliseconds.");
        return new Metrics(finalMap.getValueOfMap(), this.bestSolutionTime / 1000.0, finalMap.getStringRepresentation());
    }

    private List<UrbanMap> runGenerations(UrbanMap initMap, List<UrbanMap> initialPopulation, int numGenerations, int generationSizeElitism, int numChildren, int numParents, int numNew, int mutationChance, long millisecondsToRun) {

        List<UrbanMap> currentGeneration = new ArrayList<UrbanMap>();
        currentGeneration.addAll(initialPopulation);
        Collections.copy(currentGeneration, initialPopulation);

        for(int generation = 0; generation < numGenerations; generation++){

            // If it takes more than 10 seconds, quit.
            if(System.currentTimeMillis() - this.startTime >= millisecondsToRun){
                break;
            }

            // 2: Choose the best (numParents) of this generation to serve as parents. (Selection)
            List<UrbanMap> parentPopulation = chooseBestPopulations(currentGeneration, numParents);
            // 3: 'Mate' the chosen parents. (CrossOver)
            List<UrbanMap> childPopulation = mateParents(parentPopulation, numChildren, mutationChance);
            // 4: Generate (numNew) new random maps to add new 'Genes' to the pool
            List<UrbanMap> newRandomMaps = generateRandomPopulation(initMap, numNew);

            // Add the new population to the next generation
            currentGeneration.clear();
            currentGeneration.addAll(parentPopulation);            
            currentGeneration.addAll(childPopulation);
            currentGeneration.addAll(newRandomMaps);
            Collections.sort(currentGeneration);
            currentGeneration = chooseBestPopulations(currentGeneration, generationSizeElitism);

            // Check for the current best value
            if(this.bestValue < currentGeneration.get(0).getValueOfMap()){
                bestSolutionTime = (System.currentTimeMillis() - this.startTime);
            }

        }// 5: Repeat (numGenerations) times

        // After the loop, current generation will be the final generation.
        return currentGeneration;
    }

    /*
     * generateRandomPopulation() generates a list of maps with random buildings of
     * the given size.
     */
    private List<UrbanMap> generateRandomPopulation(UrbanMap initMap, int populationSize) {

        List<UrbanMap> randomPopulation = new ArrayList<UrbanMap>();

        for (int i = 0; i < populationSize; i++) {
            // Add randomly generated maps to the list of maps
            randomPopulation.add(UrbanMap.randomBuildingsMap(initMap));
        }
        return randomPopulation;
    }

    /*
     * chooseBestPopulations() chooses a list of the best map layouts based on the
     * percentage of maps to choose
     */
    private List<UrbanMap> chooseBestPopulations(List<UrbanMap> currentGeneration, int numToKeep) {
        // Sort the population
        Collections.sort(currentGeneration);
        // Take the first x number of elements of the population.
        List<UrbanMap> bestMaps = new ArrayList<UrbanMap>();
        bestMaps.addAll(currentGeneration.subList(0, numToKeep));
        
        return bestMaps;
    }

    /*
     * mateMaps() Mate the maps in the population of maps to create a new generation
     * of maps.
     */
    private List<UrbanMap> mateParents(List<UrbanMap> parentPopulation, int numberOfChildren, int mutationChance) {

        // List of children
        List<UrbanMap> childPopulation = new ArrayList<UrbanMap>();
        // size of the parent population
        int parentPopulationSize = parentPopulation.size();

        // Mate 2 parent maps
        for(int i = 1; i < numberOfChildren; i++){
            int map1Element = r.nextInt(parentPopulationSize);
            int map2Element = r.nextInt(parentPopulationSize);
            childPopulation.add(mateMaps(parentPopulation.get(map1Element), parentPopulation.get(map2Element), mutationChance));
        }

        return childPopulation;
    }

    /*
     * mateMaps() Mate the 2 given maps
     */
    private UrbanMap mateMaps(UrbanMap map1, UrbanMap map2, int mutationChance) {

        UrbanMap childMap = new UrbanMap(map1);

        // There is a 50/50 chance that we will split the map vertically or horizontally:
        if(r.nextInt(2) == 0){
            // Split Vertically (by Columns)

            // first split point will be at this random location
            int splitPoint1 = r.nextInt(map1.mapWidth);
            // first split point will be at this random location
            int splitPoint2 = r.nextInt(map1.mapWidth);

            if(splitPoint1 > splitPoint2){
                int temp = splitPoint1;
                splitPoint1 = splitPoint2;
                splitPoint2 = temp;
            }

            // Go through all rows and choose the cols from splitPoint1-splitPoint2 to keep form map2.
            for(int row = 0; row < childMap.mapHeight; row++){

                // // Take the splitPoint1-splitPoint2 elements of map2 for the child map
                // Take the rest of the elements from map1
                for(int secondCol = splitPoint1; secondCol < splitPoint2; secondCol++){
                    // Set this terrain in the map to be map2's
                    childMap.replaceBuildingFromMap(row, secondCol, map2);
                }
            }
        }
        else{
            // Split Horizontally (by Rows)

            // first split point will be at this random location
            int splitPoint1 = r.nextInt(map1.mapHeight);
            // first split point will be at this random location
            int splitPoint2 = r.nextInt(map1.mapHeight);

            if(splitPoint1 > splitPoint2){
                int temp = splitPoint1;
                splitPoint1 = splitPoint2;
                splitPoint2 = temp;
            }

            // Just choose the splitPoint1-splitPoint2 of map2 to give to the child
            for(int row = splitPoint1; row < splitPoint2; row++){

                // // Take the 0-splitPoint elements of map1 for the child map
                // Take the splitPoint-mapheight elements of map2 for the child map
                for(int col = 0; col < map1.mapWidth; col++){
                    // Set this terrain in the map to be map2's
                    childMap.replaceBuildingFromMap(row, col, map2);
                }
            }
        }

        // Potential Chance for Mutation
        if(r.nextInt(mutationChance) == 1){
            childMap.mutate();
        }

        // If there are too many of any building type, just randomly delete some until its ok
        childMap.ensureSatisfiesBuildingCount(BuildingType.RESIDENTIAL, childMap.maxResidential);
        childMap.ensureSatisfiesBuildingCount(BuildingType.COMMERCIAL, childMap.maxCommercial);
        childMap.ensureSatisfiesBuildingCount(BuildingType.INDUSTRIAL, childMap.maxIndustrial);

        return childMap;
    }

}
