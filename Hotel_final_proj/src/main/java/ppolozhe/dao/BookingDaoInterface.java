package ppolozhe.dao;

import ppolozhe.modelEntity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingDaoInterface extends CrudDao<Booking> {
    Optional<Booking> findByNumber(String number) throws Exception;
    List<Booking> getByUserId(int userId) throws Exception;
    List<Booking> findProcessedBooking(int firstRecord, int recordsPerPage) throws Exception;
    int getNumberOfPagesForProcessedBookings() throws Exception;
    Optional<Booking> findByBooking(Booking booking) throws Exception;
}
