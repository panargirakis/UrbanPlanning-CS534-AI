import Algorithms.AlgorithmManager;
import Algorithms.HillClimb;
import Algorithms.Metrics;
import IO.Options;
import IO.WriteResults;
import Map.UrbanMap;

public class Main
{
    // Assignment 1 Google Doc:
    // https://docs.google.com/document/d/1rH2Cmk5KzQ7_EN9-3wJejrr8PcwbdFwpYUoCDd6_OPU/edit
    public static void main(String[] args){

        // parameters are the file to read in, and the technique to use - HC (Hill Climbing) and GA (Genetic Algorithm)
        String inputFile = args[0];
        String algorithm = args[1];

        boolean isUsingHC;
        if (algorithm.equals("HC")) {
            isUsingHC = true;
        }
        else if (algorithm.equals("GA")) {
            isUsingHC = false;
        }
        else {
            System.out.print("Invalid algorithm name. Usage: plan {filename} {GA or HC}");
            return;
        }

        Options options = new Options(inputFile, isUsingHC);

        UrbanMap map = new UrbanMap(options.getFileToRead());

        AlgorithmManager solver = new AlgorithmManager(options, map);

        solver.run();
        Metrics result = solver.getMetrics();

        String resultsSavePath = "results.csv";
        try {
            WriteResults.writeResults(result, resultsSavePath);
            System.out.println("Results csv file written at resultsSavePath: " + resultsSavePath);
        }
        catch (Exception e) {
            System.out.println("Could not write results to csv file. An error occurred:");
            System.out.println(e.getMessage());
        }

    }
}
