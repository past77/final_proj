package ppolozhe.command;

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
            return "addApartments";
        }
        roomService.create(room);
        request.setAttribute("success", "message.complete");
        return "addApartments";
    }

    private void setAttributesToRequest(HttpServletRequest request, Room room, List<String> errors) {
        request.setAttribute("errors", errors);
        request.setAttribute("number", room.getNumber());
        request.setAttribute("price", room.getPrice());
        request.setAttribute("capacity", room.getRoomCap());
        request.setAttribute("typeRoom", room.getRoomType().toString());

    }

    private List<String> validate(Room room) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validatePrice(room.getPrice())){
            errors.add("message.price.invalid");
        }

        if(!validator.validateCapacity(room.getRoomCap())){
            errors.add("message.capacity.invalid");
        }
        if(!validator.validateNumber(room.getNumber())){
            errors.add("message.number.invalid");
        }

        return errors;
    }

    private Room createRoomFromRequest(HttpServletRequest request) {
        return new Room.Builder()
                .setPrice(Integer.parseInt(request.getParameter("price")))
                .setRoomCap(Integer.parseInt(request.getParameter("capacity")))
                .setRoomType(TypeRoom.valueOf(request.getParameter("typeRoom").toUpperCase()))
                .setNumber(Integer.parseInt(request.getParameter("number")))
                .build();
    }

}
