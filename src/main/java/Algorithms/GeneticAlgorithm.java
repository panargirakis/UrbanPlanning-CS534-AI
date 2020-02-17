package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

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
    public UrbanMap runGeneticAlgorithm(UrbanMap initMap, int populationSize, float percentageToMate,
            int numGenerations) {

        // For a population size of 50
        populationSize = 50;

        // Generate 30 new children
        int numberOfChildren = populationSize - 20; // 30
        // Keep 10 parents
        int numberOfParents = populationSize - 40; // 10
        // Add 10 new random maps to the simualtion (Remainder after parents and children)
        int numberToGenerate = populationSize - 40; // 10

        // 1: Create the initial population of x maps with random buildings
        population.addAll(generateRandomPopulation(initMap, populationSize));

        // 2: Choose the best x% of them
        List<UrbanMap> parentPopulation = chooseBestPopulations(percentageToMate, numberOfParents);
        // 3: 'Mate' the best, choosing buildings randomly from each
        List<UrbanMap> childPopulation = mateParents(parentPopulation, numberOfChildren);
        // 4: Generate some x new random maps (Whatever is left over from
        // percentagToMate)
        int newPopulationSize = (int) ((int) populationSize * (1 - percentageToMate));
        population.addAll(generateRandomPopulation(initMap, newPopulationSize));
        // 5: Repeat z (numGenerations) times

        return this.population.get(0);
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
    private List<UrbanMap> chooseBestPopulations(float percentageToMate, int numToKeep) {
        // Sort the population
        Collections.sort(population);
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
