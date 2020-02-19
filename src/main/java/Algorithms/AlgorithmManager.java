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
        int temperature = 5;
        double decreaseRatio = 0.01;
        int maxRestart = 5;
        int maxWorseMoves = 100;
        this.hillClimb = new HillClimb(map, temperature, decreaseRatio, maxRestart, maxWorseMoves); //send through an unpopulated map
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void run() {
        if (options.isUsingHillClimb()){
            // Hill Climb
            hillClimb.runHillClimb();
        }
        else{
            // Genetic Algorithm (These inputs can be tweaked)
            int numGenerations = 500;
            int generationSize = 500;
            int numChildren = 200;
            int numParents = 200; 
            int numNew = 100; // Add new random maps to the simulation (Remainder after parents and children)
            int mutationChance = 5; // there is a 1/mutationChance chance that the child will have some mutation.
            UrbanMap bestMap = GeneticAlgorithm.runGeneticAlgorithm(this.map, numGenerations, generationSize, numChildren, numParents, numNew, mutationChance);

            System.out.println("FINAL MAP:\n");
            System.out.println(bestMap);
        }

    }

    // public Metrics getMetrics() {
    //     return new Metrics(0, 0, map); // TODO: return the actual metrics from the correct algorithm
    // }

}
