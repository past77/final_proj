package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class MainPage implements Command{
    JspConst jspConst = new JspConst();

    MessageForUsers messageForUsers = new MessageForUsers();

    private static class Holder {
        static final MainPage INSTANCE = new MainPage();
    }

    public static MainPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return jspConst.MAIN;
    }
}
