package enums;

public enum StatusUser {
        ADMIN,
        USER;

        @Override
        public String toString() {
            return name().toLowerCase() ;
        }
    }
