package ppolozhe.service;

import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.dto.JdbcDto;
import ppolozhe.jdbcDaoRealization.JdbcRoomDao;
import ppolozhe.modelEntity.Room;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class RoomService {

    private static final Logger LOGGER = Logger.getLogger(RoomService.class);
    private ConnectionToDatabase connectionToDatabase;
    private DaoFactory daoFactory;

    RoomService(ConnectionToDatabase connectionManager, DaoFactory daoFactory) {
        this.connectionToDatabase = connectionToDatabase;
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final RoomService INSTANCE = new RoomService(ConnectionToDatabase.getInstance(), DaoFactory.getInstance());
    }

    public static RoomService getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<Room> find(int id) throws Exception {
        Optional<Room> apartment;
        apartment = daoFactory.getRoomDao().find(id);
        return apartment;
    }
    /*
        LocalDate dateFrom, LocalDate dateTo, int capacity, Apartment.ApartmentsType apartmentsType
    */
    public List<Room> findFreeRooms(JdbcDto bookingDto) throws Exception {
        List<Room> apartment;
        apartment = daoFactory.getRoomDao().findFreeRooms(bookingDto);
        return apartment;
    }


    public List<Room> findAll() throws Exception {
        List<Room> apartment;
        apartment = daoFactory.getRoomDao().findAll();
        LOGGER.info("APARTMENS "+ apartment + " ");
        return apartment;
    }

    public boolean create(Room room) throws Exception {
        boolean created;
        JdbcRoomDao jdbcRoomDao = (JdbcRoomDao) daoFactory.getRoomDao(); // daoFactory.getRoomDao();
        created = jdbcRoomDao.create(room);
        return created;
    }

    public boolean delete(int id) throws Exception {
        boolean deleted;
        deleted = daoFactory.getRoomDao().delete(id);
        return deleted;
    }
}
