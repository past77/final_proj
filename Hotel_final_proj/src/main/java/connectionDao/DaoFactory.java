package connectionDao;

import dao.AccountsDaoInterface;
import dao.BookingDaoInterface;
import dao.RoomDaoInterface;
import dao.UserDaoInterface;
import jdbcDaoRealization.JdbcAccountsDao;
import jdbcDaoRealization.JdbcBookingDao;
import jdbcDaoRealization.JdbcRoomDao;
import jdbcDaoRealization.JdbcUserDao;

public class DaoFactory {

    private DaoFactory() {
    }

    private static class Holder {
        static final DaoFactory daoFactory = new DaoFactory();
    }

    public static DaoFactory getInstance() {
        return Holder.daoFactory;
    }

    public BookingDaoInterface getBookingDao(){
        return JdbcBookingDao.getInstance();
    }

    public UserDaoInterface getUserDao(){
        return JdbcUserDao.getInstance();
    }
    public AccountsDaoInterface getAccountsDao(){
        return JdbcAccountsDao.getInstance();
    }

    public RoomDaoInterface getRoomDao(){
        return JdbcRoomDao.getInstance();
    }
}