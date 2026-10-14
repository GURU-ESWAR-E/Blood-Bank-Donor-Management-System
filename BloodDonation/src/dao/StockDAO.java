package dao;

import model.BloodStock;
import java.util.ArrayList;
import java.util.List;

/**
 * StockDAO.java
 *
 * Data Access Object (DAO) for the Blood Stock inventory table.
 * Provides method templates for querying and updating available blood units.
 *
 * Team Member responsible: Student 1 (Database & DAOs)
 *
 * Note: Method implementations are left empty for now with TODO comments as starters.
 */
public class StockDAO {

    /**
     * Retrieves the current inventory stock for a specific blood group.
     *
     * @param bloodGroup The blood group (e.g. "O+", "AB-").
     * @return BloodStock object with unit count, or null if not found.
     */
    public BloodStock getStockByBloodGroup(String bloodGroup) {
        // TODO: Prepare SQL statement (SELECT * FROM blood_stock WHERE blood_group = ?)
        // TODO: Query the database and return mapped BloodStock object
        return null;
    }

    /**
     * Retrieves stock levels across all blood groups.
     *
     * @return List containing BloodStock entries for all 8 blood groups.
     */
    public List<BloodStock> getAllStock() {
        // TODO: Prepare SQL statement (SELECT * FROM blood_stock ORDER BY blood_group)
        // TODO: Map ResultSet rows to BloodStock objects and add to list
        List<BloodStock> stockList = new ArrayList<>();
        return stockList;
    }

    /**
     * Adjusts the unit count of a blood group by adding or subtracting units.
     * Positive delta increases stock (donation received), negative decreases stock (blood issued).
     *
     * @param bloodGroup The blood group to adjust.
     * @param deltaUnits The amount to add (positive) or deduct (negative).
     * @return true if updated successfully, false otherwise.
     */
    public boolean updateStockUnits(String bloodGroup, int deltaUnits) {
        // TODO: Prepare SQL statement (UPDATE blood_stock SET units_available = units_available + ? WHERE blood_group = ?)
        // TODO: Ensure units_available does not drop below 0
        return false;
    }

    /**
     * Sets an absolute number of units for a blood group directly.
     *
     * @param bloodGroup The blood group to set.
     * @param newUnits The new unit count.
     * @return true if updated successfully, false otherwise.
     */
    public boolean setStockUnits(String bloodGroup, int newUnits) {
        // TODO: Prepare SQL statement (UPDATE blood_stock SET units_available = ? WHERE blood_group = ?)
        return false;
    }

    /**
     * Finds blood groups that have stock below an emergency threshold.
     *
     * @param threshold Minimum units required.
     * @return List of BloodStock items falling below the threshold.
     */
    public List<BloodStock> getLowStockAlerts(int threshold) {
        // TODO: Prepare SQL statement (SELECT * FROM blood_stock WHERE units_available <= ?)
        List<BloodStock> lowStockList = new ArrayList<>();
        return lowStockList;
    }
}
