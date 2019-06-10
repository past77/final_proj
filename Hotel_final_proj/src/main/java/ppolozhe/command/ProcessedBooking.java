package ppolozhe.command;

import ppolozhe.dto.JdbcDto;
import ppolozhe.modelEntity.Booking;
import ppolozhe.service.BookingService;
import ppolozhe.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProcessedBooking implements Command{
    private BookingService bookingService;
    private RoomService roomService;
    private static final int RECORDS_PER_PAGE = 5;


    ProcessedBooking(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    private static class Holder {
        static final ProcessedBooking INSTANCE = new ProcessedBooking(BookingService.getInstance(), RoomService.getInstance());
    }

    public static ProcessedBooking getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page = getPage(request);

        List<Booking> bookings = bookingService.findProcessedBookings((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        int numberOfPages = getNumberOfPages();
//        List<List<Apartment>> freeNumbersForBooking = getFreeNumbers(bookings);

//        System.out.println(bookings);
        setAttributesToRequest(request, bookings, numberOfPages, page);

        return "statusOfBooking";
    }

    private void setAttributesToRequest(HttpServletRequest request, List<Booking> bookings, int numberOfPages, int page) {
        request.setAttribute("bookings", bookings);
//        request.setAttribute(Parameters.FREE_NUMBERS, freeNumbersForBooking);
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
    }


    private int getNumberOfPages() throws Exception {
        int numberOfRecords = bookingService.getNumberOfPagesForProcessedBookings();
        int numberOfPages = (int) Math.ceil((double)numberOfRecords / RECORDS_PER_PAGE);
        return numberOfPages;
    }

    private int getPage(HttpServletRequest request) {
        int page;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }else {
            page = 1;
        }
        return page;
    }
}

