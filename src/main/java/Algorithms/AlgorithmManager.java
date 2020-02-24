package Algorithms;

import IO.Options;
import Map.UrbanMap;

public class AlgorithmManager {
    UrbanMap map;
    GeneticAlgorithm geneticAlgorithm;
    HillClimb hillClimb;

    Options options;

    Metrics finalResult;

    public AlgorithmManager(Options options, UrbanMap map) {
        this.options = options;
        this.map = map;

        this.geneticAlgorithm = new GeneticAlgorithm();
        double temperature = 10; //starting temperature
        double decreaseRatio = 0.009; //ratio to decrease temperature by
        int maxRestart = 1000; //maximum number of restarts
        int minWorseMoves = 10000; //minimum number of worse moves to reach before restarting
        this.hillClimb = new HillClimb(map, temperature, decreaseRatio, maxRestart, minWorseMoves); //send through an unpopulated map
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void run() {
        if (options.isUsingHillClimb()){
            // Hill Climb
            this.finalResult = this.hillClimb.runHillClimb();
        }
        else{
            // Genetic Algorithm (These inputs can be tweaked)
            int numGenerations = 50000;  // This is likely to be superceded by the 10 second time limit.
            int generationSizeElitism = 200; // ELITISM PARAMETER - Limits the size of the generations by choosing the best of them.
            int numParents = 100; // The number of parents to choose from a generation.
            int numChildren = 200; // The number of children a generation should create.
            int numNew = 100; // Add new additional random maps to the simulation (new 'Genes').
            int mutationChance = 5; // there is a 1/mutationChance chance that children will have some mutation.
            long millisecondsToRun = 100; // Run for X Milliseconds
            this.finalResult = this.geneticAlgorithm.runGeneticAlgorithm(this.map, numGenerations, generationSizeElitism, numParents, numChildren, numNew, mutationChance, millisecondsToRun);
        }

    }

     public Metrics getMetrics() {
         return this.finalResult;
     }

}
