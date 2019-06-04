package ppolozhe.dao;

import ppolozhe.modelEntity.Bill;
import ppolozhe.modelEntity.Booking;

import java.util.Optional;

public interface BillDaoInterface extends CrudDao<Bill> {

    Optional<Bill> findByBooking(Booking booking) throws Exception;
}
