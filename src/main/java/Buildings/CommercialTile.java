package Buildings;

import Map.UrbanMap;
import Buildings.BuildingTile;

public class CommercialTile implements BuildingTile
{

    public CommercialTile(){}

    @Override
    public int getValueOfBuilding(UrbanMap map, int row, int col, int difficulty) {

        int buildingValue = 0;

        // Commercial zones within 2 tiles of Toxic Waste take a penalty of -20.
        buildingValue += map.getNeighbors("TOXIC", 2, row, col) * -20;
        // For each residential tile within 3 squares of commercial, there is a bonus of 4 points.
        buildingValue += map.getNeighbors("RESIDENTIAL",3, row, col) * 4;
        // For each commercial site with 2 squares of commercial, there is a penalty of 4 points.
        buildingValue += map.getNeighbors("COMMERCIAL", 2, row, col) * -4;
        // For every building built, there is a difficulty of 2 as well as a difficulty for that square.
        buildingValue -= (2 + difficulty);

        return buildingValue;
    }

    @Override
    public BuildingType getType() {
        return BuildingType.COMMERCIAL;
    }

    @Override
    public String getShortName() {
        return "C";
    }

}
