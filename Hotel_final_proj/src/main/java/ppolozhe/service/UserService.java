package ppolozhe.service;

import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.modelEntity.Accounts;
import ppolozhe.modelEntity.User;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private ConnectionToDatabase connectionToDatabase;
    private DaoFactory daoFactory;

    UserService(ConnectionToDatabase connectionToDatabase, DaoFactory daoFactory) {
        this.connectionToDatabase = connectionToDatabase;
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final UserService INSTANCE = new UserService(ConnectionToDatabase.getInstance(), DaoFactory.getInstance());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> findUserByLoginPassword(String login, String password) throws Exception {
        Optional<User> user = Optional.empty();
        Optional<Accounts> accounts;
            LOGGER.info(" Login " + login + " Password: " + password);
        accounts = daoFactory.getAccountsDao().findAccountsByLogin(login);
        LOGGER.info(accounts);
        if (accounts.isPresent() && correctPassword(accounts.get(), password)) {
            LOGGER.info("accounts.get().getId(): "+accounts.get().getId());
            user = daoFactory.getUserDao().find(accounts.get().getId());
            LOGGER.info(user);
            user.get().setAccounts(accounts.get());
        }
        return user;
    }

    private boolean correctPassword(Accounts accounts, String password) {
        boolean res = false;
            LOGGER.info(" PASSWORD_IN_DB:::: " + accounts.getPassword() + " PASSWORD_FROM_FORM: " + password);
        if (password.equals(accounts.getPassword())){
            res = true;
        }
        LOGGER.info(res);
        return res;
    }



    public boolean create(User user) throws Exception {
        boolean created = false;
        try {
            LOGGER.info(user.getSurname() + " " + user.getFirstName() + " " + user.getPhoneNumber() + " " +
                    user.getAccounts() + " " + user.getId());
            connectionToDatabase.startTransaction();
            daoFactory.getAccountsDao().create(user.getAccounts());
            created = daoFactory.getUserDao().create(user);
            connectionToDatabase.commit();
        } catch (Exception e){
            connectionToDatabase.rollback();
            throw e;
        }
        return created;
    }

}
