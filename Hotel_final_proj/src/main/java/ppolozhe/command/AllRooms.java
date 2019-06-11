package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForLogger;
import ppolozhe.modelEntity.Room;
import ppolozhe.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ppolozhe on 5/28/19.
 */
public class AllRooms implements Command{
    private RoomService roomService;
    JspConst jspConst = new JspConst();

    AllRooms(RoomService roomService) {
        this.roomService = roomService;
    }

    private static class Holder {
        static final AllRooms INSTANCE = new AllRooms(RoomService.getInstance());
    }

    public static AllRooms getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Room> rooms;
        rooms = roomService.findAll();
        request.setAttribute("rooms", rooms);
        return jspConst.ALL_ROOMS;
    }
}
