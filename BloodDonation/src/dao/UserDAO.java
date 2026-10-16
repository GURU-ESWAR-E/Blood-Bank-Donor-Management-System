package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UserDAO.java
 *
 * Data Access Object (DAO) for handling user authentication and registration.
 *
 * Role: Member 1 (Database & Persistence Engineer)
 */
public class UserDAO {

    /**
     * Authenticates a user against the users table.
     *
     * @param username The entered username.
     * @param password The entered plain/hashed password.
     * @return User object if credentials are valid and account is active, null otherwise.
     */
    public User login(String username, String password) {
        String sql = "SELECT id, username, full_name, email, role, status FROM users " +
                     "WHERE username = ? AND password_hash = ? AND status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Login authentication error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Registers a new user with their chosen role in the system.
     *
     * @param user The User object containing credentials, name, email, and role.
     * @return true if user was successfully registered, false otherwise.
     */
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, full_name, email, role, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("User registration error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a given username is already taken.
     *
     * @param username The username to check.
     * @return true if username exists, false otherwise.
     */
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Username existence check error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
