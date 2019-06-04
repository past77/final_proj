package ppolozhe.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ppolozhe on 5/28/19.
 */
public class LoginPage implements Command {
    private static class Holder {
        static final LoginPage INSTANCE = new LoginPage();
    }

    public static LoginPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/login";
    }
}
