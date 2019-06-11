package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;
import ppolozhe.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRoom implements Command{
    private RoomService apartmentService;
    MessageForUsers messageForUsers = new MessageForUsers();

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
        int id = Integer.parseInt(request.getParameter(messageForUsers.DELETE));
        apartmentService.delete(id);
        request.setAttribute(messageForUsers.SUCCESS, messageForUsers.COMPLETE);
        return JspConst.ALL_ROOMS;
    }
}
