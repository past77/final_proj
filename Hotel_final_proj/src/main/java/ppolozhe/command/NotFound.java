package ppolozhe.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ppolozhe on 5/28/19.
 */
public class NotFound implements Command {
    private static final Logger LOGGER = Logger.getLogger(NotFound.class);

    private static class Holder {
        static final NotFound INSTANCE = new NotFound();
    }

    public static NotFound getInstance() {
        return NotFound.Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "notFound";
    }
}