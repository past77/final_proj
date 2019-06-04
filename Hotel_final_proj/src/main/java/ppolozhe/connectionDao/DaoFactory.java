package ppolozhe.connectionDao;

import ppolozhe.dao.*;
import ppolozhe.jdbcDaoRealization.*;
import org.apache.log4j.Logger;

public class DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
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
    public BillDaoInterface getBillDao(){
        return JdbcBillDao.getInstance();
    }
}