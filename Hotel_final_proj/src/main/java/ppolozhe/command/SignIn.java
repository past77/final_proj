package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;
import org.apache.log4j.Logger;
import ppolozhe.service.UserService;
import ppolozhe.validator.Validator;
import ppolozhe.modelEntity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class SignIn implements Command{
    private static final Logger LOGGER = Logger.getLogger(SignIn.class);
    MessageForUsers messageForUsers = new MessageForUsers();

    private UserService userService;

    SignIn(UserService userService) {
        this.userService = userService;
    }

    private static class Holder {
        static final SignIn INSTANCE = new SignIn(UserService.getInstance());
    }

    public static SignIn getInstance() {
        return Holder.INSTANCE;
    }

    public SignIn(){}

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String login = request.getParameter(messageForUsers.LOGIN);
        String password = request.getParameter(messageForUsers.PASSWORD);

    LOGGER.info("login: "+ login + " password: " + password);
        List<String> errors = Validate(login, password);
        if (!errors.isEmpty()){
            setAttributesToRequest(request, login, errors);
            return messageForUsers.LOGIN;
        }
        Optional<User> user = userService.findUserByLoginPassword(login, password);
        LOGGER.info(user);

        if (!user.isPresent()) {

            errors.add(messageForUsers.INVALID_SIGIN);
            setAttributesToRequest(request, login, errors);
            return messageForUsers.LOGIN;
        }
        request.getSession().setAttribute(messageForUsers.USER, user.get());
        return JspConst.PROFILE;
    }

    private void setAttributesToRequest(HttpServletRequest request, String login, List<String> errors){
        request.setAttribute(messageForUsers.LOGIN, login);
        request.setAttribute(messageForUsers.ERRORS, errors);
    }

    private List<String> Validate(String login, String password) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateLogin(login)){
            errors.add(messageForUsers.INVALID_LOGIN);
        }
        return errors;
    }
}
