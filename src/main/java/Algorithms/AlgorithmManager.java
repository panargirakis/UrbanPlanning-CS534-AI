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
            int numGenerations = 1;
            int generationSize = 5;
            int numChildren = 1; // Generate 30 new children
            int numParents = 2;  // Keep 10 parents
            int numNew = 4; // Add 10 new random maps to the simualtion (Remainder after parents and children)
            UrbanMap bestMap = this.geneticAlgorithm.runGeneticAlgorithm(this.map, numGenerations, generationSize, numChildren, numParents, numNew);

            System.out.println(bestMap);
        }

    }

    public Metrics getMetrics() {
        return new Metrics(); // return the actual metrics from the correct algorithm
    }

}
