package ppolozhe.connectionDao;

import ppolozhe.jdbcDaoRealization.JdbcBookingDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection implements AutoCloseable{
    private static final Logger LOGGER = Logger.getLogger(JdbcBookingDao.class);
    private Connection connection;
    private boolean inTransaction;

    public JdbcConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public PreparedStatement prepareStatement (String sql,int autoGeneratedKeys) throws SQLException {
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (!inTransaction) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }


    }
}
