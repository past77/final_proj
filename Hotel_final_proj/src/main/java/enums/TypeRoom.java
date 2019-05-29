package enums;

public enum TypeRoom {
    ECONOM, STANDART, LUX;

    @Override
    public String toString() {
        return name().toLowerCase() ;
    }
}