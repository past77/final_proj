package ppolozhe.service;

import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.enums.Status;
import ppolozhe.modelEntity.Bill;
import ppolozhe.modelEntity.Booking;
import ppolozhe.modelEntity.Room;
import ppolozhe.modelEntity.User;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class BookingService {

    private ConnectionToDatabase connectionManager;
    private DaoFactory daoFactory;

    BookingService(ConnectionToDatabase connectionManager, DaoFactory daoFactory) {
        this.connectionManager = connectionManager;
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final BookingService INSTANCE = new BookingService(ConnectionToDatabase.getInstance(), DaoFactory.getInstance());
    }

    public static BookingService getInstance() {
        return Holder.INSTANCE;
    }

    public boolean update(Booking booking) throws Exception {
        return daoFactory.getBookingDao().update(booking);
    }

    public int getNumberOfPagesForProcessedBookings() throws Exception {
        int numberOfPages = daoFactory.getBookingDao().getNumberOfPagesForProcessedBookings();
        return numberOfPages;
    }

    public void update(List<Booking> bookings) throws Exception {
        for (Booking booking : bookings) {
            System.out.println("booking in BookingService: " + booking);
            if (booking.getStatus().equals(Status.CONFIRMED)) {
                updateConfirmed(booking);
            } else {
                update(booking);
            }
        }
    }

    private void updateConfirmed(Booking booking) throws Exception {
        Bill bill = createBill(booking);
        booking.setBill(bill);
        System.out.println("bill in updateConfirmed" + bill);
        try {
            connectionManager.startTransaction();
            daoFactory.getBillDao().create(bill);
            System.out.println("booking after createbill : " + booking);
            update(booking);
            connectionManager.commit();
        } catch (Exception e) {
            connectionManager.rollback();
            throw e;
        }
    }

    private Bill createBill(Booking booking) {
        long days = ChronoUnit.DAYS.between(booking.getDateIn(), booking.getDateOut());
        return new Bill.Builder()
                .setPrice((int) (days * booking.getRoom().getPrice()))
                .build();
    }

    public boolean create(Booking booking) throws Exception {
        boolean created;
        created = daoFactory.getBookingDao().create(booking);
        return created;
    }

    public boolean delete(int id) throws Exception {
        boolean deleted;
        deleted = daoFactory.getBookingDao().delete(id);
        return deleted;
    }


    public List<Booking> findByUser(int userId) throws Exception {
        List<Booking> bookings;
        bookings = daoFactory.getBookingDao().getByUserId(userId);
        return bookings;
    }

    public List<Booking> findProcessedBookings(int firstRecord, int recordsPerPage) throws Exception {
        List<Booking> bookings;
        bookings = daoFactory.getBookingDao().findProcessedBooking(firstRecord, recordsPerPage);
        for (Booking booking : bookings) {
            daoFactory.getBillDao().findByBooking(booking);
            Optional<Room> roomOptional = daoFactory.getRoomDao().findByBooking(booking);
            if(roomOptional.isPresent()){
                booking.setRoom(roomOptional.get());
            }
            Optional<User> userOptional = daoFactory.getUserDao().findByBooking(booking);
            if(userOptional.isPresent()){
                booking.setUser(userOptional.get());
            }
        }
        return bookings;
    }
}
