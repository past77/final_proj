package jdbcDaoRealization;

import connectionDao.ConnectionToDatabase;
import connectionDao.JdbcConnection;
import constants.MessageForLogger;
import dto.JdbcDto;
import dao.AbstaractFuncForDao;
import dao.RoomDaoInterface;
import enums.TypeRoom;
import modelEntity.Booking;
import modelEntity.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.valueOf;

/**
 * Created by ppolozhe on 5/26/19.
 */
public class JdbcRoomDao extends AbstaractFuncForDao implements RoomDaoInterface {
    MessageForLogger messageForLogger = new MessageForLogger();

    private static final Logger LOGGER = Logger.getLogger(valueOf(JdbcRoomDao.class));

    private static ConnectionToDatabase connectionToDatabase;//ATTENTION
    private static final String ROOM_TABLE = "room";
    private static final String ID = "id";
    private static final String CAPACITY = "capacity";
    private static final String ROOM_TYPE = "typeRoom";
    private static final String PRICE = "price";
    private static final String NUMBER = "number";
    private static final String BOOKING = "booking";
    private static final String BOOKINGROOM = "booking.idRoom";
    private static final String ROOMID = "room.id";


    private static final String INSERT_ROOM = "INSERT INTO `hotel`." + ROOM_TABLE + "(`"+ CAPACITY +"`, `"
            + ROOM_TYPE +"`, `"+ PRICE +"`, `"+ NUMBER +"`) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_ROOM = "UPDATE `hotel`.`" + ROOM_TABLE + " SET `"+ CAPACITY +"`=?, `"
            + ROOM_TYPE +"`=?, `"+ PRICE +"`=?, `"+ NUMBER +"``=? WHERE `"+ ID +"`=?;";
    private static final String DELETE_ROOM = "DELETE FROM `hotel`.`"+ ROOM_TABLE+"` WHERE `"+ ID +"`=?;";

    private static final String FIND_ALL = "SELECT * FROM " + ROOM_TABLE + " ";
    private static final String FIND_BY_ID = FIND_ALL + "WHERE "+ ID +" = ?";
    private static final String FIND_BY_NUMBER = FIND_ALL + "WHERE "+ NUMBER +" = ?";
    private static final String FIND_FREE_NUMBER = FIND_ALL +
            "WHERE "+ ROOM_TABLE +"."+ ID + "NOT IN (" +
            "SELECT "+ BOOKINGROOM +" " +
            "FROM "+ ROOM_TABLE +" " +
            "INNER JOIN "+ BOOKING +" ON "+ BOOKINGROOM +" = " + ROOM_TABLE + "." + ID +" " +
            "WHERE status = 'confirmed' and (? < "+ BOOKING +".dateOut AND  ? > "+ BOOKING +".dateIn)) " +
            "AND "+ROOM_TYPE+"."+ROOM_TYPE+" = ? and "+ROOM_TYPE+".capacity >= ?";
    private static final String FIND_BY_BOOKING = FIND_ALL + " INNER JOIN "+BOOKING+" ON "+ROOMID+" = "+BOOKINGROOM+" " +
            " WHERE booking.id = ? AND "+ROOMID+" NOT IN (" +
            "SELECT "+BOOKINGROOM+" " +
            "FROM " + ROOM_TABLE + " " +
            "INNER JOIN "+ BOOKING +" ON "+BOOKINGROOM+" = "+ROOMID+" " +
            "WHERE status = 'confirmed' and (? < booking.dateOut AND  ? > booking.dateIn)) " +
            "AND room.typeRoom = ? and room.capacity > ?";


    JdbcRoomDao(ConnectionToDatabase connectionToDatabase) {
        this.connectionToDatabase = connectionToDatabase;
    }

    private List<Room> getSomeRooms(ResultSet resultSet) throws SQLException {
        List<Room> result = new ArrayList<>();
        if(resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(buildRoom(resultSet));
            }
        }
        return result;
    }

    private Optional<Room> getOneRoom(ResultSet resultSet) throws SQLException {
        Optional<Room> result = Optional.empty();
        if(resultSet.isBeforeFirst()){
            while (resultSet.next()) {
                result = Optional.of(buildRoom(resultSet));
            }
        }
        return result;
    }

    private Room buildRoom(ResultSet resultSet) throws SQLException {
        return new Room.Builder()
                .setId(resultSet.getInt(ID))
                .setRoomCap(resultSet.getInt(CAPACITY))
                .setNumber(resultSet.getInt(NUMBER))
                .setRoomType(TypeRoom.valueOf(resultSet.getString(2).toUpperCase()))//СHANGE NUM OF INDEX
                .setPrice(resultSet.getInt(3))
                .build();
    }

    @Override
    public Optional<Room> find(int id) throws Exception {
        Optional<Room> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result = getOneRoom(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + "find" + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<Room> findAll() throws Exception {
        List<Room> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = statement.executeQuery();
            result = getSomeRooms(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + "findAll" + e.getMessage());
        }
        return result;
    }


    @Override
    public boolean create(Room room) throws Exception {
        int insertedRow;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_ROOM,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(4, room.getRoomCap());//capindex
            statement.setString(3, room.getRoomType().toString());//aptype
            statement.setInt(2, room.getPrice());//price
            statement.setInt(5, room.getNumber());//number
            insertedRow = statement.executeUpdate();
            room.setId(generateId(statement));
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + "create" + e.getMessage());
            throw new Exception();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {

        int deletedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_ROOM)) {
            statement.setInt(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + "delete" + e.getMessage());
            throw new Exception();
        }
        return deletedRow > 0;
    }

    @Override
    public boolean update(Room room) throws Exception {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_ROOM)) {
            statement.setInt(4, room.getRoomCap());//cap
            statement.setString(3, room.getRoomType().toString());//type
            statement.setInt(2, room.getPrice());//price
            statement.setInt(5, room.getNumber());//num
            statement.setInt(1, room.getId());//id
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + "update" + e.getMessage());
            throw new Exception();
        }
        return updatedRow > 0;
    }


    @Override
    public Optional<Room> findByNumber(String number) throws Exception {
        Optional<Room> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_NUMBER)){
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            result = getOneRoom(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + messageForLogger.FIND_BY_NUMBER + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public Optional<Room> findByBooking(Booking booking) throws Exception {
        Optional<Room> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_BY_BOOKING)){
            statement.setInt(1, booking.getId());
            statement.setDate(2, Date.valueOf(booking.getDateIn()));
            statement.setDate(3, Date.valueOf(booking.getDateOut()));
            statement.setString(4, booking.getTypeRoom().toString());
            ResultSet resultSet = statement.executeQuery();
            result = getOneRoom(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + messageForLogger.FIND + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    @Override
    public List<Room> findFreeRooms(JdbcDto bookingDto) throws Exception {
        List<Room> result = null;
        try (JdbcConnection connection = connectionToDatabase.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_FREE_NUMBER)){
            statement.setDate(1, Date.valueOf(bookingDto.getDateFrom()));
            statement.setDate(2, Date.valueOf(bookingDto.getDateTo()));
            statement.setInt(4, bookingDto.getPersons());
            statement.setString(3, bookingDto.getTypeRoom().toString());
            ResultSet resultSet = statement.executeQuery();
            result = getSomeRooms(resultSet);
        } catch (SQLException e) {
            LOGGER.info(JdbcRoomDao.class.toString() + messageForLogger.FIND_FREE_NUMBER + e.getMessage());
            throw new Exception();
        }
        return result;
    }

    private static class Holder {
        static final JdbcRoomDao INSTANCE = new JdbcRoomDao(connectionToDatabase.getInstance());
    }

    public static JdbcRoomDao getInstance() {
        return Holder.INSTANCE;
    }

}
