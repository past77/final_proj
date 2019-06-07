package ppolozhe.command;

import ppolozhe.enums.Status;
import ppolozhe.enums.TypeRoom;
import ppolozhe.modelEntity.Booking;
import ppolozhe.modelEntity.Room;
import ppolozhe.modelEntity.User;
import org.apache.log4j.Logger;
import ppolozhe.service.BookingService;
import ppolozhe.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBook implements Command {
    private BookingService bookingService;
    private static final Logger LOGGER = Logger.getLogger(AddBook.class);
    AddBook(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static class Holder {
        static final AddBook INSTANCE = new AddBook(BookingService.getInstance());
    }

    public static AddBook getInstance() {
        LOGGER.info("addBook.GetInstance");
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Booking booking = createBookingFromRequest(request);
    LOGGER.info("GET USER_______: "+booking.getUser());
        List<String> errors = validate(booking);
        LOGGER.info("In addbook!");
        if(!errors.isEmpty()){
            LOGGER.info("In addbookErrors!");
            setAttributesToRequest(request, booking, errors);
            return "/addBook";
        }
        LOGGER.info("In addBOOK!");
        bookingService.create(booking);
        request.setAttribute("success", "message.complete");
        return "profilePage";
    }

    private void setAttributesToRequest(HttpServletRequest request, Booking booking, List<String> errors) {
        request.setAttribute("errors", errors);
        request.setAttribute("dateIn", booking.getDateIn());
        request.setAttribute("dateOut", booking.getDateOut());
        request.setAttribute("typeRoom", booking.getTypeRoom().toString());
    }


    private List<String> validate(Booking booking) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateDate(booking.getDateIn(), booking.getDateOut())){
            errors.add("message.invalid.date");
        }
        return errors;
    }

    private Booking createBookingFromRequest(HttpServletRequest request) {
        return new Booking.Builder()
                .setDateIn(LocalDate.parse(request.getParameter("dateIn")))
                .setDateOut(LocalDate.parse(request.getParameter("dateOut")))
                .setRoomType(TypeRoom.valueOf(request.getParameter("typeRoom").toUpperCase()))
                .setStatus(Status.PROCESSED)
                .setRoom(new Room.Builder()
                        .setId(Integer.parseInt(request.getParameter("find")))
                        .build())
                .setUser(((User) request.getSession().getAttribute("user")))
                .build();
    }

}
