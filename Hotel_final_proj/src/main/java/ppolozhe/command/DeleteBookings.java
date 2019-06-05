package ppolozhe.command;

import org.apache.log4j.Logger;
import ppolozhe.service.BookingService;
import ppolozhe.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBookings implements Command{
    private static final Logger LOGGER = Logger.getLogger(DeleteBookings.class);

    private BookingService bookingService;

    DeleteBookings(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static class Holder {
        static final DeleteBookings INSTANCE = new DeleteBookings(BookingService.getInstance());
    }

    public static DeleteBookings getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       LOGGER.info("request.getParameter(\"delete\")________"+ request.getParameter("delete"));
        int id = Integer.parseInt(request.getParameter("delete"));
        LOGGER.info("id: " + id);
        bookingService.delete(id);
        request.setAttribute("success", "message.complete");
        return UsersBookings.getInstance().execute(request, response);
    }

}
