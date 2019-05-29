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
    private int persons;

    public static class Builder {
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private TypeRoom typeRoom;
        private int persons;

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

        public Builder setPersons(int persons) {
            this.persons = persons;
            return this;
        }
        public JdbcDto build(){
            JdbcDto dto = new JdbcDto();
            dto.setDateFrom(dateFrom);
            dto.setDateTo(dateTo);
            dto.setTypeRoom(typeRoom);
            dto.setPersons(persons);
            return dto;

        }
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }
}
