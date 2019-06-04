package ppolozhe.dao;

import ppolozhe.modelEntity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingDaoInterface extends CrudDao<Booking> {
    Optional<Booking> findByNumber(String number) throws Exception;
    List<Booking> getByUserId(int userId) throws Exception;
   // List<Booking> getByRoomId(int roomId) throws Exception;
    List<Booking> findProcessedBooking(int firstRecord, int recordsPerPage) throws Exception;
    public int getNumberOfPagesForProcessedBookings() throws Exception;
    //List<Room> findFreeApartments(BookingDto bookingDto();
    Optional<Booking> findByBooking(Booking booking) throws Exception;
}
