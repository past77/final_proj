package ppolozhe.command;

import org.junit.Test;
import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.dto.JdbcDto;
import ppolozhe.enums.TypeRoom;
import ppolozhe.jdbcDaoRealization.JdbcRoomDao;
import ppolozhe.modelEntity.Room;
import ppolozhe.service.RoomService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class FreeRoomsTest {
    private DaoFactory daoFactory;
    private ConnectionToDatabase connectionToDatabase;
    private RoomService roomService;
    private JdbcRoomDao roomDao;
    private JdbcDto jdbcDto;

    private void init(){
        daoFactory = mock(DaoFactory.class);
        connectionToDatabase = mock(ConnectionToDatabase.class);
        roomDao = mock(JdbcRoomDao.class);
        roomService = new RoomService(connectionToDatabase, daoFactory);
        jdbcDto = mock(JdbcDto.class);
    }

    private JdbcDto buildRooms() {
        return new JdbcDto.Builder()
                .setDateIn(LocalDate.now())
                .setDateOut(LocalDate.now().plusDays(1))
                .setTypeRoom(TypeRoom.STANDART)
                .build();
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

    private List<Room> buildRoomm() {
        return Arrays.asList(buildRoom(), buildRoom());
    }

    private void stubDaoFactory() {
        when(daoFactory.getRoomDao()).thenReturn(roomDao);
    }

    private void stubForFindFree(List<Room> rooms) throws Exception {
        jdbcDto = buildRooms();
        when(roomDao.findFreeRooms(jdbcDto)).thenReturn(rooms);
    }


    @Test
    public void testFindFreeRooms() throws Exception {
        jdbcDto = buildRooms();
        List<Room> rooms = buildRoomm();
        init();
        stubForFindFree(rooms);
        stubDaoFactory();
        List<Room> actualRooms = roomService.findFreeRooms(jdbcDto);

        assertEquals(rooms, actualRooms);
        verify(daoFactory).getRoomDao();
        verify(roomDao).findFreeRooms(jdbcDto);
    }
}