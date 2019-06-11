package ppolozhe.command;

import ppolozhe.constants.JspConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout implements Command{
JspConst jspConst = new JspConst();

    private static class Holder {
        static final Logout INSTANCE = new Logout();
    }

    public static Logout getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return jspConst.MAIN;
    }
}
