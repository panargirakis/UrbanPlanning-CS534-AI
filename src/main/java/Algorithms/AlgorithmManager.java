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
            int numGenerations = 200;
            int generationSize = 100;
            int numChildren = 30;
            int numParents = 70; 
            int numNew = 40; // Add new random maps to the simulation (Remainder after parents and children)
            UrbanMap bestMap = GeneticAlgorithm.runGeneticAlgorithm(this.map, numGenerations, generationSize, numChildren, numParents, numNew);

            System.out.println("FINAL MAP:\n");
            System.out.println(bestMap);
        }

    }

    public Metrics getMetrics() {
        return new Metrics(0, 0, map); // TODO: return the actual metrics from the correct algorithm
    }

}
