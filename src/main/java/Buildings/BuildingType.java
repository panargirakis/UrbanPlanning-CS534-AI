package Buildings;

public enum BuildingType {
    RESIDENTIAL("residential"), COMMERCIAL("commercial"), INDUSTRIAL("industrial");

    private final String name;

    BuildingType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
