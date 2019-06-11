package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForLogger;
import ppolozhe.constants.MessageForUsers;
import ppolozhe.dto.JdbcDto;
import ppolozhe.enums.TypeRoom;
import ppolozhe.modelEntity.Room;
import org.apache.log4j.Logger;
import ppolozhe.service.RoomService;
import ppolozhe.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FreeRooms implements Command {
    private RoomService roomService;
    MessageForUsers messageForUsers = new MessageForUsers();
    JspConst jspConst = new JspConst();
    private static final Logger LOGGER = Logger.getLogger(FreeRooms.class);

    FreeRooms(RoomService roomService) {
        this.roomService = roomService;
    }

    private static class Holder {
        static final FreeRooms INSTANCE = new FreeRooms(RoomService.getInstance());
    }

    public static FreeRooms getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JdbcDto jdbcDto = createBookingFromRequest(request);
        String page = createPageFromRequest(request);
        List<String> errors = validate(jdbcDto);

        if(!errors.isEmpty()){
            setAttributesToRequestWithErrors(request, jdbcDto, errors);
            return page;
        }
        List<Room> rooms = roomService.findFreeRooms(jdbcDto);
        if (rooms == null || jdbcDto == null) {
            request.getRequestDispatcher("NotFound").forward(request, response);
        }
        setAttributesToRequest(request, jdbcDto, rooms);
        return page;
    }

    private String createPageFromRequest(HttpServletRequest request) {
        return request.getParameter("page");
    }

    private void setAttributesToRequest(HttpServletRequest request, JdbcDto bookingDto, List<Room> rooms) {
        setInputedAttributesToRequest(request, bookingDto);
        request.setAttribute("freeNumbersForBooking", rooms);
    }

    private void setAttributesToRequestWithErrors(HttpServletRequest request, JdbcDto bookingDto, List<String> errors) {
        setInputedAttributesToRequest(request, bookingDto);
        request.setAttribute(messageForUsers.ERRORS, errors);
    }

    private void setInputedAttributesToRequest(HttpServletRequest request, JdbcDto bookingDto) {
        request.setAttribute(messageForUsers.DATE_IN, bookingDto.getDateIn());
        request.setAttribute(messageForUsers.DATE_OUT, bookingDto.getDateOut());
        request.setAttribute(messageForUsers.TYPE_ROOM, bookingDto.getTypeRoom().toString());
    }


    private List<String> validate(JdbcDto booking) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateDate(booking.getDateIn(), booking.getDateOut())){
            errors.add(messageForUsers.INVALID_DATE);
        }

        return errors;
    }

    private JdbcDto createBookingFromRequest(HttpServletRequest request) {
        return new JdbcDto.Builder()
                .setDateIn(LocalDate.parse(request.getParameter(messageForUsers.DATE_IN)))
                .setDateOut(LocalDate.parse(request.getParameter(messageForUsers.DATE_OUT)))
                .setTypeRoom(TypeRoom.valueOf(request.getParameter(messageForUsers.TYPE_ROOM).toUpperCase()))
                .build();
    }
}
