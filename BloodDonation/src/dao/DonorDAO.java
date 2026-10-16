package dao;

import db.DBConnection;
import model.Donor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DonorDAO.java
 *
 * Data Access Object (DAO) for Donor entities.
 * Handles complete CRUD (Create, Read, Update, Delete) database operations
 * using secure PreparedStatements to prevent SQL injection.
 *
 * Role: Member 1 (Database & Persistence Engineer)
 */
public class DonorDAO {

    /**
     * Inserts a new donor record into the database.
     *
     * @param donor The Donor object containing name, phone, bloodGroup, lastDonationDate.
     * @return true if insertion succeeds, false otherwise.
     */
    public boolean addDonor(Donor donor) {
        String sql = "INSERT INTO donors (full_name, phone, blood_group, last_donation_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, donor.getName());
            ps.setString(2, donor.getPhone());
            ps.setString(3, donor.getBloodGroup());

            if (donor.getLastDonationDate() != null) {
                ps.setDate(4, Date.valueOf(donor.getLastDonationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        donor.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding donor: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Finds and retrieves a single donor by their primary key ID.
     *
     * @param id The donor ID to look up.
     * @return Donor object if found, or null if no record exists.
     */
    public Donor getDonorById(int id) {
        String sql = "SELECT id, full_name, phone, blood_group, last_donation_date FROM donors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDonor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching donor by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all donor records from the database.
     *
     * @return A List of all donors.
     */
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT id, full_name, phone, blood_group, last_donation_date FROM donors ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                donors.add(mapResultSetToDonor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all donors: " + e.getMessage());
            e.printStackTrace();
        }
        return donors;
    }

    /**
     * Updates an existing donor's information in the database.
     *
     * @param donor The donor object with updated values and valid ID.
     * @return true if update succeeds, false otherwise.
     */
    public boolean updateDonor(Donor donor) {
        String sql = "UPDATE donors SET full_name = ?, phone = ?, blood_group = ?, last_donation_date = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, donor.getName());
            ps.setString(2, donor.getPhone());
            ps.setString(3, donor.getBloodGroup());

            if (donor.getLastDonationDate() != null) {
                ps.setDate(4, Date.valueOf(donor.getLastDonationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setInt(5, donor.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating donor ID " + donor.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a donor record by ID.
     *
     * @param id The ID of the donor to remove.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deleteDonor(int id) {
        String sql = "DELETE FROM donors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting donor ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Filters donors by a specific blood group (e.g., "O+", "A-").
     *
     * @param bloodGroup The blood group string to filter by.
     * @return A List of donors matching the blood group.
     */
    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT id, full_name, phone, blood_group, last_donation_date FROM donors WHERE blood_group = ? ORDER BY full_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bloodGroup);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    donors.add(mapResultSetToDonor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtering donors by blood group: " + e.getMessage());
            e.printStackTrace();
        }
        return donors;
    }

    /**
     * Helper method to map a single ResultSet row into a Donor object.
     */
    private Donor mapResultSetToDonor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("full_name");
        String phone = rs.getString("phone");
        String bloodGroup = rs.getString("blood_group");
        Date date = rs.getDate("last_donation_date");
        LocalDate lastDonationDate = (date != null) ? date.toLocalDate() : null;

        return new Donor(id, name, phone, bloodGroup, lastDonationDate);
    }
}
