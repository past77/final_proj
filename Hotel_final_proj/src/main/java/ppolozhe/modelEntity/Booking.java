package ppolozhe.modelEntity;

import ppolozhe.enums.Status;
import ppolozhe.enums.TypeRoom;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Booking {
    private int id;
    private User user;
    private Room room;
    private Bill bill;
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
    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    public void setDateIn(Timestamp dateIn) {
        this.dateIn = dateIn.toLocalDateTime().toLocalDate();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", bill=" + bill +
                ", dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                ", statusEnum=" + statusEnum +
                ", typeRoom=" + typeRoom +
                '}';
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
        private Status statusEnum;
        private int id;
        private User user;
        private Room room;
        private Bill bill;
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
            this.room = room;
            return this;
        }

        public Builder setBill(Bill bill) {
            this.bill = bill;
            return this;
        }


        public Builder setDateIn(Timestamp dateFrom) {
            this.dateIn = dateFrom.toLocalDateTime().toLocalDate();
            return this;
        }

        public Builder setDateOut(Timestamp dateTo) {
            this.dateOut = dateTo.toLocalDateTime().toLocalDate();
            return this;
        }

        public Builder setStatus(Status statusEnum) {
            this.statusEnum = statusEnum;
            return this;
        }

        public Builder setRoomType(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
        return this;
    }


        public Builder setDateIn(LocalDate dateIn) {
            this.dateIn = dateIn;
            return this;
        }

        public Builder setDateOut(LocalDate dateTo) {
           this.dateOut = dateTo;
            return this;
        }

        public Booking build() {
            Booking booking = new Booking();
                    booking.setId(id);
                    booking.setRoom(room);
                    booking.setStatus(statusEnum);
                    booking.setBill(bill);
                    booking.setDateIn(dateIn);
                    booking.setDateOut(dateOut);
                    booking.setUser(user);
                    booking.setRoomType(typeRoom);
            return booking;
        }
    }
}