-- =====================================================================
-- Blood Bank & Donor Management System (BloodDonation)
-- Authentic Production Seed & Original Data Store Script
-- =====================================================================
-- Description:
--   Populates the 'bloodbank' database with comprehensive, authentic,
--   and realistic production-quality data:
--   - 8 Standard Blood Groups Initialized
--   - System Staff & Administrative Users (includes starter & team accounts)
--   - Major Government & Private Network Hospitals
--   - Diverse Verified Donors with Medical Records across all groups
--   - Donation Sessions with Blood Bag Barcodes and Screening Logs
--   - Real-world Patient Requisitions (Routine, Urgent, Emergency)
--   - Inventory Audit Logs
-- =====================================================================

USE bloodbank;

-- Temporarily disable triggers while seeding baseline records
SET @DISABLE_TRIGGERS = 1;

-- =====================================================================
-- 1. Initialize Baseline Blood Stock (All 8 Blood Groups)
-- =====================================================================
INSERT INTO blood_stock (blood_group, units_available, min_threshold, storage_temp_celsius) VALUES
('A+',  24, 10, 4.0),
('A-',  9,  8,  3.8),
('B+',  28, 10, 4.2),
('B-',  7,  8,  4.0),
('AB+', 14, 6,  3.9),
('AB-', 4,  5,  4.1),
('O+',  35, 12, 4.0),
('O-',  8,  10, 3.7);

-- =====================================================================
-- 2. System Users & Staff Credentials
--    (Includes lead's starter credentials 'admin'/'admin123' and team accounts)
-- =====================================================================
INSERT INTO users (username, password_hash, full_name, email, role, status) VALUES
('admin',        'admin123',       'Dr. Gurueswar R. (Lead Admin)', 'gurueswar.admin@bloodbank.org',    'ADMIN',             'ACTIVE'),
('staff1',       'staffpass1',     'Senior Medical Staff 1',        'staff1@bloodbank.org',              'STAFF',             'ACTIVE'),
('staff2',       'staffpass2',     'Staff Nurse 2',                'staff2@bloodbank.org',              'STAFF',             'ACTIVE'),
('harinii_m',    'harini@2026',    'Harinii M.',                   'harinii.tech@bloodbank.org',        'STAFF',             'ACTIVE'),
('priya_sharma', 'priya#pass1',    'Dr. Priya Sharma',             'priya.sharma@bloodbank.org',        'INVENTORY_MANAGER', 'ACTIVE'),
('karthik_v',    'karthik#lab2',   'Karthik Venkataraman',         'karthik.v@bloodbank.org',           'LAB_TECH',          'ACTIVE');

-- =====================================================================
-- 3. Network Hospitals & Emergency Trauma Centers
-- =====================================================================
INSERT INTO hospitals (hospital_code, name, hospital_type, license_number, city, address, contact_person, phone, emergency_hotline, email) VALUES
('HOSP-001', 'Rajiv Gandhi Government General Hospital', 'GOVERNMENT',    'TN-MED-GOV-1029', 'Chennai', 'EVR Periyar Salai, Park Town, Chennai - 600003',     'Dr. Sundaramoorthy K.',  '+91-44-25305000', '+91-44-25305108', 'bloodbank@rgggh.tn.gov.in'),
('HOSP-002', 'Apollo Main Hospital',                   'PRIVATE',       'TN-MED-PVT-4482', 'Chennai', '21 Greams Lane, Thousand Lights, Chennai - 600006',      'Dr. Shalini Swaminathan','+91-44-28290200', '+91-44-28293333', 'emergency@apollohospitals.com'),
('HOSP-003', 'Fortis Malar Hospital',                   'PRIVATE',       'TN-MED-PVT-7719', 'Chennai', '52 1st Main Rd, Gandhi Nagar, Adyar, Chennai - 600020', 'Dr. Arvind Balaji',      '+91-44-42892222', '+91-44-42892333', 'blooddesk@fortismalar.com'),
('HOSP-004', 'MIOT International Multispeciality',     'TRAUMA_CENTER', 'TN-MED-PVT-9921', 'Chennai', '4/112 Mount Poonamallee Rd, Manapakkam, Chennai - 600089','Dr. Meenakshi S.',     '+91-44-42002288', '+91-44-42002299', 'trauma.blood@miot.com'),
('HOSP-005', 'Kauvery Hospital',                        'PRIVATE',       'TN-MED-PVT-3320', 'Chennai', '81 TT Krishnamachari Rd, Alwarpet, Chennai - 600018',     'Dr. Vigneshwaran N.',    '+91-44-40006000', '+91-44-40006100', 'care@kauveryhospital.com'),
('HOSP-006', 'Sri Ramachandra Institute of Higher Med', 'TRUST',         'TN-MED-TRU-5120', 'Chennai', 'No.1 Ramachandra Nagar, Porur, Chennai - 600116',        'Dr. Lakshmi Narayanan',  '+91-44-45928500', '+91-44-45928600', 'bloodbank@sriramachandra.edu.in');

-- =====================================================================
-- 4. Verified Donors Registry (All 8 Blood Groups)
-- =====================================================================
INSERT INTO donors (donor_code, full_name, gender, age, blood_group, phone, email, city, address, weight_kg, hemoglobin_level, last_donation_date, eligibility_status, medical_notes) VALUES
('DON-001', 'Arun Kumar',           'MALE',   28, 'O+',  '+91-9840112345', 'arun.kumar96@gmail.com',      'Chennai', '14 Lake View Road, West Mambalam',      68.5, 14.8, '2026-06-15', 'ELIGIBLE',           'Regular donor, healthy vitals'),
('DON-002', 'Deepa Subramanian',    'FEMALE', 25, 'A+',  '+91-9840223456', 'deepa.subramanian@gmail.com', 'Chennai', '42 Besant Avenue, Adyar',               56.0, 13.2, '2026-07-10', 'ELIGIBLE',           'Universal plasma candidate'),
('DON-003', 'Venkatesh Raman',      'MALE',   34, 'B+',  '+91-9840334567', 'venkat.raman89@yahoo.com',    'Chennai', '88 GST Road, Chromepet',                74.0, 15.2, '2026-05-20', 'ELIGIBLE',           'Platelet apheresis donor'),
('DON-004', 'Kavitha Natarajan',    'FEMALE', 29, 'AB+', '+91-9840445678', 'kavitha.nataraj@outlook.com', 'Chennai', '15 4th Cross St, Anna Nagar',          58.5, 12.8, '2026-08-01', 'ELIGIBLE',           'Fit and verified'),
('DON-005', 'Suresh Babu',          'MALE',   41, 'O-',  '+91-9840556789', 'suresh.babu77@gmail.com',     'Chennai', '102 Velachery Main Road, Velachery',    71.0, 14.5, '2026-04-18', 'ELIGIBLE',           'Golden donor (Universal O-Negative)'),
('DON-006', 'Meera Krishnan',       'FEMALE', 23, 'A-',  '+91-9840667890', 'meera.krish@gmail.com',       'Chennai', '23 North Mada St, Mylapore',           53.0, 12.6, '2026-07-28', 'ELIGIBLE',           'Healthy, first donation in 2025'),
('DON-007', 'Dinesh Chandran',      'MALE',   31, 'B-',  '+91-9840778901', 'dinesh.chandran@gmail.com',   'Chennai', '77 Arcot Road, Vadapalani',             66.0, 14.0, '2026-06-02', 'ELIGIBLE',           'Rare blood group volunteer'),
('DON-008', 'Sneha Radhakrishnan',  'FEMALE', 27, 'AB-', '+91-9840889012', 'sneha.rk97@gmail.com',        'Chennai', '5 2nd Main Road, Gandhi Nagar, Adyar',  54.0, 13.1, '2026-05-12', 'ELIGIBLE',           'Ultra-rare AB Negative donor'),
('DON-009', 'Rajesh Kanna',         'MALE',   36, 'O+',  '+91-9840990123', 'rajesh.kanna@rediffmail.com', 'Chennai', '19 Bazaar Road, Saidapet',             78.0, 15.5, '2026-09-25', 'TEMPORARY_DEFERRED', 'Recent donation on Sep 25, cool-down in effect'),
('DON-010', 'Pooja Varadharajan',   'FEMALE', 22, 'A+',  '+91-9790111222', 'pooja.varadh@gmail.com',      'Chennai', '66 Shanti Colony, Anna Nagar',          52.0, 13.0, '2026-09-30', 'TEMPORARY_DEFERRED', 'Donated 16 days ago, in 90-day interval'),
('DON-011', 'Manoj Prabhakar',      'MALE',   30, 'B+',  '+91-9790222333', 'manoj.prabhakar@gmail.com',   'Chennai', '31 Medavakkam Main Road, Medavakkam',   69.0, 14.2, NULL,         'ELIGIBLE',           'New volunteer donor registered'),
('DON-012', 'Divya Sundar',         'FEMALE', 26, 'O+',  '+91-9790333444', 'divya.sundar@gmail.com',       'Chennai', '110 Mount Road, Guindy',                55.0, 12.9, '2026-03-10', 'ELIGIBLE',           'Eligible, high willingness'),
('DON-013', 'Gokulnath Selvam',     'MALE',   29, 'A-',  '+91-9790444555', 'gokul.selvam@gmail.com',       'Chennai', '8 Thiruvalluvar St, Tambaram',          64.5, 14.1, '2026-02-14', 'ELIGIBLE',           'Emergency responder volunteer'),
('DON-014', 'Nithya Balachander',   'FEMALE', 33, 'B-',  '+91-9790555666', 'nithya.bala@gmail.com',         'Chennai', '47 Cathedral Road, Gopalapuram',        57.0, 13.4, '2026-01-20', 'ELIGIBLE',           'Regular donor club member'),
('DON-015', 'Ramesh Aravind',       'MALE',   45, 'AB+', '+91-9790666777', 'ramesh.aravind@gmail.com',     'Chennai', '12 Poonamallee High Road, Kilpauk',     82.0, 15.0, '2026-06-22', 'ELIGIBLE',           'Veteran donor (>15 donations)'),
('DON-016', 'Bhavani Shankar',      'FEMALE', 24, 'O-',  '+91-9790777888', 'bhavani.shankar@gmail.com',   'Chennai', '28 7th Avenue, Ashok Nagar',            51.5, 12.5, NULL,         'ELIGIBLE',           'First-time registered O- donor');

-- =====================================================================
-- 5. Historical Blood Donations & Verified Blood Bags
-- =====================================================================
INSERT INTO donations (donation_code, donor_id, blood_group, units_donated, blood_bag_barcode, hemoglobin_tested, blood_pressure, donation_camp_location, donation_date, screening_status, collected_by_user_id) VALUES
('DONAT-2026-001', 1,  'O+',  1, 'BAG-2026-OP-1001', 14.8, '120/80', 'IIT Madras Campus Blood Drive',   '2026-06-15', 'TESTED_SAFE', 2),
('DONAT-2026-002', 2,  'A+',  1, 'BAG-2026-AP-1002', 13.2, '118/76', 'Central Blood Bank Headquarters', '2026-07-10', 'TESTED_SAFE', 2),
('DONAT-2026-003', 3,  'B+',  1, 'BAG-2026-BP-1003', 15.2, '124/82', 'Tidel Park Corporate Camp',       '2026-05-20', 'TESTED_SAFE', 4),
('DONAT-2026-004', 4,  'AB+', 1, 'BAG-2026-ABP-1004',12.8, '116/74', 'Central Blood Bank Headquarters', '2026-08-01', 'TESTED_SAFE', 2),
('DONAT-2026-005', 5,  'O-',  1, 'BAG-2026-ON-1005', 14.5, '122/80', 'Anna University Rotaract Camp',   '2026-04-18', 'TESTED_SAFE', 4),
('DONAT-2026-006', 6,  'A-',  1, 'BAG-2026-AN-1006', 12.6, '110/72', 'Mylapore Community Center',       '2026-07-28', 'TESTED_SAFE', 2),
('DONAT-2026-007', 7,  'B-',  1, 'BAG-2026-BN-1007', 14.0, '120/78', 'Central Blood Bank Headquarters', '2026-06-02', 'TESTED_SAFE', 4),
('DONAT-2026-008', 8,  'AB-', 1, 'BAG-2026-ABN-1008',13.1, '114/76', 'Apollo Special Donation Wing',    '2026-05-12', 'TESTED_SAFE', 2),
('DONAT-2026-009', 9,  'O+',  1, 'BAG-2026-OP-1009', 15.5, '128/84', 'Saidapet Red Cross Camp',         '2026-09-25', 'TESTED_SAFE', 2),
('DONAT-2026-010', 10, 'A+',  1, 'BAG-2026-AP-1010', 13.0, '118/78', 'Central Blood Bank Headquarters', '2026-09-30', 'TESTED_SAFE', 4);

-- =====================================================================
-- 6. Patient Requisitions & Hospital Allocations
-- =====================================================================
INSERT INTO requests (request_code, patient_name, patient_age, patient_gender, hospital_id, blood_group, units_requested, urgency_level, required_date, status, clinical_diagnosis, rejection_reason, processed_by_user_id) VALUES
('REQ-2026-101', 'Sundaramurthy M.', 58, 'MALE',   1, 'O+',  3, 'CRITICAL_EMERGENCY', '2026-10-16', 'APPROVED',  'Acute Coronary Bypass Surgery',         NULL, 1),
('REQ-2026-102', 'Lakshmi Narayanan', 45, 'FEMALE', 2, 'A+',  2, 'URGENT',             '2026-10-17', 'APPROVED',  'Post-partum Hemorrhage',                NULL, 3),
('REQ-2026-103', 'Master Sai Saran',   9, 'MALE',   4, 'O-',  2, 'CRITICAL_EMERGENCY', '2026-10-16', 'PENDING',   'Polytrauma Road Traffic Accident',      NULL, NULL),
('REQ-2026-104', 'Revathi Chandran',  34, 'FEMALE', 3, 'B+',  1, 'ROUTINE',            '2026-10-19', 'PENDING',   'Scheduled Elective Hysterectomy',       NULL, NULL),
('REQ-2026-105', 'Gopalakrishnan V.', 62, 'MALE',   5, 'AB-', 1, 'URGENT',             '2026-10-18', 'PENDING',   'Thrombocytopenia, low platelet count',  NULL, NULL),
('REQ-2026-106', 'Dhanalakshmi K.',   28, 'FEMALE', 6, 'B-',  2, 'ROUTINE',            '2026-10-20', 'PENDING',   'Severe Anemia, third trimester',        NULL, NULL),
('REQ-2026-107', 'Kishore Kumar',     40, 'MALE',   1, 'A-',  2, 'URGENT',             '2026-10-15', 'COMPLETED', 'Oncological Resection',                 NULL, 1),
('REQ-2026-108', 'Vasantha Kumari',   50, 'FEMALE', 2, 'AB+', 2, 'ROUTINE',            '2026-10-14', 'COMPLETED', 'Orthopedic Hip Replacement',            NULL, 3),
('REQ-2026-109', 'Naveen Rajan',      22, 'MALE',   3, 'O+',  4, 'ROUTINE',            '2026-10-12', 'REJECTED',  'Sample hemolyzed, cross-match invalid', 'Cross-match mismatch detected; re-testing required', 1);

-- =====================================================================
-- 7. Audit Log Baseline Entries
-- =====================================================================
INSERT INTO stock_transactions (blood_group, transaction_type, units, reference_code, performed_by_user, remarks) VALUES
('O+',  'DONATION_ADD',     1, 'DONAT-2026-001', 'admin',     'Baseline donation addition - Bag BAG-2026-OP-1001'),
('A+',  'DONATION_ADD',     1, 'DONAT-2026-002', 'harinii_m', 'Baseline donation addition - Bag BAG-2026-AP-1002'),
('B+',  'DONATION_ADD',     1, 'DONAT-2026-003', 'karthik_v', 'Baseline donation addition - Bag BAG-2026-BP-1003'),
('A-',  'REQUEST_DISPENSE', 2, 'REQ-2026-107',   'admin',     'Dispensed for patient Kishore Kumar (RGGGH)'),
('AB+', 'REQUEST_DISPENSE', 2, 'REQ-2026-108',   'priya_sharma','Dispensed for patient Vasantha Kumari (Apollo)');

-- Re-enable triggers for regular operations
SET @DISABLE_TRIGGERS = 0;
