package Buildings;

public enum BuildingType {
    RESIDENTIAL("residential", "R"), COMMERCIAL("commercial", "C"),
    INDUSTRIAL("industrial", "I"), EMPTY("empty", "#");

    private final String name;
    private final String symbol;

    BuildingType(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
