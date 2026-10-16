package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 *
 * Utility class responsible for managing database connectivity using JDBC.
 * It provides a centralized method to establish a connection to the MySQL
 * 'bloodbank' database.
 *
 * For beginners:
 * 1. JDBC (Java Database Connectivity) allows Java code to talk to MySQL.
 * 2. Before running, make sure the MySQL server is running.
 * 3. Replace 'your_password_here' with your actual MySQL root password.
 * 4. Ensure 'mysql-connector-j-*.jar' is added to your project's build path/classpath.
 */
public class DBConnection {

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/bloodbank?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // MySQL password verified and configured

    // Private constructor prevents direct instantiation of this utility class
    private DBConnection() {
    }

    /**
     * Obtains a new active connection to the MySQL database.
     *
     * @return Connection object to execute SQL statements.
     * @throws SQLException if a database access error occurs or credentials are invalid.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the MySQL JDBC driver class (optional in modern JDBC, but good practice for beginners)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found! Make sure you placed mysql-connector-j jar in lib/");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Safely closes an open database connection.
     *
     * @param conn the Connection object to close.
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Helper method to quickly test whether database credentials and connectivity work.
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
