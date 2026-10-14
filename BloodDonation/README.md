# Blood Bank & Donor Management System (`BloodDonation`)
## Starter Project Skeleton

A desktop application skeleton built using **Java (Swing/AWT)** and **MySQL (JDBC)** for a team of 3 students.

---

## 📋 Prerequisites

Before setting up the project, make sure you have the following installed on your computer:
1. **Java Development Kit (JDK 17 or higher)**
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [Eclipse Adoptium Temurin](https://adoptium.net/)
   - Verify in terminal: `javac -version` and `java -version`
2. **MySQL Server (v8.0 or higher)**
   - Download: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
3. **IDE (Choose one for your team)**:
   - VS Code (with "Extension Pack for Java")
   - IntelliJ IDEA Community Edition
   - Eclipse IDE for Java Developers

---

## 🚀 Setup Steps

### Step 1: Install MySQL & Start the Service
1. Install MySQL Server and remember the password you set for the `root` user during installation.
2. Ensure the MySQL service is running:
   - On Windows: Press `Win + R`, type `services.msc`, locate **MySQL80** (or similar), and ensure it is "Running".

---

### Step 2: Create the Database & Seed Sample Data
Open your terminal / command prompt or MySQL Workbench and execute the SQL scripts located in the `sql/` folder:

#### Option A: Using the MySQL Command Line Client
```bash
# 1. Navigate to the BloodDonation directory
cd BloodDonation

# 2. Run the schema creation script
mysql -u root -p < sql/schema.sql

# 3. Populate sample dummy data
mysql -u root -p < sql/sample_data.sql
```
*(Enter your MySQL root password when prompted).*

#### Option B: Using MySQL Workbench
1. Open MySQL Workbench and connect to your local MySQL instance.
2. Go to **File -> Open SQL Script...** and select `sql/schema.sql`.
3. Click the ⚡ **Execute** icon to run the script.
4. Repeat the same for `sql/sample_data.sql`.
5. Refresh the Schemas pane to confirm the `bloodbank` database and tables are visible.

---

### Step 3: Download and Add MySQL Connector/J JAR
The Java application requires the MySQL JDBC driver to communicate with the database.

1. Download **MySQL Connector/J** (Platform Independent zip/tar):
   - Official Link: [MySQL Connector/J Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Extract the archive and copy the `.jar` file (e.g., `mysql-connector-j-8.3.0.jar`) into the `BloodDonation/lib/` folder.
3. Configure the JAR in your IDE:
   - **VS Code**: In the "Java Projects" panel at the bottom left, expand **Referenced Libraries** -> click **+** -> select `lib/mysql-connector-j-*.jar`.
   - **IntelliJ IDEA**: Go to **File -> Project Structure -> Modules -> Dependencies -> + (JARs or Directories)** -> select `lib/mysql-connector-j-*.jar`.
   - **Eclipse**: Right-click project -> **Build Path -> Configure Build Path... -> Libraries -> Add JARs...** -> select the jar in `lib/`.

---

### Step 4: Configure Database Connection
Open [`src/db/DBConnection.java`](file:///BloodDonation/src/db/DBConnection.java) and verify your connection parameters:
```java
private static final String URL = "jdbc:mysql://localhost:3306/bloodbank?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password_here"; // Replace with your actual MySQL password
```

---

### Step 5: Compile and Run `Main.java`

#### Running from your IDE:
- Open [`src/Main.java`](file:///BloodDonation/src/Main.java).
- Right-click and choose **Run 'Main.main()'** (or click the green Run button).
- The `LoginFrame` window will appear on screen.

#### Running from Command Line:
```bash
# Navigate into the BloodDonation directory
cd BloodDonation

# Compile all Java source files (on Windows PowerShell)
javac -cp "lib/*;src" -d out (Get-ChildItem -Path src -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)

# Run the Main class
java -cp "out;lib/*" Main
```

---

## 👥 Team Collaboration & Folder Ownership
For detailed documentation on which student is responsible for each folder and module, refer to:
📄 [docs/README.md](docs/README.md)
