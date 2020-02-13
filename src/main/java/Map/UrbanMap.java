package Map;

import Buildings.ResidentialTile;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrbanMap
{
    // max allowed industrial
    int maxIndustrial;

    // max commercial
    int maxCommercial;

    // max residential
    int maxResidential;

    // Terrain 2D Array
    private ArrayList<ArrayList<Terrain>> terrain;

    /* Map Constructor */
    public UrbanMap(String mapFileToParse){
        // Right now takes a string with all the terrain. May want to update to be a different input type?

        this.generateMapTerrain(mapFileToParse);
    }

    /*
    * generateMap(String mapFileToParse)
    * Takes a text file as a string (or other format) and generates the terrain layout.
    * May belong in IOModule - just have it here temporarily
    * This should only be called once per input file. Once the terrain has been set, our algorithms can only alter buildings, not terrain.
    */
    private void generateMapTerrain(String mapFileToParse){
        // Based on Width and Height of map (extraced from file):
        terrain = new ArrayList<ArrayList<Terrain>>();

        try {
            CSVReader csvReader = new CSVReader(new FileReader(mapFileToParse));
            String[] values = null;
            int lineIndex = 0;
            try {
                while ((values = csvReader.readNext()) != null) {
                    List<String> line = Arrays.asList(values);
                    System.out.println(line);

                    if (lineIndex == 0)
                        maxIndustrial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 1)
                        maxCommercial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 2)
                        maxResidential = Integer.parseInt(line.get(0));
                    else {
                        ArrayList<Terrain> lineOfTerrain = new ArrayList<>();
                        for (int colIndex = 0; colIndex < line.size(); colIndex++) {
                            lineOfTerrain.add(new Terrain(line.get(colIndex)));
                        }
                        terrain.add(lineOfTerrain);
                    }

                    lineIndex++;
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done parsing map");
    }

    /*
    * setBuildingsOnMap()
    * Places (random? Simulated Annealing? Genetic?) urban layout buildings on the map. To be used/implemented in algorithms.
    * Probably a temporary method - will need to be updated and changed based on the algorithm.
    */
    public void setBuildingsOnMap(){
        // Cannot build directly on a toxic waste site

        for(int row = 0; row < this.terrain.size(); row++){
            for(int col = 0; col < this.terrain.get(row).size(); col++){

                terrain.get(row).get(col).setBuilding(new ResidentialTile());

            }
        }

    }

    /*
    * getValueOfMap()
    * Returns the value of the map with the current urban layout.
    */
    public int getValueOfMap(){
        int mapValue = 0;

        //Iterate through every Terrain eleemnt of 2D array and check its value
        for(int row = 0; row < this.terrain.size(); row++){
            for(int col = 0; col < this.terrain.get(row).size(); col++){

                // Get the value of the current terrain and tile. Add to mapValue.
                mapValue += this.terrain.get(row).get(col).getValue(this, row, col);

            }
        }

        return mapValue;
    }

    /*
    * getToxicNeighbors(n,row,col)
    * Returns the number of Toxic terrains within n Manhattan Distances.
    */
    public int getToxicNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check its type. Increment when Toxic.

        return 0;
    }

    /*
    * getScenicNeighbors(row,col)
    * Returns the number of Scenic terrains within n Manhattan Distances.
    */
    public int getScenicNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check its type. Increment when Scenic.
        // UNLESS there is a building on the scenic view. Then, the view is destroyed and it is no longer scenic.

        return 0;
    }

    /*
    * getIndustrialNeighbors(n,row,col)
    * Returns the number of Industrial Building Tiles within n Manhattan Distances.
    */
    public int getIndustrialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Industrial.

        return 0;
    }

    /*
    * getResidentialNeighbors(n,row,col)
    * Returns the number of Residential Building Tiles within n Manhattan Distances.
    */
    public int getResidentialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Residential.

        return 0;
    }

    /*
    * getCommercialNeighbors(n,row,col)
    * Returns the number of Commercial Building Tiles within n Manhattan Distances.
    */
    public int getCommercialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Commercial.

        return 0;
    }

}
