package ppolozhe.command;

import ppolozhe.constants.JspConst;
import ppolozhe.constants.MessageForUsers;
import ppolozhe.enums.StatusUser;
import ppolozhe.modelEntity.Accounts;
import ppolozhe.modelEntity.User;
import org.apache.log4j.Logger;
import ppolozhe.service.UserService;
import ppolozhe.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppolozhe on 5/28/19.
 */
public class SignUp implements Command {
    UserService userService;
    MessageForUsers messageForUsers = new MessageForUsers();
    private static final Logger LOGGER = Logger.getLogger(SignUp.class);


    SignUp(UserService userService) {
        this.userService = userService;
    }

    private static class Holder {
        static final SignUp INSTANCE = new SignUp(UserService.getInstance());
    }

    public static SignUp getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = createUserFromRequest(request);

        LOGGER.info(user.getSurname());

        List<String> errors = validateUser(user);
        if(!errors.isEmpty()){
            setAttributesToRequest(request, user, errors);
            return JspConst.REGISTRATION;
        }

        boolean created = createUser(user, errors);

        if(!created){
            setAttributesToRequest(request, user, errors);
            return JspConst.REGISTRATION;
        }
        setSuccessAtributeToRequest(request);
        return JspConst.MAIN;
    }

    private boolean createUser(User user, List<String> errors) throws Exception {
        boolean created = false;
        try {
            created = userService.create(user);
        } catch (Exception e) {
            errors.add(messageForUsers.DUPLICATE_LOGIN);
        }
        return created;
    }

    private void setSuccessAtributeToRequest(HttpServletRequest request) {
        request.setAttribute(messageForUsers.SUCCESS, messageForUsers.COMPLETE);
    }

    private void setAttributesToRequest(HttpServletRequest request, User user, List<String> errors){
        request.setAttribute(messageForUsers.LOGIN, user.getAccounts().getLogin());
        request.setAttribute(messageForUsers.FIRST_NAME, user.getFirstName());
        request.setAttribute(messageForUsers.SURNAME, user.getSurname());
        request.setAttribute(messageForUsers.PHONE, user.getPhoneNumber());
        request.setAttribute(messageForUsers.ERRORS, errors);
    }

    private List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();

        Validator validator = Validator.getInstance();

        if(!validator.validateLogin(user.getAccounts().getLogin())){
            errors.add(messageForUsers.INVALID_PASSWORD);
        }

        if(!validator.validatePhone(user.getPhoneNumber())){
            errors.add(messageForUsers.INVALID_PHONE);
        }
        return errors;
    }

    private User createUserFromRequest(HttpServletRequest request) {
        return new User.Builder()
                .setPhoneNumber(request.getParameter(messageForUsers.PHONE))
                .setSurname(request.getParameter(messageForUsers.SURNAME))
                .setFirstName(request.getParameter(messageForUsers.NAME))
                .setAccounts(new Accounts.Builder()
                        .setLogin(request.getParameter(messageForUsers.LOGIN))
                        .setPassword(request.getParameter(messageForUsers.PASSWORD))
                        .setStatusUser(StatusUser.USER)
                        .build())
                .build();
    }
}
