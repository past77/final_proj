package jdbcDaoRealization;

import connectionDao.ConnectionToDatabase;
import connectionDao.JdbcConnection;
import dao.AbstaractFuncForDao;
import dao.BookingDaoInterface;
import enums.Status;
import enums.TypeRoom;
import modelEntity.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.valueOf;

public class JdbcBookingDao extends AbstaractFuncForDao implements BookingDaoInterface {

    private static final Logger LOGGER = Logger.getLogger(valueOf(JdbcBookingDao.class));
    private static ConnectionToDatabase connectionToDatabase;//ATTENTION "STATIC"

    private static final String BOOKING_TABLE = "booking";
    private static final String BOOKING_ID = "id";
    private static final String BOOKING_ID_ROOM = "idRoom";
    private static final String BOOKING_ID_USER = "idUser";
    private static final String BOOKING_STATUS = "status";
    private static final String BOOKING_ID_DATE_IN = "dateIn";
    private static final String BOOKING_ID_DATE_OUT = "dateOut";
    
    private static final String SELECT_ALL = "SELECT * FROM " + BOOKING_TABLE;
    private static final String INSERT = "INSERT INTO " + BOOKING_TABLE + " (" + BOOKING_ID_ROOM + ", "
            + BOOKING_ID_USER + ", "
            + BOOKING_ID_DATE_IN + ", "
            + BOOKING_ID_DATE_OUT + ") "
            + "VALUES (?, ?, ?, ?) ";
    private static final String UPDATE = "UPDATE " + BOOKING_TABLE + " SET "
            + BOOKING_ID_ROOM + " = ?, "
            + BOOKING_ID_USER + " = ?, "
            + BOOKING_ID_DATE_IN + " = ?, "
            + BOOKING_ID_DATE_OUT + " = ? "
            + "WHERE + " + BOOKING_ID + " = ?";
    private static final String DELETE = "DELETE FROM " + BOOKING_TABLE + " WHERE " + BOOKING_ID + " = ?";

    private static final String FIND_ALL = "SELECT * FROM " + BOOKING_TABLE;
    private static final String FIND_BY_ID = FIND_ALL + "WHERE" + BOOKING_ID + " = ?";
    private static final String FIND_BY_USER = FIND_ALL + "INNER JOIN users ON" + BOOKING_TABLE +"."+ BOOKING_ID_USER + " = users.id WHERE users.id = ?";
    private static final String FIND_PROCESSED = FIND_ALL + "WHERE status = \"processed\"";
    private static final String LIMIT = "LIMIT ?,? ";


    JdbcBookingDao(ConnectionToDatabase connectionToDatabase) {
        this.connectionToDatabase = connectionToDatabase;
    }

    private static class DoLink {
        static final JdbcBookingDao INSTANCE = new JdbcBookingDao(connectionToDatabase.getInstance());
    }

    public static JdbcBookingDao getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean create(Booking booking) throws Exception {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setInt(2, booking.getRoom().getId());
            statement.setInt(3, booking.getUser().getAccounts().getId());
            statement.setString(4, booking.getStatus().toString());
            statement.setTimestamp(5, Timestamp.valueOf(booking.getDateIn().atStartOfDay()));
            statement.setTimestamp(6, Timestamp.valueOf(booking.getDateOut().atStartOfDay()));

            //statement.setString(3, booking.getRoom().getRoomType().toString());
            insertedRow = statement.executeUpdate();
            booking.setId(generateId(statement));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(JdbcBookingDao.class.toString() + "create()" + e.getMessage());
            throw new Exception();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean delete(int id)  throws Exception{
        int deletedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcBookingDao.class.toString() + "delete" + e.getMessage());
            throw new Exception();
        }
        return deletedRow > 0;
    }

    @Override
    public boolean update(Booking booking) throws Exception {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE)) {
            statement.setTimestamp(5, Timestamp.valueOf(booking.getDateIn().atStartOfDay()));
            statement.setTimestamp(6, Timestamp.valueOf(booking.getDateOut().atStartOfDay()));
            statement.setString(4, booking.getStatus().toString());
            //statement.setString(3, booking.getRoom().getRoomType().toString());
            if(booking.getRoom() != null){
                statement.setInt(3, booking.getRoom().getId());
            }else {
                statement.setNull(3, Types.INTEGER);
            }
            if(booking.getUser() != null) {
                statement.setInt(2, booking.getUser().getAccounts().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
//            if (booking.getMinibar() != null) {
//                statement.setInt(7, booking.getBill().getId());
//            } else {
//                statement.setNull(7, Types.INTEGER);
//            }
            statement.setInt(1, booking.getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcBookingDao.class.toString() + "update" + e.getMessage());
            throw new Exception();
        }
        return updatedRow > 0;

    }

    public Optional<Booking> findByNumber(String number) {
        return null;
    }

    @Override
    public List<Booking> getByUserId(int userId) throws Exception{
        List<Booking> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_USER)){
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            result = getSomeBookings(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(JdbcBookingDao.class.toString() + "find by user" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<Booking> findProcessedBooking(int firstRecord, int recordsPerPage) throws Exception {
        List<Booking> results = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_PROCESSED + LIMIT)){
            statement.setInt(1, firstRecord);
            statement.setInt(2, recordsPerPage);
            ResultSet resultSet = statement.executeQuery();
            results = getSomeBookings(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBookingDao.class.toString() + "FIND_PROCESSED_BOOKINGS" + e.getMessage());
            throw new Exception();
        }
        return results;
    }

    @Override
    public Optional<Booking> findByBooking(Booking booking) {
        return null;
    }

    private static class Holder {
        static final JdbcBookingDao INSTANCE = new JdbcBookingDao(ConnectionToDatabase.getInstance());
    }

    private Optional<Booking> getOneBooking(ResultSet resultSet) throws SQLException {
        Optional<Booking> result = Optional.empty();
        if(resultSet.isBeforeFirst()){
            while (resultSet.next()) {
                result = Optional.of(buildBooking(resultSet));
            }
        }
        return result;
    }

    private List<Booking> getSomeBookings(ResultSet resultSet) throws SQLException {
        List<Booking> result = new ArrayList<>();
        if(resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(buildBooking(resultSet));
            }
        }
        return result;
    }

    private Booking buildBooking(ResultSet resultSet) throws SQLException {
        return new Booking.Builder()
                .setId(resultSet.getInt(BOOKING_ID))
                .setStatus(Status.valueOf(resultSet.getString(BOOKING_STATUS).toUpperCase()))
                .setDateIn(resultSet.getTimestamp(BOOKING_ID_DATE_IN))
                .setDateOut(resultSet.getTimestamp(BOOKING_ID_DATE_OUT))
                .setRoomType(TypeRoom.valueOf(resultSet.getString(3).toUpperCase()))//not sure
                .build();
    }

    @Override
    public Optional<Booking> find(int id) throws Exception{
        Optional<Booking> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result = getOneBooking(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBookingDao.class.toString() + "find()" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<Booking> findAll() throws Exception {
        List<Booking> result = null;
        ResultSet resultSet = null;

        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL)){
            resultSet = statement.executeQuery();
            result = getSomeBookings(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcBookingDao.class.toString() + "findAll()" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

}