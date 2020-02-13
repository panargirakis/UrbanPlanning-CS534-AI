package src.main.java.Buildings;

import src.main.java.Map.UrbanMap;

public class IndustrialTile implements BuildingTile
{

    public IndustrialTile(){}

    @Override
    public int getValueOfBuilding(UrbanMap map, int row, int col, int difficulty) {

        int buildingValue = 0;

        // Industrial zones within 2 tiles of a Toxic Zone take a penalty of -10.
        buildingValue += map.getToxicNeighbors(2, row, col) * -10;
        // For each industrial tile within 2 squares of idustry, there is a bonus of 2 points.
        buildingValue += map.getIndustrialNeighbors(2, row, col) * 2;
        // For every building built, there is a difficulty of 2 as well as a difficulty for that square.
        buildingValue -= (2 + difficulty);

        return buildingValue;
    }
    
}
