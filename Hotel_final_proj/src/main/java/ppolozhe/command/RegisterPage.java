package ppolozhe.command;

import ppolozhe.constants.JspConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPage implements Command {
    private static class Holder {
        static final RegisterPage INSTANCE = new RegisterPage();
    }

    public static RegisterPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JspConst.REGISTRATION;
    }
}
