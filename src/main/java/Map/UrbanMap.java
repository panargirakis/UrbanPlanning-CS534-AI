package Map;

import com.opencsv.CSVReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Map.Terrain;
import Buildings.ResidentialTile;

public class UrbanMap
{
    // max allowed industrial
    int maxIndustrial;

    // max commercial
    int maxCommercial;

    // max residential
    int maxResidential;

    // Terrain 2D Array (changed to nested arraylist, easier to populate)
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
        this.terrain = new ArrayList<ArrayList<Terrain>>();

        try {
            CSVReader csvReader = new CSVReader(new FileReader(mapFileToParse));
            String[] values = null;
            int lineIndex = 0;
            try {
                while ((values = csvReader.readNext()) != null) {
                    List<String> line = Arrays.asList(values);
                    System.out.println(line);

                    if (lineIndex == 0) // parse max industrial
                        maxIndustrial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 1) // parse max commercial
                        maxCommercial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 2) // parse max residential
                        maxResidential = Integer.parseInt(line.get(0));
                    else { // parse map
                        ArrayList<Terrain> lineOfTerrain = new ArrayList<>();

                        // go through everything in the line and create terrain
                        for (int colIndex = 0; colIndex < line.size(); colIndex++) {
                            lineOfTerrain.add(new Terrain(line.get(colIndex)));
                        }
                        this.terrain.add(lineOfTerrain); // add populated line to terrain
                    }

                    lineIndex++;
                }
            }
            catch (IOException e) { // handle IO exception from readNext()
                System.out.println(e.getMessage());
            }
        }
        catch (FileNotFoundException e) { // handle FileNotFoundException
            System.out.println(e.getMessage());
        }

        // done parsing map!!
    }

    /*
    * setBuildingsOnMap()
    * Places buildings on map randomly.
    * There are a max number of industrial/residential/commercial zones in each inpout file.
    */
    public void setBuildingsOnMapRandomly(int maxIndustrial, int maxResidential, int maxCommercial){
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
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - i); //compute manhattan distance
                    if (manhattan <= n && terrain.get(i).get(j).building.getType().equals("toxic"))
                        count++; //if within distance and correct tile, increment
                }
            }
        }
        return count;
    }

    /*
    * getScenicNeighbors(row,col)
    * Returns the number of Scenic terrains within n Manhattan Distances.
    */
    public int getScenicNeighbors(int n, int row, int col){
        //STILL NEED TO ACCOUNT FOR IF BUILDING ON TOP OF SCENIC VIEW
        // Iterate trough the requisite terrain elements and check its type. Increment when Scenic.
        // UNLESS there is a building on the scenic view. Then, the view is destroyed and it is no longer scenic.
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - i); //compute manhattan distance
                    if (manhattan <= n && terrain.get(i).get(j).building.getType().equals("scenic")) count++; //if within distance and correct tile, increment
                }
            }
        }
        return count;
    }

    /*
    * getIndustrialNeighbors(n,row,col)
    * Returns the number of Industrial Building Tiles within n Manhattan Distances.
    */
    public int getIndustrialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Industrial.
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - i); //compute manhattan distance
                    if (manhattan <= n && terrain.get(i).get(j).building.getType().equals("industrial")) count++; //if within distance and correct tile, increment
                }
            }
        }
        return count;
    }

    /*
    * getResidentialNeighbors(n,row,col)
    * Returns the number of Residential Building Tiles within n Manhattan Distances.
    */
    public int getResidentialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Residential.
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - i); //compute manhattan distance
                    if (manhattan <= n && terrain.get(i).get(j).building.getType().equals("residential")) count++; //if within distance and correct tile, increment
                }
            }
        }
        return count;
    }

    /*
    * getCommercialNeighbors(n,row,col)
    * Returns the number of Commercial Building Tiles within n Manhattan Distances.
    */
    public int getCommercialNeighbors(int n, int row, int col){

        // Iterate trough the requisite terrain elements and check the building type on it. Increment when Commercial.
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - i); //compute manhattan distance
                    if (manhattan <= n && terrain.get(i).get(j).building.getType().equals("commercial")) count++; //if within distance and correct tile, increment
                }
            }
        }
        return count;
    }

}
