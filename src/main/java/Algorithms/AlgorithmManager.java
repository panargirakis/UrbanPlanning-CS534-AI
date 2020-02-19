package Algorithms;

import IO.Options;
import Map.UrbanMap;

public class AlgorithmManager {
    UrbanMap map;
    GeneticAlgorithm geneticAlgorithm;
    HillClimb hillClimb;

    Options options;

    public AlgorithmManager(Options options, UrbanMap map) {
        this.options = options;
        this.map = map;

        this.geneticAlgorithm = new GeneticAlgorithm();
        this.hillClimb = new HillClimb();
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void run() {
        if (options.isUsingHillClimb()){
            // do something with hill climb
        }
        else{
            // Genetic Algorithm (These inputs can be tweaked)
            int numGenerations = 200;
            int generationSize = 100;
            int numChildren = 30;
            int numParents = 70; 
            int numNew = 40; // Add new random maps to the simulation (Remainder after parents and children)
            UrbanMap bestMap = GeneticAlgorithm.runGeneticAlgorithm(this.map, numGenerations, generationSize, numChildren, numParents, numNew);

            System.out.println("FINAL:");
            System.out.println(bestMap);
        }

    }

    public Metrics getMetrics() {
        return new Metrics(); // return the actual metrics from the correct algorithm
    }

}
