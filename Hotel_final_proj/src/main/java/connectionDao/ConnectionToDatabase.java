package connectionDao;

import org.apache.log4j.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by allugard on 01.07.17.
 */
public class ConnectionToDatabase {
    private static final Logger LOGGER = Logger.getLogger(ConnectionToDatabase.class);
    static final String DATABASE_URL = "jdbc/hotel";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel" +
            "?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";

    private static BasicDataSource dataSource;
   // private DataSource dataSource;
   private static ThreadLocal<JdbcConnection> ThreadLocalconnection = new ThreadLocal<>();

    private ConnectionToDatabase() {
        try {
            if (dataSource == null) {
                dataSource = new BasicDataSource();
                 dataSource.setUrl(DB_URL);
                dataSource.setUsername(USER);
                dataSource.setPassword(PASSWORD);
                dataSource.setMinIdle(0);
                dataSource.setMaxIdle(20);
                dataSource.setMaxTotal(20);
                dataSource.setInitialSize(0);
            }
//            InitialContext initialContext = new InitialContext();
//            Context env  = (Context) initialContext.lookup("java:/comp/env");
//            dataSource = (DataSource) env.lookup(DATABASE_URL);
            LOGGER.info(ConnectionToDatabase.class.toString() +"happy");
        } catch (Exception e) {
            LOGGER.info(ConnectionToDatabase.class.toString() + "connection is not ok" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static class DoLink {
        static final ConnectionToDatabase INSTANCE = new ConnectionToDatabase();
    }

    public static ConnectionToDatabase getInstance() {
        LOGGER.info("CONNECTION INSTANCE");
        return DoLink.INSTANCE;
    }

    public synchronized JdbcConnection getConnection() {
        if(ThreadLocalconnection.get() == null) {
            try {
                return new JdbcConnection(dataSource.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ThreadLocalconnection.get();
        }
    }

}