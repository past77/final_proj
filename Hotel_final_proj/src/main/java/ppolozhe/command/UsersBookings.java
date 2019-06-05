package ppolozhe.command;

import org.apache.log4j.Logger;
import ppolozhe.enums.Status;
import ppolozhe.modelEntity.Bill;
import ppolozhe.modelEntity.Booking;
import ppolozhe.modelEntity.User;
import ppolozhe.service.BillService;
import ppolozhe.service.BookingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class UsersBookings implements Command{

    private static final Logger LOGGER = Logger.getLogger(UsersBookings.class);
    private BookingService bookingService;
    private BillService billService;

    UsersBookings(BookingService bookingService, BillService billService) {
        this.bookingService = bookingService;
        this.billService = billService;
    }

    private static class Holder {
        static final UsersBookings INSTANCE = new UsersBookings(BookingService.getInstance(), BillService.getInstance());
    }

    public static UsersBookings getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Booking> bookings = createBookings(request);

        LOGGER.info("bookings: " + bookings);
        request.setAttribute("bookings", bookings);
        return "usersBooking";
    }

    private List<Booking> createBookings(HttpServletRequest request) throws Exception {
        List<Booking> bookings = bookingService.findByUser(((User) request.getSession().getAttribute("user")).getAccounts().getId());

        for (Booking booking: bookings) {
            if(booking.getStatus().equals(Status.CONFIRMED)){
                Optional<Bill> bill = billService.findByBooking(booking);
                if(bill.isPresent()) {
                    booking.setBill(bill.get());
                }
            }
        }
        return bookings;
    }

}
