package ppolozhe.command;

import ppolozhe.constants.JspConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilePage implements Command {

    private static class Holder {
        static final ProfilePage INSTANCE = new ProfilePage();
    }

    public static ProfilePage getInstance() {
        return Holder.INSTANCE;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return JspConst.PROFILE;
    }
}
