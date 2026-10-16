-- =====================================================================
-- Blood Bank & Donor Management System (BloodDonation)
-- Production-Grade Database Schema & Relational Architecture
-- =====================================================================
-- Description:
--   Comprehensive, robust MySQL schema designed for blood bank operations.
--   Fully compatible with starter Java DAOs while providing enterprise
--   capabilities:
--   1. Authentication & System Roles (users)
--   2. Donors Registry with medical vitals & eligibility (donors)
--   3. Network Hospitals Directory (hospitals)
--   4. Live Inventory Management for 8 Blood Groups (blood_stock)
--   5. Patient Requisitions & Allocation Tracking (requests)
--   6. Donation History & Bag Screening Records (donations)
--   7. Stock Transaction Audit Log (stock_transactions)
--   8. Automated Stock Triggers (trg_after_donation_insert, trg_after_request_status_update)
--   9. Dashboard Reporting Views (v_blood_stock_summary, v_active_patient_requests, v_eligible_donors_list)
-- =====================================================================

-- Step 1: Create and select database
CREATE DATABASE IF NOT EXISTS bloodbank
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE bloodbank;

-- Disable foreign key checks for clean re-creation
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS stock_transactions;
DROP TABLE IF EXISTS donations;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS blood_stock;
DROP TABLE IF EXISTS hospitals;
DROP TABLE IF EXISTS donors;
DROP TABLE IF EXISTS users;

DROP VIEW IF EXISTS v_blood_stock_summary;
DROP VIEW IF EXISTS v_active_patient_requests;
DROP VIEW IF EXISTS v_eligible_donors_list;

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================================
-- Table 1: users
-- Purpose: System authentication, role-based access, and staff management.
-- =====================================================================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL DEFAULT 'System Staff',
    email VARCHAR(100) NULL,
    role ENUM('ADMIN', 'STAFF', 'INVENTORY_MANAGER', 'LAB_TECH') NOT NULL DEFAULT 'STAFF',
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_role (role),
    INDEX idx_user_status (status)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 2: donors
-- Purpose: Profiles of registered blood donors with medical criteria.
-- Note: All advanced columns include sensible DEFAULTS so simple starter
--       queries like INSERT INTO donors (full_name, phone, blood_group, last_donation_date)
--       execute without any missing field errors.
-- =====================================================================
CREATE TABLE donors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    last_donation_date DATE NULL,
    donor_code VARCHAR(25) NULL UNIQUE,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL DEFAULT 'MALE',
    age INT NULL DEFAULT 25 CHECK (age IS NULL OR (age >= 18 AND age <= 65)),
    email VARCHAR(100) NULL,
    city VARCHAR(80) NOT NULL DEFAULT 'Chennai',
    address VARCHAR(255) NULL,
    weight_kg DECIMAL(5,2) NOT NULL DEFAULT 60.0 CHECK (weight_kg >= 45.0),
    hemoglobin_level DECIMAL(4,2) NOT NULL DEFAULT 13.5 CHECK (hemoglobin_level >= 12.0),
    eligibility_status ENUM('ELIGIBLE', 'INELIGIBLE', 'TEMPORARY_DEFERRED') NOT NULL DEFAULT 'ELIGIBLE',
    medical_notes VARCHAR(255) DEFAULT 'Medically cleared for blood donation',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_donor_blood_group (blood_group),
    INDEX idx_donor_phone (phone),
    INDEX idx_donor_eligibility (eligibility_status),
    INDEX idx_donor_last_date (last_donation_date)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 3: hospitals
-- Purpose: Registered medical centers, clinics, and emergency hospitals.
-- =====================================================================
CREATE TABLE hospitals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NULL,
    phone VARCHAR(20) NULL,
    email VARCHAR(100) NULL,
    hospital_code VARCHAR(25) NULL UNIQUE,
    hospital_type ENUM('GOVERNMENT', 'PRIVATE', 'TRUST', 'TRAUMA_CENTER') NOT NULL DEFAULT 'PRIVATE',
    license_number VARCHAR(50) NULL,
    city VARCHAR(80) NOT NULL DEFAULT 'Chennai',
    contact_person VARCHAR(100) NULL,
    emergency_hotline VARCHAR(20) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_hospital_city (city),
    INDEX idx_hospital_name (name)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 4: blood_stock
-- Purpose: Live inventory tracking for all 8 standard human blood groups.
-- =====================================================================
CREATE TABLE blood_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL UNIQUE,
    units_available INT NOT NULL DEFAULT 0 CHECK (units_available >= 0),
    min_threshold INT NOT NULL DEFAULT 10,
    storage_temp_celsius DECIMAL(4,2) NOT NULL DEFAULT 4.0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_stock_group (blood_group)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 5: requests
-- Purpose: Tracks blood requisitions placed for patients by hospitals.
-- =====================================================================
CREATE TABLE requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    hospital_id INT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    units_requested INT NOT NULL DEFAULT 1 CHECK (units_requested > 0),
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    request_code VARCHAR(25) NULL UNIQUE,
    urgency_level ENUM('ROUTINE', 'URGENT', 'CRITICAL_EMERGENCY') NOT NULL DEFAULT 'ROUTINE',
    required_date DATE NULL,
    patient_age INT NULL,
    patient_gender ENUM('MALE', 'FEMALE', 'OTHER') NULL,
    clinical_diagnosis VARCHAR(200) NULL,
    rejection_reason VARCHAR(255) NULL,
    processed_by_user_id INT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_requests_hospital
        FOREIGN KEY (hospital_id)
        REFERENCES hospitals(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_requests_user
        FOREIGN KEY (processed_by_user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    INDEX idx_request_status (status),
    INDEX idx_request_blood_group (blood_group),
    INDEX idx_request_urgency (urgency_level),
    INDEX idx_request_date (request_date)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 6: donations
-- Purpose: Records historical donation transactions made by donors.
-- =====================================================================
CREATE TABLE donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT NOT NULL,
    units_donated INT NOT NULL DEFAULT 1 CHECK (units_donated > 0),
    donation_date DATE NOT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NULL,
    donation_code VARCHAR(25) NULL UNIQUE,
    blood_bag_barcode VARCHAR(50) NULL UNIQUE,
    hemoglobin_tested DECIMAL(4,2) NULL DEFAULT 13.5,
    blood_pressure VARCHAR(20) DEFAULT '120/80',
    donation_camp_location VARCHAR(150) DEFAULT 'Main Blood Bank Center',
    screening_status ENUM('TESTED_SAFE', 'UNDER_SCREENING', 'REJECTED_INFECTIOUS') NOT NULL DEFAULT 'TESTED_SAFE',
    collected_by_user_id INT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_donations_donor
        FOREIGN KEY (donor_id)
        REFERENCES donors(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_donations_user
        FOREIGN KEY (collected_by_user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    INDEX idx_donation_date (donation_date),
    INDEX idx_donation_blood_group (blood_group),
    INDEX idx_donation_screening (screening_status)
) ENGINE=InnoDB;

-- =====================================================================
-- Table 7: stock_transactions
-- Purpose: Immutable audit log tracking every unit added or dispensed.
-- =====================================================================
CREATE TABLE stock_transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    transaction_type ENUM('DONATION_ADD', 'REQUEST_DISPENSE', 'EXPIRED_DISCARD', 'AUDIT_ADJUSTMENT') NOT NULL,
    units INT NOT NULL,
    reference_code VARCHAR(50) NULL,
    performed_by_user VARCHAR(50) DEFAULT 'SYSTEM',
    remarks VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_trans_group (blood_group),
    INDEX idx_trans_date (created_at)
) ENGINE=InnoDB;

-- =====================================================================
-- Triggers for Automatic Data Integrity
-- =====================================================================

-- Trigger 1: Automatically increment stock and update donor last donation date on new donation
DELIMITER //
CREATE TRIGGER trg_after_donation_insert
AFTER INSERT ON donations
FOR EACH ROW
BEGIN
    DECLARE bg VARCHAR(5);
    IF NEW.screening_status = 'TESTED_SAFE' THEN
        -- Resolve blood group from row or lookup from donor profile
        SET bg = COALESCE(NEW.blood_group, (SELECT blood_group FROM donors WHERE id = NEW.donor_id));
        
        IF bg IS NOT NULL THEN
            UPDATE blood_stock
            SET units_available = units_available + NEW.units_donated
            WHERE blood_group = bg;

            INSERT INTO stock_transactions (blood_group, transaction_type, units, reference_code, remarks)
            VALUES (bg, 'DONATION_ADD', NEW.units_donated, COALESCE(NEW.donation_code, CONCAT('DON-', NEW.id)), 'Added from completed blood donation');
        END IF;

        -- Update donor's last donation date
        UPDATE donors
        SET last_donation_date = NEW.donation_date
        WHERE id = NEW.donor_id;
    END IF;
END //
DELIMITER ;

-- Trigger 2: Automatically deduct blood units when a request status changes to COMPLETED
DELIMITER //
CREATE TRIGGER trg_after_request_status_update
AFTER UPDATE ON requests
FOR EACH ROW
BEGIN
    IF OLD.status <> 'COMPLETED' AND NEW.status = 'COMPLETED' THEN
        UPDATE blood_stock
        SET units_available = GREATEST(0, units_available - NEW.units_requested)
        WHERE blood_group = NEW.blood_group;

        INSERT INTO stock_transactions (blood_group, transaction_type, units, reference_code, remarks)
        VALUES (NEW.blood_group, 'REQUEST_DISPENSE', NEW.units_requested, COALESCE(NEW.request_code, CONCAT('REQ-', NEW.id)), CONCAT('Dispensed for patient: ', NEW.patient_name));
    END IF;
END //
DELIMITER ;

-- =====================================================================
-- Helpful Analytics Views for UI Dashboards
-- =====================================================================

-- View 1: Real-time Blood Stock Inventory Status
CREATE VIEW v_blood_stock_summary AS
SELECT 
    s.id,
    s.blood_group,
    s.units_available,
    s.min_threshold,
    s.storage_temp_celsius,
    CASE 
        WHEN s.units_available = 0 THEN 'OUT OF STOCK'
        WHEN s.units_available <= s.min_threshold THEN 'CRITICAL LOW'
        WHEN s.units_available <= (s.min_threshold * 1.5) THEN 'MODERATE'
        ELSE 'ADEQUATE'
    END AS inventory_status,
    s.last_updated
FROM blood_stock s;

-- View 2: Active / Pending Hospital Requisitions
CREATE VIEW v_active_patient_requests AS
SELECT 
    r.id,
    r.request_code,
    r.patient_name,
    r.patient_age,
    r.patient_gender,
    COALESCE(h.name, 'Direct Requisition') AS hospital_name,
    h.phone AS hospital_phone,
    r.blood_group,
    r.units_requested,
    r.urgency_level,
    r.status,
    r.request_date
FROM requests r
LEFT JOIN hospitals h ON r.hospital_id = h.id
WHERE r.status IN ('PENDING', 'APPROVED')
ORDER BY 
    CASE r.urgency_level 
        WHEN 'CRITICAL_EMERGENCY' THEN 1
        WHEN 'URGENT' THEN 2
        ELSE 3
    END,
    r.request_date ASC;

-- View 3: Eligible Donors (Last donation > 90 days ago or first-time donor)
CREATE VIEW v_eligible_donors_list AS
SELECT 
    id,
    donor_code,
    full_name,
    gender,
    age,
    blood_group,
    phone,
    city,
    last_donation_date,
    DATEDIFF(CURRENT_DATE, last_donation_date) AS days_since_last_donation
FROM donors
WHERE eligibility_status = 'ELIGIBLE'
  AND (last_donation_date IS NULL OR DATEDIFF(CURRENT_DATE, last_donation_date) >= 90);
