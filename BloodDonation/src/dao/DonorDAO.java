package dao;

import model.Donor;
import java.util.ArrayList;
import java.util.List;

/**
 * DonorDAO.java
 *
 * Data Access Object (DAO) for Donor entities.
 * The DAO pattern separates database operations (SQL queries) from application logic.
 *
 * Team Member responsible: Student 1 (Database & DAOs)
 *
 * Note: Method implementations are left empty for now with TODO comments as starters.
 */
public class DonorDAO {

    /**
     * Inserts a new donor record into the database.
     *
     * @param donor The Donor object containing name, phone, bloodGroup, lastDonationDate.
     * @return true if insertion succeeds, false otherwise.
     */
    public boolean addDonor(Donor donor) {
        // TODO: Prepare SQL INSERT statement (e.g. INSERT INTO donors (full_name, phone, blood_group, last_donation_date) VALUES (?, ?, ?, ?))
        // TODO: Use DBConnection.getConnection() and PreparedStatement to execute
        return false;
    }

    /**
     * Finds and retrieves a single donor by their primary key ID.
     *
     * @param id The donor ID to look up.
     * @return Donor object if found, or null if no record exists.
     */
    public Donor getDonorById(int id) {
        // TODO: Prepare SQL SELECT statement (SELECT * FROM donors WHERE id = ?)
        // TODO: Map ResultSet columns into a new Donor instance
        return null;
    }

    /**
     * Retrieves all donor records from the database.
     *
     * @return A List of all donors.
     */
    public List<Donor> getAllDonors() {
        // TODO: Prepare SQL SELECT statement (SELECT * FROM donors ORDER BY full_name)
        // TODO: Iterate over ResultSet and populate the list
        List<Donor> donors = new ArrayList<>();
        return donors;
    }

    /**
     * Updates an existing donor's information in the database.
     *
     * @param donor The donor object with updated values and valid ID.
     * @return true if update succeeds, false otherwise.
     */
    public boolean updateDonor(Donor donor) {
        // TODO: Prepare SQL UPDATE statement (UPDATE donors SET full_name = ?, phone = ?, blood_group = ?, last_donation_date = ? WHERE id = ?)
        // TODO: Execute update with PreparedStatement
        return false;
    }

    /**
     * Deletes a donor record by ID.
     *
     * @param id The ID of the donor to remove.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deleteDonor(int id) {
        // TODO: Prepare SQL DELETE statement (DELETE FROM donors WHERE id = ?)
        // TODO: Execute with PreparedStatement
        return false;
    }

    /**
     * Filters donors by a specific blood group (e.g., "O+", "A-").
     *
     * @param bloodGroup The blood group string to filter by.
     * @return A List of donors matching the blood group.
     */
    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        // TODO: Prepare SQL SELECT statement (SELECT * FROM donors WHERE blood_group = ?)
        List<Donor> donors = new ArrayList<>();
        return donors;
    }
}
