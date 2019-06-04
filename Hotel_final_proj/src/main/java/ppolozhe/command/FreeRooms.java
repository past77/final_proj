package ppolozhe.command;

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

       //LOGGER.info("LocalDate.parse(request.getParameter(dateIn)): "+ LocalDate.parse(request.getParameter("dateIn")));
        ///LOGGER.info("LocalDate.parse(request.getParameter(dateOUT)): "+ LocalDate.parse(request.getParameter("dateOut")));

        JdbcDto jdbcDto = createBookingFromRequest(request);
        LOGGER.info(jdbcDto.getDateIn()+ " - Datein___DateOut - " + jdbcDto.getDateOut());
        String page = createPageFromRequest(request);
        LOGGER.info("pageinFreeRooms: " + page);
        List<String> errors = validate(jdbcDto);

        if(!errors.isEmpty()){
            setAttributesToRequestWithErrors(request, jdbcDto, errors);
            return page;
        }

        List<Room> rooms = roomService.findFreeRooms(jdbcDto);

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
        request.setAttribute("errors", errors);
    }

    private void setInputedAttributesToRequest(HttpServletRequest request, JdbcDto bookingDto) {
        request.setAttribute("dateIn", bookingDto.getDateIn());
        request.setAttribute("dateOut", bookingDto.getDateOut());
        request.setAttribute("typeRoom", bookingDto.getTypeRoom().toString());
    }


    private List<String> validate(JdbcDto booking) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateDate(booking.getDateIn(), booking.getDateOut())){
            errors.add("message.date.invalid");
        }

        return errors;
    }

    private JdbcDto createBookingFromRequest(HttpServletRequest request) {
        return new JdbcDto.Builder()
                .setDateIn(LocalDate.parse(request.getParameter("dateIn")))
                .setDateOut(LocalDate.parse(request.getParameter("dateOut")))
                .setTypeRoom(TypeRoom.valueOf(request.getParameter("typeRoom").toUpperCase()))
                .build();
    }
}
