package dto;

import enums.TypeRoom;

import java.time.LocalDate;

/**
 * Created by ppolozhe on 5/26/19.
 */
public class JdbcDto {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private TypeRoom typeRoom;

    public static class Builder {
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private TypeRoom typeRoom;

        public Builder setDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder setDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public Builder setTypeRoom(TypeRoom typeRoom) {
            this.typeRoom = typeRoom;
            return this;
        }

        public JdbcDto build(){
            JdbcDto dto = new JdbcDto();
            dto.setDateIn(dateFrom);
            dto.setDateOut(dateTo);
            dto.setTypeRoom(typeRoom);
            return dto;

        }
    }

    public LocalDate getDateIn() {
        return dateFrom;
    }

    public void setDateIn(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateOut() {
        return dateTo;
    }

    public void setDateOut(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }


}
