package ppolozhe.dto;

import ppolozhe.enums.TypeRoom;

import java.time.LocalDate;

/**
 * Created by ppolozhe on 5/26/19.
 */
public class JdbcDto {
    private LocalDate dateIn;
    private LocalDate dateOut;
    private TypeRoom typeRoom;

    public static class Builder {
        private LocalDate dateIn;
        private LocalDate dateOut;
        private TypeRoom typeRoom;

        public Builder setDateIn(LocalDate dateIn) {
            this.dateIn = dateIn;
            return this;
        }

        public Builder setDateOut(LocalDate dateTo) {
            this.dateOut = dateTo;
            return this;
        }

        public Builder setTypeRoom(TypeRoom typeRoom) {
            this.typeRoom = typeRoom;
            return this;
        }

        public JdbcDto build(){
            JdbcDto dto = new JdbcDto();
            dto.setDateIn(dateIn);
            dto.setDateOut(dateOut);
            dto.setTypeRoom(typeRoom);
            return dto;

        }
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateFrom) {
        this.dateIn = dateFrom;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateTo) {
        this.dateOut = dateTo;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }
}
