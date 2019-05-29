package connectionDao;


import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by allugard on 01.07.17.
 */
public class ConnectionToDatabase {
    static final String DATABASE_URL = "java:comp/env/jdbc/hotelDB";

    private DataSource dataSource;
   private static ThreadLocal<JdbcConnection> ThreadLocalconnection = new ThreadLocal<>();

    private ConnectionToDatabase() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup(DATABASE_URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class DoLink {
        static final ConnectionToDatabase INSTANCE = new ConnectionToDatabase();
    }

    public static ConnectionToDatabase getInstance() {
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