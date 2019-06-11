package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.modelEntity.Room;
import ppolozhe.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllRoomPage implements Command{
    private RoomService roomService;

    AllRoomPage( RoomService apartmentService) {
        this.roomService = apartmentService;
    }

    private static class Holder {
        static final AllRoomPage INSTANCE = new AllRoomPage( RoomService.getInstance());
    }

    public static AllRoomPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Room> rooms;
        rooms =  roomService.findAll();
        request.setAttribute("rooms", rooms);
        return JspConst.ALL_ROOMS;
    }
}
