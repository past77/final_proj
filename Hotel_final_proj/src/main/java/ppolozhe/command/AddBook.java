package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;
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

    MessageForUsers messageForUsers= new MessageForUsers();
    private BookingService bookingService;
    private static final Logger LOGGER = Logger.getLogger(AddBook.class);
    AddBook(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static class Holder {
        static final AddBook INSTANCE = new AddBook(BookingService.getInstance());
    }

    public static AddBook getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Booking booking = createBookingFromRequest(request);
        List<String> errors = validate(booking);
        if(!errors.isEmpty()){
            setAttributesToRequest(request, booking, errors);
            return JspConst.ADD_BOOK;
        }
        bookingService.create(booking);
        request.setAttribute(messageForUsers.SUCCESS, messageForUsers.COMPLETE);
        return JspConst.PROFILE;
    }

    private void setAttributesToRequest(HttpServletRequest request, Booking booking, List<String> errors) {
        request.setAttribute(messageForUsers.ERRORS, errors);
        request.setAttribute(messageForUsers.DATE_IN, booking.getDateIn());
        request.setAttribute(messageForUsers.DATE_OUT, booking.getDateOut());
        request.setAttribute(messageForUsers.TYPE_ROOM, booking.getTypeRoom().toString());
    }


    private List<String> validate(Booking booking) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateDate(booking.getDateIn(), booking.getDateOut())){
            errors.add(messageForUsers.INVALID_DATE);
        }
        return errors;
    }

    private Booking createBookingFromRequest(HttpServletRequest request) {
        return new Booking.Builder()
                .setDateIn(LocalDate.parse(request.getParameter(messageForUsers.DATE_IN)))
                .setDateOut(LocalDate.parse(request.getParameter(messageForUsers.DATE_OUT)))
                .setRoomType(TypeRoom.valueOf(request.getParameter(messageForUsers.TYPE_ROOM).toUpperCase()))
                .setStatus(Status.PROCESSED)
                .setRoom(new Room.Builder()
                        .setId(Integer.parseInt(request.getParameter(messageForUsers.FIND)))
                        .build())
                .setUser(((User) request.getSession().getAttribute(messageForUsers.USER)))
                .build();
    }

}
