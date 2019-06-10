package ppolozhe.command;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.jdbcDaoRealization.JdbcUserDao;
import ppolozhe.modelEntity.User;
import ppolozhe.service.UserService;


import java.util.Locale;
import java.util.Optional;

/**
 * Created by lerafatova on 09.05.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @InjectMocks
    private JdbcUserDao jdbcUserDao = JdbcUserDao.getInstance();

    @InjectMocks
    private UserService userService = UserService.getInstance();



    @Mock
    private RequestDispatcher requestDispatcherLogin;

    @Mock
    private RequestDispatcher requestDispatcherMenu;

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User(2, "Paul", "Polozhevets", "+380989889673");
        when(request.getParameter("login")).thenReturn("ppolozhe@ukr.net");
        when(request.getParameter("password")).thenReturn("11111");
       //when(userService.findUserByLoginPassword("ppolozhe@ukr.net", "11111")).thenReturn(Optional.of(user));
        when(request.getSession(true)).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(request.getRequestDispatcher(eq("/login"))).thenReturn(requestDispatcherLogin);

//        SignIn login = new SignIn(userService);
//
//        login.execute(request, response);
//        verify(session).setAttribute(eq("user"), any());
//        verify(requestDispatcherLogin, never()).forward(request, response);
//        verify(response).sendRedirect(eq("/"));

    }

}