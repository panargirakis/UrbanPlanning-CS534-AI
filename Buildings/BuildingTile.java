package Buildings;

public interface BuildingTile
{
    
    /*
    * INDUSTRIAL: For each industrial tile within 2 squares of idustry, there is a bonus of 2 points.
    * COMMERCIAL: For each residential tile within 3 squares of commercial, there is a bonus of 4 points.
    * COMMERCIAL: For each commercial site with 2 squares of commercial, there is a penalty of 4 points.
    * RESIDENTIAL: For each industrial site within 3 squares of Residential, there is a penalty of 5 points.
    * RESIDENTIAL: For each commercial site with 3 squares of residential there is a bonus of 4 points.
    */

    /*
    * getValueOfBuilding()
    * Returns the value of this building based on surrounding tiles.
    */
    public int getValueOfBuilding(int scenicNeighbors, int toxicNeighbors, int industrialWithinTwoSquares, int industrialWithinThreeSquares, int residentialWithinThreeSquares, int commercialWithinTwoSquares, int commercialWithinThreeSquares);

}