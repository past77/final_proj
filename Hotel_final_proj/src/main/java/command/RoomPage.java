package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomPage implements Command {
    private static class Holder {
        static final RoomPage INSTANCE = new RoomPage();
    }

    public static RoomPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/freeNumbers";
    }
}
