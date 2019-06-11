package ppolozhe.controller;

import ppolozhe.command.Command;
import ppolozhe.command.CommandFactory;
import ppolozhe.constants.MessageForUsers;
import ppolozhe.constants.MessageForLogger;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class FrontController extends HttpServlet{
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);
    private CommandFactory commandFactory;
    MessageForUsers messageForUsers = new MessageForUsers();
    MessageForLogger messageForLogger = new MessageForLogger();

    public FrontController() {
        commandFactory = CommandFactory.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = executeCommand(request, response);

        String path = "/WEB-INF/JspFiles/" + page + ".jsp";
        // String path = String.format("/WEB-INF/JspFiles/%s.jsp ", page);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
       dispatcher.forward(request, response);
    }

    private String executeCommand(HttpServletRequest request, HttpServletResponse response) {
        Command command = commandFactory.getCommand(request);
        LOGGER.info("ppolozhe.command in Front_ExecuteCommand " + command);

        String page = null;
        try {
            page = command.execute(request, response);
        } catch (Exception e) {
            page = messageForUsers.ERROR;
            e.printStackTrace();
            LOGGER.error(messageForLogger.EXECUTE_COMMAND + e);
        }
        return page;
    }


}
