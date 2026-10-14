package model;

/**
 * Person.java
 *
 * Base / Parent class representing any individual in the blood bank system.
 * Demonstrates Object-Oriented Programming (OOP) inheritance:
 * Common fields like id, name, phone, and bloodGroup are defined here once,
 * and shared by subclasses like Donor and Patient.
 */
public class Person {
    protected int id;
    protected String name;
    protected String phone;
    protected String bloodGroup;

    // Default constructor (required by many frameworks and useful for blank objects)
    public Person() {
    }

    // Constructor without ID (useful when creating a new person before database generates an ID)
    public Person(String name, String phone, String bloodGroup) {
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    // Full constructor (useful when loading existing records from the database)
    public Person(int id, String name, String phone, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }
}
