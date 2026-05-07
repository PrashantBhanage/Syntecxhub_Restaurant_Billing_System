import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Central MySQL connection details for this app.
 */
public class DatabaseConnection {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "syntecxhub_restaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "prashant.0927";
    private static final String SERVER_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/";
    private static final String DATABASE_URL = SERVER_URL + DATABASE;

    public static Connection getConnection() throws SQLException {
        createDatabaseIfMissing();
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    private static void createDatabaseIfMissing() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS syntecxhub_restaurant";

        try (Connection connection = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Database creation failed: " + e.getMessage(), e);
        }
    }
}
