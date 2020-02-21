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
        double decreaseRatio = 0.99; //ratio to decrease temperature by
        int maxRestart = 5; //maximum number of restarts
        int minWorseMoves = 100000; //minimum number of worse moves to reach before restarting
        this.hillClimb = new HillClimb(map, temperature, decreaseRatio, maxRestart, minWorseMoves); //send through an unpopulated map
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void run() {
        if (options.isUsingHillClimb()){
            // Hill Climb
            this.finalResult = hillClimb.runHillClimb();
        }
        else{
            // Genetic Algorithm (These inputs can be tweaked)
            int numGenerations = 5000;  // This is likely to be superceded by the 10 second time limit.
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

     public Metrics getMetrics() {
         return this.finalResult;
     }

}
