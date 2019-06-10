package ppolozhe.service;

import org.junit.Test;
import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.enums.Status;
import ppolozhe.enums.StatusUser;
import ppolozhe.enums.TypeRoom;
import ppolozhe.jdbcDaoRealization.JdbcBookingDao;
import ppolozhe.jdbcDaoRealization.JdbcRoomDao;
import ppolozhe.modelEntity.Accounts;
import ppolozhe.modelEntity.Booking;
import ppolozhe.modelEntity.Room;
import ppolozhe.modelEntity.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BookingServiceTest {

    private DaoFactory daoFactory;
    private ConnectionToDatabase connectionToDatabase;
    private BookingService bookingService;
    private JdbcBookingDao bookingDao;

    private void init(){
        daoFactory = mock(DaoFactory.class);
        connectionToDatabase = mock(ConnectionToDatabase.class);
        bookingDao = mock(JdbcBookingDao.class);
        bookingService = new BookingService(connectionToDatabase, daoFactory);
    }

    private Accounts buildAccounts() {
        return new Accounts.Builder()
                .setId(1)
                .setLogin("user@gmail.com")
                .setPassword("password")
                .setStatusUser(StatusUser.USER)
                .build();
    }


    private User buildUsers() {
        Accounts accounts = buildAccounts();
        return    new User.Builder()
                .setAccounts(accounts)
                .setFirstName("Andrew")
                .setSurname("Balaban")
                .setPhoneNumber("+390986765648")
                .build();
    }

    private Booking buildBooking() {
        User user = buildUsers();
        return new Booking.Builder()
                .setId(1)
                .setDateIn(LocalDate.now())
                .setDateOut(LocalDate.now().plusDays(1))
                .setUser(user)
                .setRoomType(TypeRoom.STANDART)
                .setStatus(Status.CONFIRMED)
                .build();
    }

    private List<Booking> buildBookings() {
        return Arrays.asList(buildBooking(), buildBooking());
    }

    private void stubDaoFactory() {
        when(daoFactory.getBookingDao()).thenReturn(bookingDao);
    }

    private void stubFindByUser(List<Booking> bookings) throws Exception {
        stubDaoFactory();
        when(bookingDao.getByUserId(1)).thenReturn(bookings);
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Booking booking = buildBooking();
        init();
        stubDaoFactory();

        bookingService.create(booking);

        verify(daoFactory).getBookingDao();
        verify(bookingDao).create(booking);
    }

    @Test
    public void testByUser() throws Exception {
        List<Booking> bookings = buildBookings();
        init();
        stubFindByUser(bookings);

        List<Booking> book = bookingService.findByUser(1);

        assertEquals(bookings, book);
        verify(daoFactory).getBookingDao();
        verify(bookingDao).getByUserId(1);
    }

}