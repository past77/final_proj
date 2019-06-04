package ppolozhe.filters;

import ppolozhe.enums.StatusUser;
import ppolozhe.modelEntity.User;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Admin_sFilter extends MainFilter{
    private List<String> notAllowed;

    public void init(FilterConfig filterConfig) throws ServletException {

        notAllowed = new ArrayList();

        notAllowed.add("/login");
        notAllowed.add("/addBooking");
        notAllowed.add("/deleteBooking");
        notAllowed.add("/registration");
        //notAllowed.add("/login");

    }

    @Override
    boolean isn_tAllowed(HttpServletRequest req, User user) {
        return user != null && user.getAccounts().getStatusUser().equals(StatusUser.ADMIN) && notAllowed.contains(req.getServletPath());
    }
}
