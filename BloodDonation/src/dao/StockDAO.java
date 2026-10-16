package dao;

import db.DBConnection;
import model.BloodStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * StockDAO.java
 *
 * Data Access Object (DAO) for the Blood Stock inventory table.
 * Handles inventory level queries, stock additions, distributions, and alerts.
 *
 * Role: Member 1 (Database & Persistence Engineer)
 */
public class StockDAO {

    /**
     * Retrieves the current inventory stock for a specific blood group.
     *
     * @param bloodGroup The blood group (e.g. "O+", "AB-").
     * @return BloodStock object with unit count, or null if not found.
     */
    public BloodStock getStockByBloodGroup(String bloodGroup) {
        String sql = "SELECT id, blood_group, units_available, last_updated FROM blood_stock WHERE blood_group = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bloodGroup);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStock(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching stock for blood group " + bloodGroup + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves stock levels across all blood groups.
     *
     * @return List containing BloodStock entries for all 8 blood groups.
     */
    public List<BloodStock> getAllStock() {
        List<BloodStock> stockList = new ArrayList<>();
        String sql = "SELECT id, blood_group, units_available, last_updated FROM blood_stock ORDER BY blood_group";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                stockList.add(mapResultSetToStock(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all stock records: " + e.getMessage());
            e.printStackTrace();
        }
        return stockList;
    }

    /**
     * Adjusts the unit count of a blood group by adding or subtracting units.
     * Positive delta increases stock (donation received), negative decreases stock (blood issued).
     * Prevents stock from falling below 0.
     *
     * @param bloodGroup The blood group to adjust.
     * @param deltaUnits The amount to add (positive) or deduct (negative).
     * @return true if updated successfully, false otherwise.
     */
    public boolean updateStockUnits(String bloodGroup, int deltaUnits) {
        // Prevent negative inventory
        String checkSql = "SELECT units_available FROM blood_stock WHERE blood_group = ?";
        String updateSql = "UPDATE blood_stock SET units_available = units_available + ? WHERE blood_group = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql);
             PreparedStatement updatePs = conn.prepareStatement(updateSql)) {

            checkPs.setString(1, bloodGroup);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    int currentUnits = rs.getInt("units_available");
                    if (currentUnits + deltaUnits < 0) {
                        System.err.println("Insufficient stock! Available: " + currentUnits + ", Requested deduction: " + Math.abs(deltaUnits));
                        return false;
                    }
                } else {
                    return false; // Blood group not found
                }
            }

            updatePs.setInt(1, deltaUnits);
            updatePs.setString(2, bloodGroup);

            return updatePs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating stock units for " + bloodGroup + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets an absolute number of units for a blood group directly.
     *
     * @param bloodGroup The blood group to set.
     * @param newUnits The new unit count (must be >= 0).
     * @return true if updated successfully, false otherwise.
     */
    public boolean setStockUnits(String bloodGroup, int newUnits) {
        if (newUnits < 0) {
            System.err.println("Units cannot be negative.");
            return false;
        }

        String sql = "UPDATE blood_stock SET units_available = ? WHERE blood_group = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newUnits);
            ps.setString(2, bloodGroup);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error setting stock units: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Finds blood groups that have stock below or equal to a minimum threshold.
     *
     * @param threshold Minimum units required.
     * @return List of BloodStock items falling below or equal to the threshold.
     */
    public List<BloodStock> getLowStockAlerts(int threshold) {
        List<BloodStock> lowStockList = new ArrayList<>();
        String sql = "SELECT id, blood_group, units_available, last_updated FROM blood_stock WHERE units_available <= ? ORDER BY units_available ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, threshold);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lowStockList.add(mapResultSetToStock(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching low stock alerts: " + e.getMessage());
            e.printStackTrace();
        }
        return lowStockList;
    }

    /**
     * Helper method to map a single ResultSet row into a BloodStock object.
     */
    private BloodStock mapResultSetToStock(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String bloodGroup = rs.getString("blood_group");
        int unitsAvailable = rs.getInt("units_available");
        Timestamp ts = rs.getTimestamp("last_updated");
        LocalDateTime lastUpdated = (ts != null) ? ts.toLocalDateTime() : null;

        return new BloodStock(id, bloodGroup, unitsAvailable, lastUpdated);
    }
}
