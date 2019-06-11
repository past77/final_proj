package ppolozhe.command;
import org.junit.Test;
import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.enums.TypeRoom;
import ppolozhe.jdbcDaoRealization.JdbcRoomDao;
import ppolozhe.modelEntity.Room;
import ppolozhe.service.RoomService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DeleteRoomTest {
    private DaoFactory daoFactory;
    private ConnectionToDatabase connectionToDatabase;
    private RoomService roomService;
    private JdbcRoomDao roomDao;

    private void init() {
        daoFactory = mock(DaoFactory.class);
        connectionToDatabase = mock(ConnectionToDatabase.class);
        roomDao = mock(JdbcRoomDao.class);
        roomService = new RoomService(connectionToDatabase, daoFactory);
    }

    private void stubDaoFactory() {
        when(daoFactory.getRoomDao()).thenReturn(roomDao);
    }

    private Room buildRoom() {
        return new Room.Builder()
                .setId(1)
                .setRoomCap(3)
                .setNumber(39)
                .setRoomType(TypeRoom.STANDART)
                .setPrice(3500)
                .build();
    }

    @Test
    public void TestDeleteRoom() throws  Exception{
        Room apartment = buildRoom();
        init();
        stubDaoFactory();

        roomService.create(apartment);
        verify(daoFactory).getRoomDao();
        verify(roomDao).create(apartment);
    }
}