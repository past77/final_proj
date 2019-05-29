package modelEntity;

import enums.Status;
import enums.TypeRoom;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Booking {
    private int id;
    private User user;
    private Room room;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private Status statusEnum;
    private TypeRoom typeRoom;

    public Booking(){}

    public Booking(User user, Room room,Status statusEnum, LocalDate dateIn, LocalDate dateOut) {
        this.user = user;
        this.room = room;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.statusEnum = statusEnum;
    }

    public Timestamp getDateOut(Timestamp dateOut) {
        return dateOut;
    }

    public void setDateOut(Timestamp dateOut) {
        this.dateOut = dateOut.toLocalDateTime().toLocalDate();
    }

    public Timestamp getDateIn(Timestamp dateIn) {
        return dateIn;
    }

    public void setDateIn(Timestamp dateIn) {
        this.dateIn = dateIn.toLocalDateTime().toLocalDate();
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Status getStatus() {
        return statusEnum;
    }

    public void setStatus(Status status) {
        statusEnum = status;
    }
    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }
    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateTo) {
        this.dateOut = dateTo;
    }

    public void setRoomType(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }


    public static class Builder {
        private Booking newBooking;
        private Status statusEnum;
        private int id;
        private User user;
        private Room room;
        private LocalDate dateIn;
        private LocalDate dateOut;
        private TypeRoom typeRoom;


        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setRoom(Room room) {
            newBooking.room = room;
            return this;
        }


        public Builder setDateIn(Timestamp dateFrom) {
            newBooking.dateIn = dateFrom.toLocalDateTime().toLocalDate();
            return this;
        }

        public Builder setDateOut(Timestamp dateTo) {
            newBooking.dateOut = dateTo.toLocalDateTime().toLocalDate();
            return this;
        }

        public Builder setStatus(Status status) {
            statusEnum = status;
            return this;
        }

        public Builder setRoomType(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
        return this;
    }

//    public Builder setPersons(int persons) {
//        this.persons = persons;
//        return this;
//    }

        public Builder setDateIn(LocalDate dateFrom) {
            newBooking.dateIn = dateFrom;
            return this;
        }

        public Builder setDateOut(LocalDate dateTo) {
            newBooking.dateOut = dateTo;
            return this;
        }

        public Booking build() {
            Booking booking = new Booking();
                    booking.setId(id);
                    booking.setRoom(room);
                    booking.setStatus(statusEnum);
                    booking.setDateIn(dateIn);
                    booking.setDateOut(dateOut);
                    booking.setUser(newBooking.user);
                    booking.setRoomType(typeRoom);
            //booking.setPersons(persons);
            return booking;
        }
    }
}