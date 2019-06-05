package ppolozhe.service;

import ppolozhe.connectionDao.ConnectionToDatabase;
import ppolozhe.connectionDao.DaoFactory;
import ppolozhe.modelEntity.Bill;
import ppolozhe.modelEntity.Booking;

import java.util.Optional;

public class BillService {

    private ConnectionToDatabase connectionToDatabase;
    private DaoFactory daoFactory;

    BillService(ConnectionToDatabase connectionToDatabase, DaoFactory daoFactory) {
        this.connectionToDatabase = connectionToDatabase;
        this.daoFactory = daoFactory;
    }


    private static class Holder {
        static final BillService INSTANCE = new BillService(ConnectionToDatabase.getInstance(), DaoFactory.getInstance());
    }

    public static BillService getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<Bill> findByBooking(Booking booking) throws Exception {
        return daoFactory.getBillDao().findByBooking(booking);
    }
}
