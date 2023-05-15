package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String url = "jdbc:postgresql://localhost/quiz_app_db";
    private static final String user = "quiz_app_user";
    private static final String password = "SecurePas$2";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
