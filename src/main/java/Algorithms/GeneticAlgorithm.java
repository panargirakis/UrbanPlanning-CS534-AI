package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Buildings.BuildingType;
import Map.UrbanMap;

public class GeneticAlgorithm {

    // Default Constructor
    public GeneticAlgorithm() {}

    /*
     * runGeneticAlgorithm() Runs the genetic algorithm on the given terrain map.
     */
    public UrbanMap runGeneticAlgorithm(UrbanMap initMap, int numGenerations, int generationSize, int numChildren, int numParents, int numNew) {

        // 1: Create the initial population of x maps with random buildings
        List<UrbanMap> initialPopulation = this.generateRandomPopulation(initMap, generationSize);
        System.out.println(initialPopulation);

        /* RUN GENERATIONS */
        List<UrbanMap> finalGeneration = runGenerations(initMap, initialPopulation, numGenerations, numChildren, numParents, numNew);

        /* CHOOSE BEST MAP FROM FINAL GENERATION */
        Collections.sort(finalGeneration);
        return finalGeneration.get(0);
    }

    private List<UrbanMap> runGenerations(UrbanMap initMap, List<UrbanMap> initialPopulation, int numGenerations, int numChildren, int numParents, int numNew) {

        List<UrbanMap> currentGeneration = new ArrayList<UrbanMap>();
        currentGeneration.addAll(initialPopulation);

        for(int generation = 0; generation < numGenerations; generation++){

            // 2: Choose the best (numParents) of this generation to serve as parents.
            List<UrbanMap> parentPopulation = chooseBestPopulations(currentGeneration, numParents);
            // 3: 'Mate' the chosen parents
            List<UrbanMap> childPopulation = mateParents(parentPopulation, numChildren);
            // 4: Generate (numNew) new random maps to add new 'Genes' to the pool
            List<UrbanMap> newRandomMaps = generateRandomPopulation(initMap, numNew);

            // Add the new population to the next generation
            currentGeneration.clear();
            currentGeneration.addAll(parentPopulation);
            
            currentGeneration.addAll(childPopulation);
            currentGeneration.addAll(newRandomMaps);

            // System.out.println("---");
            // System.out.println(parentPopulation);
            // System.out.println(childPopulation);
            // System.out.println(newRandomMaps);
            // System.out.println(currentGeneration + "\n");
        }// 5: Repeat (numGenerations) times

        // After the loop, current next generation will be the final generation.
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
        // System.out.println("=== - " + currentGeneration);
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
    private List<UrbanMap> mateParents(List<UrbanMap> parentPopulation, int numberOfChildren) {

        // List of children
        List<UrbanMap> childPopulation = new ArrayList<UrbanMap>();

        // Mate 2 parent maps
        for(int i = 1; i < parentPopulation.size(); i = i + 2){
            childPopulation.add(mateMaps(parentPopulation.get(i-1), parentPopulation.get(i)));
        }

        return childPopulation;
    }

    /*
     * mateMaps() Mate the 2 given maps
     */
    private UrbanMap mateMaps(UrbanMap map1, UrbanMap map2) {

        UrbanMap childMap = new UrbanMap(map1);

        int splitPoint = map1.mapWidth/2;

        // Just choose 50 percent of each parent
        for(int row = 0; row < childMap.mapHeight; row++){

            // // Take the 0-splitPoint elements of the row for the child map
            // for(int firstCol = 0; firstCol < splitPoint; firstCol++){
            //     // Set this terrain in the map to be map2's
            //     childMap.setTerrain(row, firstCol, map2);
            // }
            // Take the splitPoint-mapWidth elements of the row for the child map
            for(int secondCol = splitPoint; secondCol < map1.mapWidth; secondCol++){
                // Set this terrain in the map to be map2's
                childMap.setTerrain(row, secondCol, map2);
            }
        }

        // If there are too many of any building type, just randomly delete some until its ok
        childMap.ensureSatisfiesBuildingCount(BuildingType.RESIDENTIAL, childMap.maxResidential);
        childMap.ensureSatisfiesBuildingCount(BuildingType.COMMERCIAL, childMap.maxCommercial);
        childMap.ensureSatisfiesBuildingCount(BuildingType.INDUSTRIAL, childMap.maxIndustrial);

        //System.out.println(childMap);

        return childMap;
    }

}
