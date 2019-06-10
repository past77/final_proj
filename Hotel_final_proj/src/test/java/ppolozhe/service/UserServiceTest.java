package ppolozhe.service;

import org.junit.Test;
import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.enums.StatusUser;
import ppolozhe.jdbcDaoRealization.JdbcAccountsDao;
import ppolozhe.jdbcDaoRealization.JdbcUserDao;
import ppolozhe.modelEntity.Accounts;

import ppolozhe.modelEntity.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    private DaoFactory daoFactory;
    private ConnectionToDatabase connectionToDatabase;
    private UserService userService;
    private JdbcUserDao userDao;
    private JdbcAccountsDao accountsDao;
    private Accounts accounts;

    private void init(){
        daoFactory = mock(DaoFactory.class);
        connectionToDatabase = mock(ConnectionToDatabase.class);
        accountsDao = mock(JdbcAccountsDao.class);
        userDao = mock(JdbcUserDao.class);
        userService = new UserService(connectionToDatabase, daoFactory);
        accounts = new Accounts(1, "user@gmail.com", "password", StatusUser.USER);
    }

    private Accounts buildAccounts() {
        return new Accounts.Builder()
                .setId(1)
                .setLogin("user@gmail.com")
                .setPassword("password")
                .setStatusUser(StatusUser.USER)
                .build();
    }

    private User buildUser() {
        Accounts accounts = buildAccounts();
        return new User.Builder()
                .setAccounts(accounts)
                .setFirstName("Andrew")
                .setSurname("Balaban")
                .setPhoneNumber("+390986765648")
                .build();
    }

    private void stubDaoFactory() {
        when(daoFactory.getAccountsDao()).thenReturn(accountsDao);
        when(daoFactory.getUserDao()).thenReturn(userDao);
    }

    @Test
    public void testCreateAuthor() throws Exception {
        User user = buildUser();
        init();
        stubDaoFactory();

        userService.create(user);

        verify(daoFactory).getAccountsDao();
        verify(userDao).create(user);
    }

}