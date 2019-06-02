package command;

import connectionDao.ConnectionToDatabase;
import constants.MassageForUsers;
import org.apache.log4j.Logger;
import service.UserService;
import validator.Validator;
import modelEntity.User;

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
    MassageForUsers messageForUsers = new MassageForUsers();

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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String login = request.getParameter(messageForUsers.LOGIN);
        String password = request.getParameter(messageForUsers.PASSWORD);

    LOGGER.info("login: "+ login + "password: " + password);
        List<String> errors = Validate(login, password);

        if (!errors.isEmpty()){
            setAttributesToRequest(request, login, errors);
            return messageForUsers.LOGIN;
        }
        Optional<User> user = userService.findUserByLoginPassword(login, password);

        LOGGER.info("USER:     " + user);
        if (!user.isPresent()) {

            errors.add("message.signin.error");
            setAttributesToRequest(request, login, errors);
            return messageForUsers.LOGIN;
        }
    LOGGER.info("USER.GET: "+ user.get());
        request.getSession().setAttribute(messageForUsers.USER, user.get());
        return "profilePage";
    }

    private void setAttributesToRequest(HttpServletRequest request, String login, List<String> errors){
        request.setAttribute(messageForUsers.LOGIN, login);
        request.setAttribute(messageForUsers.ERRORS, errors);
    }

    private List<String> Validate(String login, String password) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateLogin(login)){
            errors.add("message.login.invalid");
        }
       // if(!validator.validatePassword(password)){
          //  errors.add("message.password.invalid");
      //  }
        return errors;
    }
}
