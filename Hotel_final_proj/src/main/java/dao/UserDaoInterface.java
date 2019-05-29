package dao;

import modelEntity.Booking;
import modelEntity.User;

import java.util.List;
import java.util.Optional;

public interface UserDaoInterface extends CrudDao<User> {
    List<User> findUserByFullName(String firstName, String lastName) throws Exception;
    Optional<User> findByBooking(Booking booking) throws Exception;
}
