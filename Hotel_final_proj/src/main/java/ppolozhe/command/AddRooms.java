package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;
import ppolozhe.enums.TypeRoom;
import ppolozhe.modelEntity.Room;
import ppolozhe.service.RoomService;
import ppolozhe.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AddRooms implements Command{
    private RoomService roomService;
    MessageForUsers messageForUsers = new MessageForUsers();


    AddRooms(RoomService roomService) {
        this.roomService = roomService;
    }

    private static class Holder {
        static final AddRooms INSTANCE = new AddRooms(RoomService.getInstance());
    }

    public static AddRooms getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Room room = createRoomFromRequest(request);

        List<String> errors = validate(room);

        if(!errors.isEmpty()){
            setAttributesToRequest(request, room, errors);
            return JspConst.ADD_APARTMENTS;
        }
        roomService.create(room);
        request.setAttribute(messageForUsers.SUCCESS, messageForUsers.COMPLETE);
        return JspConst.ADD_APARTMENTS;
    }

    private void setAttributesToRequest(HttpServletRequest request, Room room, List<String> errors) {
        request.setAttribute(messageForUsers.ERRORS, errors);
        request.setAttribute(messageForUsers.NUMBER, room.getNumber());
        request.setAttribute(messageForUsers.PRICE, room.getPrice());
        request.setAttribute(messageForUsers.CAPACITY, room.getRoomCap());
        request.setAttribute(messageForUsers.TYPE_ROOM, room.getRoomType().toString());

    }

    private List<String> validate(Room room) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validatePrice(room.getPrice())){
            errors.add(messageForUsers.INVALID_PRICE);
        }

        if(!validator.validateCapacity(room.getRoomCap())){
            errors.add(messageForUsers.INVALID_CAPACITY);
        }
        if(!validator.validateNumber(room.getNumber())){
            errors.add(messageForUsers.INVALID_NUMBER);
        }

        return errors;
    }

    private Room createRoomFromRequest(HttpServletRequest request) {
        return new Room.Builder()
                .setPrice(Integer.parseInt(request.getParameter(messageForUsers.PRICE)))
                .setRoomCap(Integer.parseInt(request.getParameter(messageForUsers.CAPACITY)))
                .setRoomType(TypeRoom.valueOf(request.getParameter(messageForUsers.TYPE_ROOM).toUpperCase()))
                .setNumber(Integer.parseInt(request.getParameter(messageForUsers.NUMBER)))
                .build();
    }

}
