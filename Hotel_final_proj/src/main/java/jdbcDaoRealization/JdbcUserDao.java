package jdbcDaoRealization;

import connectionDao.ConnectionToDatabase;
import connectionDao.JdbcConnection;
import constants.MessageForLogger;
import dao.AbstaractFuncForDao;
import dao.UserDaoInterface;
import modelEntity.Booking;
import modelEntity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by ppolozhe on 5/26/19.
 */
public class JdbcUserDao extends AbstaractFuncForDao implements UserDaoInterface {

    MessageForLogger messageForLogger = new MessageForLogger();
    private ConnectionToDatabase connectionToDatabase;
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(JdbcUserDao.class));

    private static final String USER_TABLE = "users";
    private static final String ID = "id";
    private static final String FIRST_NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";

    private static final String INSERT_USER = "INSERT INTO `hotel`.`"+ USER_TABLE +"` (`"+ ID +"`, `"+
            FIRST_NAME +"`, `"+ SURNAME +"`, `"+ PHONE +"`) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE `hotel`.`"+ USER_TABLE +"` SET `"+ ID +"`=?, `"+
            FIRST_NAME +"`=?, `"+ SURNAME +"`=?, `"+ PHONE +"`=? WHERE `"+ ID +"`=?; ";
    private static final String DELETE_USER = "DELETE FROM `hotel`.`"+ USER_TABLE +"` WHERE `"+ ID +"`=?;";


    private static final String FIND_ALL = "SELECT * FROM "+ USER_TABLE +" ";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE "+ ID +" = ?";
    private static final String FIND_BY_FULL_NAME = FIND_ALL + " WHERE "+ SURNAME +" = ? AND "+ FIRST_NAME +" = ?";
    private static final String FIND_BY_BOOKING = FIND_ALL + " INNER JOIN booking ON "+ USER_TABLE +"."+ ID +" = booking.idUser " +
            " WHERE booking.id = ?";


    JdbcUserDao(ConnectionToDatabase connectionManager) {
        this.connectionToDatabase = connectionManager;
    }

    private static class Holder {
        static final JdbcUserDao INSTANCE = new JdbcUserDao(ConnectionToDatabase.getInstance());
    }

    public static JdbcUserDao getInstance() {
        return Holder.INSTANCE;
    }


    private List<User> getSomeUsers(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();

        if(resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(buildUser(resultSet));
            }
        }
        return result;
    }

    private Optional<User> getOneUser(ResultSet resultSet) throws SQLException {
        Optional<User> result = Optional.empty();

        if(resultSet.isBeforeFirst()){
            while (resultSet.next()) {
                result = Optional.of(buildUser(resultSet));
            }
        }
        return result;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setFirstName(resultSet.getString(FIRST_NAME))
                .setSurname(resultSet.getString(SURNAME))
                .setPhoneNumber(resultSet.getString(PHONE))
                .build();
    }


    @Override
    public Optional<User> find(int id) throws Exception {
        Optional<User> result = null;
        ResultSet resultSet = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            result = getOneUser(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.FIND + e.getMessage());
            throw new Exception();

        }
        return result;

    }

    @Override
    public boolean create(User user) throws Exception {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_USER)) {
            statement.setInt(1, user.getAccounts().getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPhoneNumber());
            insertedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.CREATE + e.getMessage());
            throw new Exception();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        int deletedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.DELETE + e.getMessage());
            throw new Exception();
        }
        return deletedRow > 0;
    }

    @Override
    public boolean update(User user) throws Exception {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_USER)) {
            statement.setInt(1, user.getAccounts().getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPhoneNumber());
            statement.setInt(5, user.getAccounts().getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.UPDATE + e.getMessage());
            throw new Exception();
        }
        return updatedRow > 0;
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> result = null;
        ResultSet resultSet = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL)){
            resultSet = statement.executeQuery();
            result = getSomeUsers(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.FIND_ALL + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<User> findUserByFullName(String firstName, String surname) throws Exception{
        List<User> result = null;
        ResultSet resultSet = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_FULL_NAME)){
            statement.setString(1, surname);
            statement.setString(2, firstName);
            resultSet = statement.executeQuery();
            result = getSomeUsers(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.FIND_BY_FULL_NAME + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public Optional<User> findByBooking(Booking booking) throws  Exception{
        Optional<User> result = null;
        ResultSet resultSet = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_BOOKING)){
            statement.setInt(1, booking.getId());
            resultSet = statement.executeQuery();
            result = getOneUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(JdbcUserDao.class.toString() + messageForLogger.FIND + e.getMessage());
            throw new Exception();
        }
        return result;
    }
}
