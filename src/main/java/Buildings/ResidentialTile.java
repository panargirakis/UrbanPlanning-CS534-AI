package Buildings;

import Map.UrbanMap;
import Map.Terrain;
import Buildings.BuildingTile;

public class ResidentialTile implements BuildingTile
{

    public ResidentialTile(){}

    @Override
    public int getValueOfBuilding(UrbanMap map, int row, int col, int difficulty) {

        int buildingValue = 0;

        // Residential zones within 2 tiles of a Scenic View gain a bonus of 10 points.
        buildingValue += map.getNeighbors("SCENIC", 2, row, col) * 10;
        // Residential zones within 2 tiles of a Toxic Zone take a penalty of -20.
        buildingValue += map.getNeighbors("TOXIC",2, row, col) * -20;
        // For each industrial site within 3 squares there is a penalty of 5 points.
        buildingValue += map.getNeighbors("INDUSTRIAL", 3, row, col) * -5;
        // For each commercial site with 3 squares there is a bonus of 4 points.
        buildingValue += map.getNeighbors("COMMERCIAL",3, row, col) * 4;
        // For every building built, there is a difficulty of 2 as well as a difficulty for that square.
        buildingValue -= (2 + difficulty);

        return buildingValue;
    }

    @Override
    public BuildingType getType() {
        return BuildingType.RESIDENTIAL;
    }

}
