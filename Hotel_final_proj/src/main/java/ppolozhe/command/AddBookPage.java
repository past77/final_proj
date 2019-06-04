package ppolozhe.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookPage implements Command{
    private static class Holder {
        static final AddBookPage INSTANCE = new AddBookPage();
    }

    public static AddBookPage getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "addBook";
    }
}
