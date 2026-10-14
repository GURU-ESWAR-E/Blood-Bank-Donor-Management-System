-- =====================================================================
-- Blood Bank & Donor Management System (BloodDonation)
-- Sample Seed Data Script
-- =====================================================================
-- Description:
--   Populates the 'bloodbank' tables with realistic dummy data for testing
--   and development.
--
-- How to run:
--   Option 1 (Command Line):
--     mysql -u root -p bloodbank < sql/sample_data.sql
--   Option 2 (MySQL Workbench / phpMyAdmin):
--     Open this file, ensure 'bloodbank' database is selected, and execute.
-- =====================================================================

USE bloodbank;

-- ---------------------------------------------------------------------
-- 1. Seed Users (passwords here are plain placeholders for demonstration)
-- ---------------------------------------------------------------------
INSERT INTO users (username, password_hash, role) VALUES
('admin', 'admin123', 'ADMIN'),
('staff1', 'staffpass1', 'STAFF'),
('staff2', 'staffpass2', 'STAFF');

-- ---------------------------------------------------------------------
-- 2. Seed Donors
-- ---------------------------------------------------------------------
INSERT INTO donors (full_name, phone, blood_group, last_donation_date) VALUES
('John Doe', '+1-555-0101', 'O+', '2024-01-15'),
('Jane Smith', '+1-555-0102', 'A+', '2024-02-10'),
('Robert Johnson', '+1-555-0103', 'B+', '2023-11-20'),
('Emily Davis', '+1-555-0104', 'AB-', '2024-03-01'),
('Michael Brown', '+1-555-0105', 'O-', '2023-10-05'),
('Sarah Wilson', '+1-555-0106', 'A-', '2024-02-28');

-- ---------------------------------------------------------------------
-- 3. Seed Hospitals
-- ---------------------------------------------------------------------
INSERT INTO hospitals (name, address, phone, email) VALUES
('Metro General Hospital', '100 Medical Plaza, Downtown', '+1-555-0201', 'contact@metrogeneral.org'),
('St. Jude Community Clinic', '45 Hope Avenue, Westside', '+1-555-0202', 'info@stjudeclinic.org'),
('Children\'s Specialty Hospital', '789 Care Boulevard, North Park', '+1-555-0203', 'emergency@childspecialty.org');

-- ---------------------------------------------------------------------
-- 4. Seed Blood Stock (all 8 standard blood groups)
-- ---------------------------------------------------------------------
INSERT INTO blood_stock (blood_group, units_available) VALUES
('A+', 15),
('A-', 6),
('B+', 12),
('B-', 4),
('AB+', 8),
('AB-', 3),
('O+', 22),
('O-', 5);

-- ---------------------------------------------------------------------
-- 5. Seed Requests
-- ---------------------------------------------------------------------
INSERT INTO requests (patient_name, hospital_id, blood_group, units_requested, status) VALUES
('Alice Walker', 1, 'O+', 2, 'APPROVED'),
('David Miller', 2, 'A+', 1, 'PENDING'),
('Grace Lee', 1, 'B-', 3, 'PENDING'),
('Lucas Taylor', 3, 'O-', 2, 'COMPLETED');

-- ---------------------------------------------------------------------
-- 6. Seed Donations (historical donation transactions)
-- ---------------------------------------------------------------------
INSERT INTO donations (donor_id, units_donated, donation_date) VALUES
(1, 1, '2024-01-15'),
(2, 1, '2024-02-10'),
(3, 1, '2023-11-20'),
(4, 1, '2024-03-01'),
(5, 1, '2023-10-05');
