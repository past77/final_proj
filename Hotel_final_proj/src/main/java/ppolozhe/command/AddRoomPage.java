package ppolozhe.command;

import ppolozhe.constants.JspConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddRoomPage implements Command {
   ;
    private static class Holder {
        static final AddRoomPage INSTANCE = new AddRoomPage();
    }

    public static AddRoomPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JspConst.ADD_APARTMENTS;
    }
}
