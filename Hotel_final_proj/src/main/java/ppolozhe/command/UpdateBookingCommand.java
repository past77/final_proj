package ppolozhe.command;

import ppolozhe.enums.Status;
import ppolozhe.enums.TypeRoom;
import ppolozhe.modelEntity.Booking;
import ppolozhe.modelEntity.Room;
import ppolozhe.modelEntity.User;
import ppolozhe.service.BookingService;
import ppolozhe.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UpdateBookingCommand implements Command {

    private static final java.lang.String SPACE = " ";
    private BookingService bookingService;

    UpdateBookingCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static class Holder {
        static final UpdateBookingCommand INSTANCE = new UpdateBookingCommand(BookingService.getInstance());
    }

    public static UpdateBookingCommand getInstance() {
        return Holder.INSTANCE;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Booking> bookings = createBookingsFromRequest(request);
        System.out.println("bookings.id" + bookings.toString());

        List<String> errors = validate(bookings);

        if(!errors.isEmpty()){
            setErrorsToRequest(request, errors);
            return ProcessedBooking.getInstance().execute(request, response);
        }

       // System.out.println(bookings.toString());
        bookingService.update(bookings);

        return ProcessedBooking.getInstance().execute(request, response);
    }

    private List<String> validate(List<Booking> bookings) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

//        if(!validator.validateNumberAndDateUpdatedBookings(bookings)){
//            errors.add("message.number.and.date.invalid");
//        }

        return errors;
    }

    private void setErrorsToRequest(HttpServletRequest request, List<String> errors) {
        request.setAttribute("errors", errors);
    }

    private List<Booking> createBookingsFromRequest(HttpServletRequest request) {
        List<Booking> bookings = new ArrayList<>();

        for (int i = 0; i < request.getParameterValues("id").length; i++) {
            Booking booking = new Booking.Builder()
                    .setId(Integer.parseInt(request.getParameterValues("id")[i]))
                    .setDateIn(LocalDate.parse(request.getParameterValues("dateIn")[i]))
                    .setDateOut(LocalDate.parse(request.getParameterValues("dateOut")[i]))
                    .setRoomType(TypeRoom.valueOf(request.getParameterValues("typeRoom")[i].toUpperCase()))
                    //.setUser(((User) request.getSession().getAttribute("user")))
                    .build();
            System.out.println("getId: " + booking.getId());
            if (request.getParameterValues("status")[i].equals(Status.REJECTED.toString())) {
                booking.setStatus(Status.REJECTED);
                bookings.add(booking);
            }
            System.out.println("!!!!!!!!!!!!!!Booking: "+booking.toString());
            if (request.getParameterValues("status")[i].equals(Status.CONFIRMED.toString())) {
                String[] apartmentParameters = request.getParameterValues("status")[i].split(SPACE);
                booking.setStatus(Status.CONFIRMED);
                booking.setRoom(new Room.Builder()
                        .setId(Integer.parseInt(request.getParameter("idRoom")))
                        .setPrice(Integer.parseInt(request.getParameter("roomsPrice")))
                        .build());
                System.out.println("!!!!!!!!!!!!!!Booking__2: "+booking.toString());
                bookings.add(booking);
            }
        }
        return bookings;
    }
}

