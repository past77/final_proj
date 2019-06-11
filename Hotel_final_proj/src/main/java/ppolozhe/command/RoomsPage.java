package ppolozhe.command;

import ppolozhe.constants.JspConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomsPage implements Command {
    private static class Holder {
        static final RoomsPage INSTANCE = new RoomsPage();
    }

    public static RoomsPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JspConst.FREE_ROOMS;
    }
}
