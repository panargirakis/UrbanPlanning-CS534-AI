package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Buildings.BuildingType;
import Map.UrbanMap;

public class GeneticAlgorithm {

    // Default Constructor
    public GeneticAlgorithm() {}

    /*
     * runGeneticAlgorithm() Runs the genetic algorithm on the given terrain map.
     */
    static public Metrics runGeneticAlgorithm(UrbanMap initMap, int numGenerations, int generationSize, int numChildren, int numParents, int numNew, int mutationChance) {

        // 1: Create the initial population of x maps with random buildings
        List<UrbanMap> initialPopulation = GeneticAlgorithm.generateRandomPopulation(initMap, generationSize);

        /* RUN GENERATIONS */
        List<UrbanMap> finalGeneration = runGenerations(initMap, initialPopulation, numGenerations, numChildren, numParents, numNew, mutationChance);

        /* CHOOSE BEST MAP FROM FINAL GENERATION */
        Collections.sort(finalGeneration);
        UrbanMap finalMap = finalGeneration.get(0);
        return new Metrics(finalMap.getValueOfMap(), 0, finalMap.getStringRepresentation());
    }

    static private List<UrbanMap> runGenerations(UrbanMap initMap, List<UrbanMap> initialPopulation, int numGenerations, int numChildren, int numParents, int numNew, int mutationChance) {

        List<UrbanMap> currentGeneration = new ArrayList<UrbanMap>();
        currentGeneration.addAll(initialPopulation);
        Collections.copy(currentGeneration, initialPopulation);
        // Track the time until the algorithm has reached 10 seconds.
        long startTime = System.currentTimeMillis();

        for(int generation = 0; generation < numGenerations; generation++){

            // If it takes more than 10 seconds, quit.
            if(System.currentTimeMillis() - startTime >= 10000){
                break;
            }

            // 2: Choose the best (numParents) of this generation to serve as parents.
            List<UrbanMap> parentPopulation = chooseBestPopulations(currentGeneration, numParents);
            // 3: 'Mate' the chosen parents
            List<UrbanMap> childPopulation = mateParents(parentPopulation, numChildren, mutationChance);
            // 4: Generate (numNew) new random maps to add new 'Genes' to the pool
            List<UrbanMap> newRandomMaps = generateRandomPopulation(initMap, numNew);

            // Add the new population to the next generation
            currentGeneration.clear();
            currentGeneration.addAll(parentPopulation);            
            currentGeneration.addAll(childPopulation);
            currentGeneration.addAll(newRandomMaps);
            Collections.copy(currentGeneration, currentGeneration);

        }// 5: Repeat (numGenerations) times

        // After the loop, current next generation will be the final generation.
        return currentGeneration;
    }

    /*
     * generateRandomPopulation() generates a list of maps with random buildings of
     * the given size.
     */
    static private List<UrbanMap> generateRandomPopulation(UrbanMap initMap, int populationSize) {

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
    static private List<UrbanMap> chooseBestPopulations(List<UrbanMap> currentGeneration, int numToKeep) {
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
    static private List<UrbanMap> mateParents(List<UrbanMap> parentPopulation, int numberOfChildren, int mutationChance) {

        // List of children
        List<UrbanMap> childPopulation = new ArrayList<UrbanMap>();
        // size of the parent population
        int parentPopulationSize = parentPopulation.size();
        // Randomizer
        Random r = new Random();

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
    static private UrbanMap mateMaps(UrbanMap map1, UrbanMap map2, int mutationChance) {

        UrbanMap childMap = new UrbanMap(map1);

        int splitPoint = map1.mapWidth/2;

        // Just choose 50 percent of each parent
        for(int row = 0; row < childMap.mapHeight; row++){

            // // Take the 0-splitPoint elements of map1 for the child map
            // Take the splitPoint-mapWidth elements of map2 for the child map
            for(int secondCol = splitPoint; secondCol < map1.mapWidth; secondCol++){
                // Set this terrain in the map to be map2's
                childMap.replaceBuildingFromMap(row, secondCol, map2);
            }
        }

        // Potential Chance for Mutation
        Random r = new Random();
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
