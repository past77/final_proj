package enums;

public enum TypeRoom {
    ECONOMY, STANDARD, LUX;

    @Override
    public String toString() {
        return name().toLowerCase() ;
    }
}