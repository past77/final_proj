package ppolozhe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ppolozhe on 5/26/19.
 */
public abstract class AbstaractFuncForDao {
    protected int generateId(PreparedStatement statement) throws SQLException {
        int id = -1;
        ResultSet rs = statement.getGeneratedKeys();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }
}
