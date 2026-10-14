package model;

import java.time.LocalDate;

/**
 * Donor.java
 *
 * Model class representing a blood donor.
 * Inherits all general personal attributes from Person (id, name, phone, bloodGroup)
 * and adds specific donor attributes such as lastDonationDate.
 */
public class Donor extends Person {
    private LocalDate lastDonationDate;

    // Default constructor
    public Donor() {
        super();
    }

    // Constructor without ID (used when registering a new donor)
    public Donor(String name, String phone, String bloodGroup, LocalDate lastDonationDate) {
        super(name, phone, bloodGroup);
        this.lastDonationDate = lastDonationDate;
    }

    // Full constructor (used when retrieving a donor record from the database)
    public Donor(int id, String name, String phone, String bloodGroup, LocalDate lastDonationDate) {
        super(id, name, phone, bloodGroup);
        this.lastDonationDate = lastDonationDate;
    }

    // --- Getter and Setter ---

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", lastDonationDate=" + lastDonationDate +
                '}';
    }
}
