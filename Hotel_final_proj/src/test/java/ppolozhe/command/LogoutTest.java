package ppolozhe.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutTest {


        @Mock
        private HttpServletRequest request;

        @Mock
        private HttpServletResponse response;

        @Mock
        private HttpSession session;

        protected Logout logout = new Logout();

        @Test
        public void testLogout() {
            when(request.getSession()).thenReturn(session);

            logout.execute(request, response);
            verify(session).invalidate();
        }

}