package model;

import java.time.LocalDateTime;

/**
 * BloodRequest.java
 *
 * Model class representing a requisition for blood units submitted by or on behalf of a patient.
 */
public class BloodRequest {
    private int id;
    private String patientName;
    private int hospitalId;
    private String hospitalName; // Optional helper field for UI display
    private String bloodGroup;
    private int unitsRequested;
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED
    private LocalDateTime requestDate;

    // Default constructor
    public BloodRequest() {
    }

    // Constructor without ID/date (for creating a new blood request)
    public BloodRequest(String patientName, int hospitalId, String bloodGroup, int unitsRequested, String status) {
        this.patientName = patientName;
        this.hospitalId = hospitalId;
        this.bloodGroup = bloodGroup;
        this.unitsRequested = unitsRequested;
        this.status = status;
    }

    // Full constructor (for database records)
    public BloodRequest(int id, String patientName, int hospitalId, String hospitalName,
                        String bloodGroup, int unitsRequested, String status, LocalDateTime requestDate) {
        this.id = id;
        this.patientName = patientName;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.bloodGroup = bloodGroup;
        this.unitsRequested = unitsRequested;
        this.status = status;
        this.requestDate = requestDate;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getUnitsRequested() {
        return unitsRequested;
    }

    public void setUnitsRequested(int unitsRequested) {
        this.unitsRequested = unitsRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "BloodRequest{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", unitsRequested=" + unitsRequested +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}
