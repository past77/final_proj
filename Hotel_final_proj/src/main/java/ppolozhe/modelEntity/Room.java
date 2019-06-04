package ppolozhe.modelEntity;

import ppolozhe.enums.TypeRoom;

public class Room {
    private int id;
    private int floor;
    private int number;
    private int roomCap;
    private int price;

    private TypeRoom typeRoom;

    public Room(){}

//    public Room(int id, int floor, int number, int roomCap, int price, String roomType) {
//        this.id = id;
//        this.floor = floor;
//        this.number = number;
//        this.roomCap = roomCap;
//        this.price = price;
//        this.typeRoom = typeRoom.roomType;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRoomCap() {
        return roomCap;
    }

    public void setRoomCap(int roomCap) {
        this.roomCap = roomCap;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TypeRoom getRoomType() {
        return typeRoom;
    }

    public void setRoomType(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }


    public static class Builder {
        private int id;
        private int capacity;
        private int price;
        private int number;
        private TypeRoom typeRoom;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRoomCap(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder setRoomType(TypeRoom typeRoom) {
            this.typeRoom = typeRoom;
            return this;
        }

        public Room build() {
            Room room = new Room();
            room.setId(id);
            room.setRoomCap(capacity);
            room.setPrice(price);
            room.setNumber(number);
            room.setRoomType(typeRoom);
            return room;
        }
    }
}
