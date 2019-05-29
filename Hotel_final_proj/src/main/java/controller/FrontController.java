package controller;

import command.CommandFactory;
import command.*;
import constants.MassageForUsers;
import constants.MessageForLogger;
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
    MassageForUsers messageForUsers = new MassageForUsers();
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
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
       dispatcher.forward(request, response);
    }

    private String executeCommand(HttpServletRequest request, HttpServletResponse response) {
        Command command = commandFactory.getCommand(request);

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
