package Map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.opencsv.CSVReader;

import Buildings.BuildingTile;
import Buildings.BuildingType;
import Buildings.CommercialTile;
import Buildings.IndustrialTile;
import Buildings.NoBuildingTile;
import Buildings.ResidentialTile;

public class UrbanMap implements Comparable<UrbanMap>
{
    // max allowed industrial
    public int maxIndustrial;

    // max commercial
    public int maxCommercial;

    // max residential
    public int maxResidential;

    // map width and height
    public int mapWidth;
    public int mapHeight;

    // Terrain 2D Array (changed to nested arraylist, easier to populate)
    private ArrayList<ArrayList<Terrain>> terrain;

    /* Map Constructor */
    public UrbanMap(String mapFileToParse){
        // Right now takes a string with all the terrain. May want to update to be a different input type?
        this.generateMapTerrain(mapFileToParse);
    }

    // Constructor for new maps created from origional map
    public UrbanMap(UrbanMap originalMap) {
        this.terrain = originalMap.terrain;
        this.mapWidth = originalMap.mapWidth;
        this.mapHeight = originalMap.mapHeight;
        this.maxResidential = originalMap.maxResidential;
        this.maxCommercial = originalMap.maxCommercial;
        this.maxIndustrial = originalMap.maxIndustrial;
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
                        this.maxIndustrial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 1) // parse max commercial
                        this.maxCommercial = Integer.parseInt(line.get(0));
                    else if (lineIndex == 2) // parse max residential
                        this.maxResidential = Integer.parseInt(line.get(0));
                    else { // parse map
                        ArrayList<Terrain> lineOfTerrain = new ArrayList<>();

                        this.mapWidth = line.size();    // Set mapWidth

                        // go through everything in the line and create terrain
                        for (int colIndex = 0; colIndex < mapWidth; colIndex++) {
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
            this.mapHeight = (lineIndex - 3);
        }
        catch (FileNotFoundException e) { // handle FileNotFoundException
            System.out.println(e.getMessage());
        }

        // done parsing map!!
    }

    /*
    * setBuildingsOnMap()
    * Places buildings on map randomly.
    * There are a max number of industrial/residential/commercial zones in each input file.
    */
    public UrbanMap randomBuildingsMap(){

        // Randomly set the number of each building to place
        Random r = new Random();
        int numIndustrial = 1;//r.nextInt(this.maxIndustrial+1);
        int numResidential = 1;//r.nextInt(this.maxResidential+1);
        int numCommercial = 1;//r.nextInt(this.maxCommercial+1);

        // Initialize all terrain to have NoBuildingTile
        for(int row = 0; row < this.terrain.size(); row++){
            for(int col = 0; col < this.terrain.get(row).size(); col++){
                terrain.get(row).get(col).setBuilding(new NoBuildingTile());
            }
        }

        // For the buildings, go through and add a building.
        int maxBuildings = Math.max(Math.max(numIndustrial, numCommercial), numResidential);
        for(int nextBuilding = 0; nextBuilding < maxBuildings; nextBuilding++){

            if(numCommercial > 0){
                setBuildingRandomly(new CommercialTile(), this.mapWidth, this.mapHeight, r);
                numCommercial--;
            }
            if(numIndustrial > 0){
                setBuildingRandomly(new IndustrialTile(), this.mapWidth, this.mapHeight, r);
                numIndustrial--;
            }
            if(numResidential > 0){
                setBuildingRandomly(new ResidentialTile(), this.mapWidth, this.mapHeight, r);
                numResidential--;
            }
        }
        // This is the modified map - it will be reset the next time randomBuildingsMap() is run.
        return this;     
    }

    /*
    * setBuildingRandomly()
    * Sets this building type in a random location on the map.
    */
    private void setBuildingRandomly(BuildingTile building, int mapWidth, int mapHeight, Random r){
        int randRow;
        int randCol;

        // We will try to place the tile 6 times. After that, we will give up.
        int numTries = 0;
        while(numTries < 6){

            randRow = r.nextInt(mapHeight);
            randCol = r.nextInt(mapWidth);

            if(this.terrain.get(randRow).get(randCol).getType() == TerrainType.TOXIC ||
                    this.terrain.get(randRow).get(randCol).building.getType() != BuildingType.EMPTY) {
                // Not a valid spot.
                numTries++;
            }
            else {
                // Valid Spot. Set building and exit while loop.
                this.terrain.get(randRow).get(randCol).setBuilding(new IndustrialTile());
                numTries = 6;
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

        //System.out.println(mapValue);
        
        return mapValue;
    }

    /*
    * getNeighbors(type, n, row, col)
    * Returns the number of neighbors of given type within n Manhattan distances
     */
    public int getNeighbors(String type, int n, int row, int col) {
        // Iterate trough the requisite terrain elements and check its type. Increment when correct type.
        TerrainType tType;
        BuildingType bType;
        if (type.equals("RESIDENTIAL")||type.equals("INDUSTRIAL")||type.equals("COMMERCIAL")) {
            tType = TerrainType.STANDARD;
            bType = BuildingType.valueOf(type);
        }
        else {
            tType = TerrainType.valueOf(type);
            bType = BuildingType.EMPTY;
        }
        int count = 0;
        for (int i = row - n; i <= row + n; i++) { //check horizontal tiles within n
            for (int j = row - n; j <= row + n; j++) { //check vertical tiles within n
                if (i >= 0 && i < terrain.size() && j >= 0 && j < terrain.get(0).size()) { //make sure i and j are valid indices
                    int manhattan = Math.abs(row - i) + Math.abs(col - j); //compute manhattan distance
                    if (type.equals("RESIDENTIAL")||type.equals("INDUSTRIAL")||type.equals("COMMERCIAL")) { //if checking for buildings terrain doesn't matter
                        if (manhattan <= n && terrain.get(i).get(j).building.getType() == bType);
                        count++; //if within distance and correct tile, increment
                    }
                    else { //otherwise need to check terrain and building
                        if (manhattan <= n && terrain.get(i).get(j).getType() == tType && terrain.get(i).get(j).building.getType() == bType);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Set the terrain at the row,col coordinates to the terrain of the given mapo
    public void setTerrain(int row, int col, UrbanMap replacementMap) {
        this.terrain.get(row).get(col).building = terrain.get(row).get(col).building;
    }

    // Overides the toString() method. Prints the buildings and value of the map.
    @Override
    public String toString(){
        String result = "";

        for(int row = 0; row < this.mapHeight; row++){
            for(int col = 0; col < this.mapWidth; col++){
                result += this.terrain.get(row).get(col).building.getShortName();
            }
            result += "\n";
        }

        result += "\n\n";
        result += ("Value: " + this.getValueOfMap());
        result += "\n";
        return result;
    }

    // returns the map in a format that can be printed to the final CSV file
    public ArrayList<ArrayList<String>> getStringRepresentation() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        // TODO: need to implement

        return result;
    }

    // Allows for a map to be compared to another.
    @Override
    public int compareTo(UrbanMap compareMap) {
        return (this.getValueOfMap() < compareMap.getValueOfMap()) ? -1 : 1;
    }

	public void ensureSatisfiesBuildingCount(BuildingType buildingType, int maxBuildings){

        Random r = new Random();
        // Get the number of this building type on map
        int numBuildings = 0;
        for(int row = 0; row < this.mapHeight; row++){
            for(int col = 0; col < this.mapWidth; col++){
                if(this.terrain.get(row).get(col).building.getType() == buildingType){
                    numBuildings++;
                }
            }
        }

        // Will loop until the number of buildings is a satisfactory amount
        boolean satisfiesMaxBuildings = false;
        while(!satisfiesMaxBuildings){

            if(numBuildings <= maxBuildings){
                // All set, the building count is ok
                satisfiesMaxBuildings = true;
            }
            else{
                // Too many buildings. Need to delete a building and check if it satisfies.
                int row = 0;
                int col = 0;
                boolean deletedBuilding = false;
                while(!deletedBuilding){

                    // These will be the starting coordinates. Once it reaches a correct building tile, it deletes it and quits.
                    row = r.nextInt(this.mapHeight);
                    col = r.nextInt(this.mapWidth);

                    if(this.terrain.get(row).get(col).building.getType() == buildingType) {
                        this.terrain.get(row).get(col).setBuilding(new NoBuildingTile());
                        // We've deleted a building, now we can leave the loop and deceremented the number of this building types on the map.
                        deletedBuilding = true;
                        numBuildings--;
                    }

                    row++;
                    col++;
                    if(row >= this.mapHeight){
                        row = 0;
                    }
                    if(col >= this.mapWidth){
                        col = 0;
                    }
                }
            }

        }
    }
}
