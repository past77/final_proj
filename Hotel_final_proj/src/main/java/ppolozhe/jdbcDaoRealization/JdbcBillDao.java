package ppolozhe.jdbcDaoRealization;

import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.JdbcConnection;
import ppolozhe.constants.MessageForLogger;
import ppolozhe.dao.BillDaoInterface;
import ppolozhe.modelEntity.Bill;
import ppolozhe.modelEntity.Booking;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by allugard on 01.07.17.
 */
public class JdbcBillDao implements BillDaoInterface {

    MessageForLogger messageForLogger = new MessageForLogger();
    private static final Logger LOGGER = Logger.getLogger(JdbcBillDao.class);
    private ConnectionToDatabase connectionToDatabase;

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRICE = "price";

    private static final String INSERT_BILL = "INSERT INTO `hotel`.`bill` (`price`) VALUES (?);";
    private static final String UPDATE_BILL = "UPDATE `hotel`.`bill` SET `price`=? WHERE `id`=?;";
    private static final String DELETE_BILL= "DELETE FROM `hotel`.`bill` WHERE `id`=?;";


    private static final String FIND_ALL = "SELECT * FROM bill";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String FIND_BY_BOOKING = FIND_ALL + " INNER JOIN booking ON bill.id = booking.idBill " +
            " WHERE booking.idbook = ?";

    JdbcBillDao(ConnectionToDatabase connectionToDatabase) {
        this.connectionToDatabase = connectionToDatabase;
    }


    private static class Holder {
        static final JdbcBillDao INSTANCE = new JdbcBillDao(ConnectionToDatabase.getInstance());
    }

    public static JdbcBillDao getInstance() {
        return Holder.INSTANCE;
    }


    @Override
    public Optional<Bill> findByBooking(Booking booking) throws Exception {
        Optional<Bill> result = Optional.empty();
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_BOOKING)){
            statement.setInt(1, booking.getId());
            ResultSet resultSet = statement.executeQuery();
            result = getBillFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + "FIND_BY_BOOKING" + e.getMessage());
            throw new Exception();
        }
        return result;
    }



    @Override
    public Optional<Bill> find(int id) throws Exception {
        Optional<Bill> result = Optional.empty();
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result = getBillFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + "FIND" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<Bill> findAll() throws Exception {
        List<Bill> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = statement.executeQuery();
            result = getBillsFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + " FIND_ALL" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public boolean create(Bill bill) throws Exception {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_BILL,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, bill.getPrice());
            insertedRow = statement.executeUpdate();
            bill.setId(Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + " CREATE" + e.getMessage());
            throw new Exception();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean update(Bill bill) throws Exception {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_BILL)) {
            statement.setInt(1, bill.getPrice());
            statement.setInt(2, bill.getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + " UPDATE" + e.getMessage());
            throw new Exception();
        }
        return updatedRow > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        int deletedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_BILL)) {
            statement.setInt(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcBillDao.class.toString() + " DELETE" + e.getMessage());
            throw new Exception();
        }
        return deletedRow > 0;
    }

    private List<Bill> getBillsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Bill> result = new ArrayList<>();
        if(resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(buildBill(resultSet));
            }
        }
        return result;
    }

    private Optional<Bill> getBillFromResultSet(ResultSet resultSet) throws SQLException {
        Optional<Bill> result = Optional.empty();
        if(resultSet.isBeforeFirst()){
            while (resultSet.next()) {
                result = Optional.of(buildBill(resultSet));
            }
        }
        return result;
    }

    private Bill buildBill(ResultSet resultSet) throws SQLException {
        return new Bill.Builder()
                .setId(resultSet.getInt(COLUMN_ID))
                .setPrice(resultSet.getInt(COLUMN_PRICE))
                .build();
    }
}

