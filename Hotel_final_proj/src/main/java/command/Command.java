package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ppolozhe on 5/27/19.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
