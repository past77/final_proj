package ppolozhe.command;

import ppolozhe.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRoom implements Command{
    private RoomService apartmentService;

    DeleteRoom(RoomService apartmentService) {
        this.apartmentService = apartmentService;
    }

    private static class Holder {
        static final DeleteRoom INSTANCE = new DeleteRoom(RoomService.getInstance());
    }

    public static DeleteRoom getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("delete"));
        apartmentService.delete(id);
        request.setAttribute("success", "message.complete");
        return "allRooms";
    }
}
