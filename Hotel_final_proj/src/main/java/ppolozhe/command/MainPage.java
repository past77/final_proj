package ppolozhe.command;

import ppolozhe.constants.MassageForUsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class MainPage implements Command{

    MassageForUsers messageForUsers = new MassageForUsers();

    private static class Holder {
        static final MainPage INSTANCE = new MainPage();
    }

    public static MainPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return messageForUsers.MAIN;
    }
}
