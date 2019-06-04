package ppolozhe.enums;

public enum Status {
    PROCESSED,
    CONFIRMED,
    REJECTED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
