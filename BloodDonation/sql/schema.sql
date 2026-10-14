-- =====================================================================
-- Blood Bank & Donor Management System (BloodDonation)
-- Database Schema Script
-- =====================================================================
-- Description:
--   This script creates the 'bloodbank' database and the essential tables
--   required for managing donors, blood inventory, hospital requests,
--   donation records, and system user authentication.
--
-- How to run:
--   Option 1 (Command Line):
--     mysql -u root -p < sql/schema.sql
--   Option 2 (MySQL Workbench / phpMyAdmin):
--     Open this file in the query editor and click 'Execute'.
-- =====================================================================

-- Step 1: Create and select the database
CREATE DATABASE IF NOT EXISTS bloodbank
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE bloodbank;

-- =====================================================================
-- Table 1: users
-- Purpose: Stores login credentials for system administrators & staff.
-- =====================================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'STAFF') NOT NULL DEFAULT 'STAFF',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- Table 2: donors
-- Purpose: Stores donor profile details and their last donation date.
-- =====================================================================
CREATE TABLE IF NOT EXISTS donors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    last_donation_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- Table 3: hospitals
-- Purpose: Stores registered hospitals that can submit blood requests.
-- =====================================================================
CREATE TABLE IF NOT EXISTS hospitals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NULL,
    phone VARCHAR(20) NULL,
    email VARCHAR(100) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- Table 4: blood_stock
-- Purpose: Keeps track of current blood inventory units for each blood group.
-- =====================================================================
CREATE TABLE IF NOT EXISTS blood_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL UNIQUE,
    units_available INT NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- Table 5: requests
-- Purpose: Tracks blood requisitions placed for patients by hospitals.
-- Foreign Key: hospital_id references hospitals(id)
-- =====================================================================
CREATE TABLE IF NOT EXISTS requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    hospital_id INT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    units_requested INT NOT NULL DEFAULT 1,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_requests_hospital
        FOREIGN KEY (hospital_id)
        REFERENCES hospitals(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
-- Table 6: donations
-- Purpose: Records historical donation transactions made by donors.
-- Foreign Key: donor_id references donors(id)
-- =====================================================================
CREATE TABLE IF NOT EXISTS donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT NOT NULL,
    units_donated INT NOT NULL DEFAULT 1,
    donation_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_donations_donor
        FOREIGN KEY (donor_id)
        REFERENCES donors(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;
