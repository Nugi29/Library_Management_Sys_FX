package DB;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;

    @Getter
    private Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/booklib";
        String userName = "root";
        String password = "1234";
        connection = DriverManager.getConnection(URL, userName, password);
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String URL = "jdbc:mysql://localhost:3306/booklib";
            String userName = "root";
            String password = "1234";
            connection = DriverManager.getConnection(URL, userName, password);
        }
        return connection;
    }
}
