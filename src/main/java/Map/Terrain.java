package Map;

import Buildings.BuildingTile;
import Buildings.BuildingType;
import Buildings.NoBuildingTile;

enum TerrainType {
    TOXIC, SCENIC, STANDARD;
}

public class Terrain
{

    /*
    * X:  former toxic waste site.  Industrial zones within 2 tiles take a penalty of -10.  Commercial and residential zones within 2 tiles take a penalty of -20.  You cannot build directly on a toxic waste site.
    * S:  scenic view.  Residential zones within 2 tiles gain a bonus of 10 points.  If you wish, you can build on a scenic site but it destroys the view.  Building on a scenic view has a cost of 1.
    * 1...9:  how difficult it is to build on that square.  To build a zone on any square costs 2+difficulty.  So building a Commercial zone on a square of difficulty 6 costs 8 points.  You will receive a penalty of that many points to put any zone on that square.  
    */

    // types: "Toxic", "Scenic", "Standard"
    TerrainType type;
    BuildingTile building;
    int difficulty;

    /* Terrain Constructor */
    public Terrain(String rawType) {
        try {
            this.difficulty = Integer.parseInt(rawType);
            this.type = TerrainType.STANDARD;
            this.building = new NoBuildingTile();
        }
        catch (NumberFormatException e) {
            if (rawType.equals("X"))
                this.type = TerrainType.TOXIC;
            else if (rawType.equals("S"))
                this.type = TerrainType.SCENIC;
            this.difficulty = 9999;
            this.building = new NoBuildingTile();
        }
    }

    /*
    * setBuilding()
    * Sets the given building on this tile.
    */
	public void setBuilding(BuildingTile building) {
        this.building = building;
	}

    /*
    * getValue()
    * Returns the value of this specific terrain tile based on buildings on it and neighboring tiles.
    */
	public int getValue(UrbanMap map, int row, int col) {

        return this.building.getValueOfBuilding(map, row, col, this.difficulty);

    }

    /*
    * getType()
    * Returns the type of this tile (Scenic/Toxic/Standard)
    * If there is a building tile on a scenic tile, this function will return "standard"
    */
	public TerrainType getType() {
	    if (this.type == TerrainType.SCENIC && this.building.getType() != BuildingType.EMPTY)
	        return TerrainType.STANDARD;
        else
            return this.type;
    }

    public BuildingType getBuildingType() {
	    return this.building.getType();
    }

}
