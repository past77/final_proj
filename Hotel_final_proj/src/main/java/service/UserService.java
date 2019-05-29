package service;

import connectionDao.ConnectionToDatabase;
import connectionDao.DaoFactory;
import jdbcDaoRealization.JdbcAccountsDao;
import modelEntity.Accounts;
import modelEntity.User;
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
            LOGGER.info(" LoOGIN " + login + " PASSWORD: " + password);
        accounts = daoFactory.getAccountsDao().findAccountsByLogin(login);
        LOGGER.info(accounts + " Acc!");
        if (accounts.isPresent() && correctPassword(accounts.get(), password)) {
            user = daoFactory.getUserDao().find(accounts.get().getId());
            user.get().setAccounts(accounts.get());
        }
        return user;
    }

    private boolean correctPassword(Accounts accounts, String password) {
        boolean res = false;
            LOGGER.info(" PASSWORFD_IN_DB:::: " + accounts.getPassword() + " PASSWORD_RFOM_FORM: " + password);
        if (password.equals(accounts.getPassword())){
            res = true;
        }
        return res;
    }

//    public boolean create(User user) throws Exception {
//        boolean created = false;
//        try {
//            connectionToDatabase.startTransaction();
//            daoFactory.getUserAuthenticationDao().create(user.getUserAuthentication());
//            created = daoFactory.getUserDao().create(user);
//            if(1 == 1){
//                throw new RuntimeException();
//            }
//            connectionManager.commit();
//        } catch (DaoException e){
//            connectionManager.rollback();
//            throw e;
//        }
//        return created;
//    }

}
