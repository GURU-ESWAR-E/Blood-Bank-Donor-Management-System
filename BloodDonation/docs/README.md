# Blood Bank & Donor Management System (`BloodDonation`)
### Team Project Architecture & Ownership Guide

---

## 1. Project Title & Overview
**Project Title**: Blood Bank & Donor Management System (`BloodDonation`)  
**Target Users**: Blood bank administrators, inventory managers, and hospital liaisons.  
**Objective**: Provide a reliable desktop application to manage blood donors, track available blood units by group, process hospital blood requests, and maintain donation histories.

---

## 2. Technology Stack

| Layer | Technology | Details |
| :--- | :--- | :--- |
| **Language** | Java (JDK 17+) | Object-oriented core logic and desktop GUI |
| **GUI Framework** | Java Swing & AWT | Standard desktop UI components (`JFrame`, `JPanel`, `JTable`) |
| **Database** | MySQL 8.0+ | Relational storage for donors, stock, requests, and users |
| **Persistence Driver** | MySQL Connector/J | Official JDBC driver (`com.mysql.cj.jdbc.Driver`) |
| **Architecture** | Layered MVC / DAO Pattern | Clear separation of Model, Data Access, UI, and DB connection |

---

## 3. Folder Guide & Structure

```
BloodDonation/
├── lib/                      # External dependencies (.jar files like MySQL Connector/J)
├── sql/                      # Database scripts
│   ├── schema.sql            # Table definitions, constraints, primary & foreign keys
│   └── sample_data.sql       # Initial seed data for development and testing
├── src/                      # Java application source code
│   ├── Main.java             # System entry point (boots Swing EDT & LoginFrame)
│   ├── db/                   # Database connection management
│   │   └── DBConnection.java # Centralized JDBC connection factory
│   ├── model/                # Domain entities (OOP classes)
│   │   ├── Person.java       # Parent class (id, name, phone, bloodGroup)
│   │   ├── Donor.java        # Subclass of Person (adds lastDonationDate)
│   │   ├── Patient.java      # Subclass of Person
│   │   ├── BloodStock.java   # Inventory representation for blood groups
│   │   └── BloodRequest.java # Hospital blood order/requisition model
│   ├── dao/                  # Data Access Object layer (SQL queries & JDBC operations)
│   │   ├── DonorDAO.java     # CRUD operations for donors
│   │   ├── StockDAO.java     # Queries & updates for blood units
│   │   └── RequestDAO.java   # Queries & status updates for blood requests
│   └── ui/                   # Graphical user interface panels and frames
│       ├── LoginFrame.java   # Top-level window for user authentication
│       ├── DonorPanel.java   # Panel for donor listings and registration
│       ├── StockPanel.java   # Panel for stock inventory & alerts
│       └── RequestPanel.java # Panel for processing blood requests
├── docs/                     # Project architecture, guidelines, and team documents
│   └── README.md             # This architecture & team ownership guide
├── .gitignore                # Git ignore rules for build artifacts & IDE configs
└── README.md                 # Setup and run instructions for developers
```

---

## 4. Team Ownership Matrix (Team of 3 Students)

To ensure smooth collaboration and prevent merge conflicts, each team member is assigned primary ownership of specific modules and directories:

| Student | Assigned Role | Primary Folders / Files Owned | Key Deliverables & Responsibilities |
| :--- | :--- | :--- | :--- |
| **Member 1** | **Database & Persistence Engineer** | `sql/`<br>`src/db/`<br>`src/dao/` | • Design and maintain `schema.sql` and `sample_data.sql`.<br>• Configure and verify `DBConnection.java`.<br>• Implement JDBC CRUD methods inside `DonorDAO`, `StockDAO`, and `RequestDAO`.<br>• Write SQL queries using `PreparedStatement` to prevent SQL injection. |
| **Member 2** | **Domain Models & Business Logic Specialist** | `src/model/`<br>Domain Validation Logic | • Maintain and enhance domain models (`Person`, `Donor`, `Patient`, `BloodStock`, `BloodRequest`).<br>• Implement business rules: donor eligibility intervals (e.g. 90-day cooldown), input validations (phone format, valid blood groups).<br>• Coordinate with Member 1 on DAO data structures and Member 3 on model representations for the UI. |
| **Member 3** | **UI/UX & Application Flow Engineer** | `src/ui/`<br>`src/Main.java` | • Design and implement Swing screens (`LoginFrame`, `DonorPanel`, `StockPanel`, `RequestPanel`).<br>• Wire action listeners, event handlers, and user input validation in UI forms.<br>• Bind `JTable` models with data fetched from Member 1's DAOs.<br>• Manage dialogs, navigation, and feedback messages for users. |

---

## 5. Collaboration Best Practices

1. **Keep Layers Decoupled**:
   - `ui` classes should never write direct SQL; they should call `dao` methods.
   - `dao` classes should return `model` objects or lists of models.
2. **Feature Branching**:
   - Each member works on their designated feature branch (e.g., `feature/donor-dao`, `feature/ui-dashboard`).
3. **Database Consistency**:
   - Any schema changes must be updated in `sql/schema.sql` and communicated to all members.
