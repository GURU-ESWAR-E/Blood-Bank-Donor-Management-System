package model;

/**
 * Patient.java
 *
 * Model class representing a patient in need of blood.
 * Inherits fundamental details (id, name, phone, bloodGroup) from the Person superclass.
 */
public class Patient extends Person {

    // Default constructor
    public Patient() {
        super();
    }

    // Constructor without ID (for newly admitted patients)
    public Patient(String name, String phone, String bloodGroup) {
        super(name, phone, bloodGroup);
    }

    // Full constructor (for existing patients retrieved from records)
    public Patient(int id, String name, String phone, String bloodGroup) {
        super(id, name, phone, bloodGroup);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }
}
