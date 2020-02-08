package Map;

import Buildings.BuildingTile;

public class Terrain
{

    // types: "Toxic", "Scenic", "Standard"
    String type; // Enum?
    BuildingTile building;
    int difficulty;

    /* Terrain Constructor */
    public Terrain(String type, int difficulty){
        this.type = type;
        //this.building = building; - I think for generating the terrain, we should not be initializing the building. Rather, do it in setBuilding() to be called when the buildings are actually placed.
        this.difficulty = difficulty;
    }

    /*
    * getValue()
    * Returns the value of this specific terrain tile based on buildings on it and neighboring tiles.
    */
	public int getValue(int toxicNeighbors, int scenicNeighbors, int industrialNeighbors, int residentialNeighbors,int commercialNeighbors) {

        int value = this.building.getValueOfBuilding();

        // Each Square has its own dfficulty which is a penalty on the value.
        value -= difficulty;

		return value;
    }

    /*
    * setBuilding()
    * Returns the value of this specific terrain tile based on buildings on it and neighboring tiles.
    */
	public void setBuilding(BuildingTile building) {
        this.building = building;
	}
    
}