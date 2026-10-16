package dao;

import db.DBConnection;
import model.BloodRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * RequestDAO.java
 *
 * Data Access Object (DAO) for handling hospital and patient blood requests.
 * Executes database operations with hospital join queries to display hospital names.
 *
 * Role: Member 1 (Database & Persistence Engineer)
 */
public class RequestDAO {

    /**
     * Creates and records a new blood request in the database.
     *
     * @param request The BloodRequest containing patient, hospital, blood group, and units.
     * @return true if created successfully, false otherwise.
     */
    public boolean addRequest(BloodRequest request) {
        String sql = "INSERT INTO requests (patient_name, hospital_id, blood_group, units_requested, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, request.getPatientName());

            if (request.getHospitalId() > 0) {
                ps.setInt(2, request.getHospitalId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, request.getBloodGroup());
            ps.setInt(4, request.getUnitsRequested());

            String status = request.getStatus();
            if (status == null || status.trim().isEmpty()) {
                status = "PENDING";
            }
            ps.setString(5, status);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        request.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding blood request: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Looks up a single blood request by its ID.
     *
     * @param id The ID of the blood request.
     * @return The BloodRequest object if found, or null otherwise.
     */
    public BloodRequest getRequestById(int id) {
        String sql = "SELECT r.id, r.patient_name, r.hospital_id, COALESCE(h.name, 'Direct') AS hospital_name, " +
                     "r.blood_group, r.units_requested, r.status, r.request_date " +
                     "FROM requests r " +
                     "LEFT JOIN hospitals h ON r.hospital_id = h.id " +
                     "WHERE r.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRequest(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching request ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all blood requests in the system with their associated hospital names.
     *
     * @return A list of all BloodRequest records.
     */
    public List<BloodRequest> getAllRequests() {
        List<BloodRequest> requests = new ArrayList<>();
        String sql = "SELECT r.id, r.patient_name, r.hospital_id, COALESCE(h.name, 'Direct') AS hospital_name, " +
                     "r.blood_group, r.units_requested, r.status, r.request_date " +
                     "FROM requests r " +
                     "LEFT JOIN hospitals h ON r.hospital_id = h.id " +
                     "ORDER BY r.request_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all blood requests: " + e.getMessage());
            e.printStackTrace();
        }
        return requests;
    }

    /**
     * Retrieves all requests matching a specific status (e.g. 'PENDING', 'APPROVED', 'REJECTED', 'COMPLETED').
     *
     * @param status The status string to filter by.
     * @return List of matching BloodRequest records.
     */
    public List<BloodRequest> getRequestsByStatus(String status) {
        List<BloodRequest> requests = new ArrayList<>();
        String sql = "SELECT r.id, r.patient_name, r.hospital_id, COALESCE(h.name, 'Direct') AS hospital_name, " +
                     "r.blood_group, r.units_requested, r.status, r.request_date " +
                     "FROM requests r " +
                     "LEFT JOIN hospitals h ON r.hospital_id = h.id " +
                     "WHERE r.status = ? " +
                     "ORDER BY r.request_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRequest(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtering requests by status: " + e.getMessage());
            e.printStackTrace();
        }
        return requests;
    }

    /**
     * Updates the status of an existing request (e.g., from PENDING to APPROVED or COMPLETED).
     *
     * @param id The ID of the request to update.
     * @param newStatus The new status value ('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED').
     * @return true if updated successfully, false otherwise.
     */
    public boolean updateRequestStatus(int id, String newStatus) {
        String sql = "UPDATE requests SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating request status for ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a request from the database.
     *
     * @param id The ID of the request to remove.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM requests WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting request ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map a single ResultSet row into a BloodRequest object.
     */
    private BloodRequest mapResultSetToRequest(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String patientName = rs.getString("patient_name");
        int hospitalId = rs.getInt("hospital_id");
        String hospitalName = rs.getString("hospital_name");
        String bloodGroup = rs.getString("blood_group");
        int unitsRequested = rs.getInt("units_requested");
        String status = rs.getString("status");
        Timestamp ts = rs.getTimestamp("request_date");
        LocalDateTime requestDate = (ts != null) ? ts.toLocalDateTime() : null;

        return new BloodRequest(id, patientName, hospitalId, hospitalName, bloodGroup, unitsRequested, status, requestDate);
    }
}
