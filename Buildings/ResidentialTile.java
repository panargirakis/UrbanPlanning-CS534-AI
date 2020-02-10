package Buildings;

public  class ResidentialTile implements BuildingTile
{

    // For each industrial site within 3 squares there is a penalty of 5 points.
    // For each commercial site with 3 squares there is a bonus of 4 points.

    // Residential zones within 2 tiles of a Scenic View gain a bonus of 10 points.
    // Residential zones within 2 tiles of a Toxic Zone take a penalty of -20.




    public ResidentialTile(){
    }

    @Override
    public int getValueOfBuilding(int scenicNeighbors, int toxicNeighbors, int industrialNeighborsThree, int commercialNeighborsThree) {

        int buildingValue = 0;
        buildingValue += scenicNeighbors * 10;
        buildingValue += toxicNeighbors * -20;
        // Need to account for Industrial Sites within 3 titles
        buildingValue += industrialNeighborsThree * -5;
        // Need to account for Commercial Sites within 3 tiles
        buildingValue += commercialNeighborsThree * 4;

        return buildingValue;
    }
    
}