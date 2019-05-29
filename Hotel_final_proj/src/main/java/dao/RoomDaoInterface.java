package dao;

import dto.JdbcDto;
import modelEntity.Booking;
import modelEntity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDaoInterface extends CrudDao<Room> {
    Optional<Room> findByNumber(String number) throws Exception;

    List<Room> findFreeRooms(JdbcDto bookingDto) throws Exception;
    Optional<Room> findByBooking(Booking booking) throws Exception;
}
