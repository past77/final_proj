package filters;

import modelEntity.User;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Client_sFilter extends MainFilter{
    private List<String> notAllowed;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    boolean isn_tAllowed(HttpServletRequest req, User user) {
        return false;
    }
}
