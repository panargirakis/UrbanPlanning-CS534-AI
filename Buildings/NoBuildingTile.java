package Buildings;

import Map.UrbanMap;

public class NoBuildingTile implements BuildingTile
{

    public NoBuildingTile(){}

    @Override
    public int getValueOfBuilding(UrbanMap map, int row, int col, int difficulty) {

        // There is no penalty or bonus value for not building on a tile.
        return 0;
    }
    
}