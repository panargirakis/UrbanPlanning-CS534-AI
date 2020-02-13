import IO.Options;
import Map.UrbanMap;

public class Main
{
    // Assignment 1 Google Doc:
    // https://docs.google.com/document/d/1rH2Cmk5KzQ7_EN9-3wJejrr8PcwbdFwpYUoCDd6_OPU/edit
    public static void main(String[] args){

        // parameters are the file to read in, and the technique to use - HC and GA
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
    }
}
