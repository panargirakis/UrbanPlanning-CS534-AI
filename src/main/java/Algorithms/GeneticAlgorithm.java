package Algorithms;

import java.util.HashMap;
import java.util.Map;

import Map.UrbanMap;

public class GeneticAlgorithm {

    // A HashMap containing the population of maps
    Map population;

    public GeneticAlgorithm(){
        population = new HashMap<UrbanMap>();
    }


    public UrbanMap runGeneticAlgorithm(UrbanMap initMap, int populationSize, float percentageToMate){

        // 1: Create x random maps with random buildings
        population.add(generateRandomPopulation(initMap, populationSize));
        // 2: Choose the best x% of them
        chooseBestPopulations(percentageToMate);
        // 3: 'Mate' the best, choosing buildings randomly from each
        mateMaps();
        // 4: Generate some x new random maps (Whatever is left over from percentagToMate)
        population.add(generateRandomPopulation(initMap, populationSize*(1-percentageToMate)));
        // 5: Repeat z times

        return this.population;
    }

    public Map<UrbanMap> generateRandomPopulation(UrbanMap initMap, int populationSize){
        
        Map randomPopulation = new HashMap<UrbanMap>();

        for(int i = 0; i < populationSize; i++){
            randomPopulation.add(initMap.generateRandomBuildings());
        }

        return randomPopulation;
    }

    public void chooseBestPopulations(float percentageToMate){
        //return population;
    }

    public void mateMaps(){
        //return population;
    }

}
