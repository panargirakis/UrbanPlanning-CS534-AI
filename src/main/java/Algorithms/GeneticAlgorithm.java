package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Buildings.BuildingType;
import Map.UrbanMap;

public class GeneticAlgorithm {

    // A list containing the population of maps
    List<UrbanMap> population;

    public GeneticAlgorithm() {
        this.population = new ArrayList<UrbanMap>();
    }

    /*
     * runGeneticAlgorithm() Runs the genetic algorithm on the given terrain map.
     */
    public UrbanMap runGeneticAlgorithm(UrbanMap initMap, int numGenerations, int populationSize) {

        /* SETUP */
        populationSize = 50;    // For a population size of 50
        int numChildren = populationSize - 20; // Generate 30 new children
        int numParents = populationSize - 40;  // Keep 10 parents
        int numNew = populationSize - 40; // Add 10 new random maps to the simualtion (Remainder after parents and children)

        // 1: Create the initial population of x maps with random buildings
        List<UrbanMap> initialPopulation = this.generateRandomPopulation(initMap, populationSize);

        /* RUN GENERATIONS */
        List<UrbanMap> finalGeneration = runGenerations(initMap, initialPopulation, numGenerations, numChildren, numParents, numNew);

        /* CHOOSE BEST MAP FROM FINAL GENERATION */
        return finalGeneration.get(0);
    }

    public List<UrbanMap> runGenerations(UrbanMap initMap, List<UrbanMap> currentGeneration, int numGenerations, int numChildren, int numParents, int numNew) {

        List<UrbanMap> nextGeneration = currentGeneration;

        for(int generation = 0; generation < numGenerations; generation++){

            // 2: Choose the best (numParents) of them to serve as parents.
            List<UrbanMap> parentPopulation = chooseBestPopulations(nextGeneration, numParents);
            // 3: 'Mate' the chosen parents
            List<UrbanMap> childPopulation = mateParents(parentPopulation, numChildren);
            // 4: Generate (numNew) new random maps to add new 'Genes' to the pool
            List<UrbanMap> newRandomMaps = generateRandomPopulation(initMap, numNew);

            // Add the new population to the next generation
            nextGeneration = new ArrayList<UrbanMap>();
            nextGeneration.addAll(parentPopulation);
            nextGeneration.addAll(childPopulation);
            nextGeneration.addAll(newRandomMaps);

        }// 5: Repeat (numGenerations) times

        // After the loop, current next generation will be the final generation.
        return nextGeneration;
    }

    /*
     * generateRandomPopulation() generates a list of maps with random buildings of
     * the given size.
     */
    private List<UrbanMap> generateRandomPopulation(UrbanMap initMap, int populationSize) {

        List<UrbanMap> randomPopulation = new ArrayList<UrbanMap>();

        for (int i = 0; i < populationSize; i++) {
            // Add randomly generated maps to the list of maps
            randomPopulation.add(new UrbanMap(initMap.randomBuildingsMap()));
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
        return population.subList(0, numToKeep);
    }

    /*
     * mateMaps() Mate the maps in the population of maps to create a new generation
     * of maps.
     */
    private List<UrbanMap> mateParents(List<UrbanMap> parentPopulation, int numberOfChildren) {

        // List of children
        List<UrbanMap> childPopulation = new ArrayList<UrbanMap>();

        // Potential low probability for mutation?s

        // Mate 2 parent maps
        for(int i = 1; i < parentPopulation.size(); i = i + 2){
            childPopulation.add(mateMaps(parentPopulation.get(i-1), parentPopulation.get(i)));
        }
        //childPopulation.add(mateMaps());

        return childPopulation;
    }

    /*
     * mateMaps() Mate the 2 given maps
     */
    private UrbanMap mateMaps(UrbanMap map1, UrbanMap map2) {

        UrbanMap childMap = new UrbanMap(map1);

        // Just choose 50 percent of each parent
        for(int row = 0; row < childMap.mapWidth; row++){
            // increments by 2 each time so that only 50% of elements are changed
            for(int col = 1; col < childMap.mapHeight; col = col + 2){
                // Set this terrain in the map to be map2's
                childMap.setTerrain(row,col, map2);
            }
        }

        // If there are too many of any building type, just randomly delete some until its ok
        childMap.ensureSatisfiesBuildingCount(BuildingType.RESIDENTIAL, childMap.maxResidential);
        childMap.ensureSatisfiesBuildingCount(BuildingType.COMMERCIAL, childMap.maxCommercial);
        childMap.ensureSatisfiesBuildingCount(BuildingType.INDUSTRIAL, childMap.maxIndustrial);

        return childMap;
    }

}
