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
        this.hillClimb = new HillClimb(map); //send through an unpopulated map
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void run() {
        if (options.isUsingHillClimb());
            // do something with hill climb
        else; // do something with GA
    }

    public Metrics getMetrics() {
        return new Metrics(0, 0, map); // TODO: return the actual metrics from the correct algorithm
    }

}
