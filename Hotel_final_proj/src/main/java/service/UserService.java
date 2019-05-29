package service;

import connectionDao.ConnectionToDatabase;
import connectionDao.DaoFactory;
import modelEntity.Accounts;
import modelEntity.User;

import java.util.Optional;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class UserService {
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

        accounts = daoFactory.getAccountsDao().findAccountsByLogin(login);
        if (accounts.isPresent() && correctPassword(accounts.get(), password)) {
            user = daoFactory.getUserDao().find(accounts.get().getId());
            user.get().setAccounts(accounts.get());
        }
        return user;
    }

    private boolean correctPassword(Accounts accounts, String password) {
        boolean res = false;
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
