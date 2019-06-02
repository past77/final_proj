package jdbcDaoRealization;

import connectionDao.ConnectionToDatabase;
import connectionDao.JdbcConnection;
import constants.MessageForLogger;
import dao.AbstaractFuncForDao;
import dao.AccountsDaoInterface;
import enums.StatusUser;
import modelEntity.Accounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Created by ppolozhe on 5/26/19.
 */
public class JdbcAccountsDao extends AbstaractFuncForDao implements AccountsDaoInterface {

    MessageForLogger messageForLogger = new MessageForLogger();
    private ConnectionToDatabase connectionToDatabase;
    private static final Logger LOGGER = Logger.getLogger(JdbcAccountsDao.class);

    private static final String ACCOUNT_TABLE = "accounts";
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String STATUS = "status";



    private static final String INSERT_ACCOUNTS = "INSERT INTO `hotel`.`"+ ACCOUNT_TABLE +"` (`"+ LOGIN +"`, `"+ PASSWORD  +"`, `"+ STATUS + "`) VALUES (?, ?, ?); ";
    private static final String UPDATE_ACCOUNTS = "UPDATE `hotel`.`"+ ACCOUNT_TABLE +"` SET `"+ LOGIN +"`=?, `"+ PASSWORD +"`=? WHERE `"+ ID +"`=?; ";
    private static final String DELETE_ACCOUNTS = "DELETE FROM `hotel`.`"+ ACCOUNT_TABLE +"` WHERE `"+ ID +"`=?;";


    private static final String FIND_ALL = "SELECT * FROM "+ ACCOUNT_TABLE +"";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE "+ ID +" = ?";
    private static final String FIND_BY_LOGIN = FIND_ALL + " WHERE "+ LOGIN +" = ?";

    JdbcAccountsDao(ConnectionToDatabase connectionManager) {
        this.connectionToDatabase = connectionManager;
    }

    private static class Holder {
        static final JdbcAccountsDao INSTANCE = new JdbcAccountsDao(ConnectionToDatabase.getInstance());
    }

    public static JdbcAccountsDao getInstance() {
        return Holder.INSTANCE;
    }


    private List<Accounts> getSomeAccounts(ResultSet resultSet) throws SQLException {
        List<Accounts> result = new ArrayList<>();
        if(resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(buildAccounts(resultSet));
            }
        }
        return result;
    }

    private Optional<Accounts> getOneAccount(ResultSet resultSet) throws SQLException {
        Optional<Accounts> result = Optional.empty();
        if(resultSet.isBeforeFirst()){
            while (resultSet.next()) {
                result = Optional.of(buildAccounts(resultSet));
            }
        }
        return result;
    }

    private Accounts buildAccounts(ResultSet resultSet) throws SQLException {
        return new Accounts.Builder()
                .setId(resultSet.getInt(ID))
                .setLogin(resultSet.getString(LOGIN))
                .setPassword(resultSet.getString(PASSWORD))
               .setStatusUser(StatusUser.valueOf(resultSet.getString(STATUS).toUpperCase()))
                .build();
    }


    @Override
    public Optional<Accounts> findAccountsByLogin(String login) throws Exception{
        Optional<Accounts> result = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_LOGIN)){
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            result = getOneAccount(resultSet);
            LOGGER.info(JdbcAccountsDao.class.toString() + "findAccountByLogin");
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() + messageForLogger.DELETE + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public Optional<Accounts> find(int id) throws Exception {
        Optional<Accounts> result = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result = getOneAccount(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() + messageForLogger.FIND + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public boolean create(Accounts accounts) throws Exception {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_ACCOUNTS,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, accounts.getLogin());
            statement.setString(2, accounts.getPassword());
            statement.setString(3, accounts.getStatusUser().toString());
            insertedRow = statement.executeUpdate();
            accounts.setId(generateId(statement));
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.error("error" + e);
            throw new Exception();//DUBLICATE
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() +" "+ messageForLogger.CREATE + e.getMessage());
            throw new Exception();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        int deletedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_ACCOUNTS)) {
            statement.setInt(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() + messageForLogger.DELETE + e.getMessage());
            throw new Exception();
        }
        return deletedRow > 0;
    }

    @Override
    public boolean update(Accounts userAccounts) throws Exception {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_ACCOUNTS)) {
            statement.setString(2, userAccounts.getLogin());
            statement.setString(3, userAccounts.getPassword());
            statement.setString(4, userAccounts.getStatusUser().toString());
            statement.setInt(1, userAccounts.getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() + messageForLogger.UPDATE + e.getMessage());
            throw new Exception();
        }
        return updatedRow > 0;
    }

    @Override
    public List<Accounts> findAll() throws Exception {
        List<Accounts> result = null;
        ResultSet resultSet = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL)){
            resultSet = statement.executeQuery();
            result = getSomeAccounts(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcAccountsDao.class.toString() + messageForLogger.FIND_ALL + e.getMessage());
            throw new Exception();
        }
        return result;
    }
}
