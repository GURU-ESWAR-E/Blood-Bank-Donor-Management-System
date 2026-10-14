package model;

import java.time.LocalDateTime;

/**
 * BloodStock.java
 *
 * Model class representing the inventory units available for a specific blood group.
 */
public class BloodStock {
    private int id;
    private String bloodGroup;
    private int unitsAvailable;
    private LocalDateTime lastUpdated;

    // Default constructor
    public BloodStock() {
    }

    // Constructor without ID/timestamp (for initializing stock groups)
    public BloodStock(String bloodGroup, int unitsAvailable) {
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
    }

    // Full constructor (for database records)
    public BloodStock(int id, String bloodGroup, int unitsAvailable, LocalDateTime lastUpdated) {
        this.id = id;
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
        this.lastUpdated = lastUpdated;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "BloodStock{" +
                "id=" + id +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", unitsAvailable=" + unitsAvailable +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
