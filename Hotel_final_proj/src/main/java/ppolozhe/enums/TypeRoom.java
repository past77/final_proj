package ppolozhe.enums;

public enum TypeRoom {
    ECONOMY, STANDART, LUX;

    @Override
    public String toString() {
        return name().toLowerCase() ;
    }
}