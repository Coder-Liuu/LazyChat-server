package chatroom.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ConnectionFactor {
    static MySQLPool mySQLPool = new MySQLPool();

    static public Connection getConnection() throws SQLException {
        return mySQLPool.dataSource.getConnection();
    }
}
