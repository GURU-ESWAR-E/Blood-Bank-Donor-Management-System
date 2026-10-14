package dao;

import model.BloodRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * RequestDAO.java
 *
 * Data Access Object (DAO) for handling hospital and patient blood requests.
 *
 * Team Member responsible: Student 1 (Database & DAOs)
 *
 * Note: Method implementations are left empty for now with TODO comments as starters.
 */
public class RequestDAO {

    /**
     * Creates and records a new blood request in the database.
     *
     * @param request The BloodRequest containing patient, hospital, blood group, and units.
     * @return true if created successfully, false otherwise.
     */
    public boolean addRequest(BloodRequest request) {
        // TODO: Prepare SQL statement (INSERT INTO requests (patient_name, hospital_id, blood_group, units_requested, status) VALUES (?, ?, ?, ?, ?))
        // TODO: Execute update with DBConnection PreparedStatement
        return false;
    }

    /**
     * Looks up a single blood request by its ID.
     *
     * @param id The ID of the blood request.
     * @return The BloodRequest object if found, or null otherwise.
     */
    public BloodRequest getRequestById(int id) {
        // TODO: Prepare SQL statement (SELECT r.*, h.name AS hospital_name FROM requests r LEFT JOIN hospitals h ON r.hospital_id = h.id WHERE r.id = ?)
        // TODO: Map database columns to a BloodRequest instance
        return null;
    }

    /**
     * Retrieves all blood requests in the system.
     *
     * @return A list of all BloodRequest records.
     */
    public List<BloodRequest> getAllRequests() {
        // TODO: Prepare SQL statement (SELECT r.*, h.name AS hospital_name FROM requests r LEFT JOIN hospitals h ON r.hospital_id = h.id ORDER BY r.request_date DESC)
        List<BloodRequest> requests = new ArrayList<>();
        return requests;
    }

    /**
     * Retrieves all requests matching a specific status (e.g. 'PENDING', 'APPROVED', 'REJECTED', 'COMPLETED').
     *
     * @param status The status string to filter by.
     * @return List of matching BloodRequest records.
     */
    public List<BloodRequest> getRequestsByStatus(String status) {
        // TODO: Prepare SQL statement (SELECT * FROM requests WHERE status = ?)
        List<BloodRequest> requests = new ArrayList<>();
        return requests;
    }

    /**
     * Updates the status of an existing request (e.g., from PENDING to APPROVED).
     *
     * @param id The ID of the request to update.
     * @param newStatus The new status value.
     * @return true if updated successfully, false otherwise.
     */
    public boolean updateRequestStatus(int id, String newStatus) {
        // TODO: Prepare SQL statement (UPDATE requests SET status = ? WHERE id = ?)
        return false;
    }

    /**
     * Deletes a request from the database.
     *
     * @param id The ID of the request to remove.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deleteRequest(int id) {
        // TODO: Prepare SQL statement (DELETE FROM requests WHERE id = ?)
        return false;
    }
}
