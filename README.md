# MotorPH Employee App

This is a simple Java-based payroll management and employee app system built for the MotorPH IT03 project.

**Project Overview**
The MotorPH Employee Management System represents a Java-based desktop application specifically designed to efficiently manage employee payroll, attendance, and salary components for MotorPH. This system features a graphical user interface (GUI) developed using Java Swing, which facilitates the streamlining of various human resources tasks, including attendance logging, payroll calculation, and reporting.
MotorPH Employee App (GUI Version) – Code Documentation
 Repository Link: https://github.com/scoopmeinnn/MO-IT03.git
 
1. Project Overview
A Java Swing desktop application implementing an Employee Management System with CRUD operations for Employees, Payroll, and Leave Requests. It uses CSV files (employees.csv, payroll.csv, leaverequests.csv) for data persistence. Full Create, Read, Update, and Delete functionality is included.

2. Setup & Running
Requirements:
Java Development Kit (JDK) 8 or higher


Code is on GitHub, in the branch: feature/gui-crud
How to Run:
git clone https://github.com/scoopmeinnn/MO-IT03.git
cd MO-IT03
git checkout feature/gui-crud
javac -d . motorphemployeeapp/src/com/motorph/employeeapp/*.java
java com.motorph.employeeapp.AppGUI

Login credentials:
Username: admin
Password: admin123

Once logged in, the app displays three tabs: Employees, Payroll, Leave Requests.

 Code Structure
motorphemployeeapp/
![image](https://github.com/user-attachments/assets/28c0e429-d204-40ca-aedc-2c38dee88a78)


CSV Files (auto-created on first use):
- `employees.csv`
- `payroll.csv`
- `leaverequests.csv`



Functionality Details
LoginDialog.java
Creates a modal JDialog with username/password fields:


admin/admin123 is the hard-coded credential.


Uses succeeded boolean to indicate login success.


LoginDialog.this.dispose() will close the dialog.


AppGUI.java
Launches GUI via SwingUtilities.invokeLater.


Displays 3 tabs with:


ValidatedEmployeeGUI


ValidatedPayrollGUI


ValidatedLeaveRequestGUI


Uses a shared Department instance for context.



ValidatedEmployeeGUI.java
Displays employee data in a JTable.


Add: appends a new row to employees.csv.


Update: updates selected row via popup dialog; overwrites CSV.


Delete: removes selected row from CSV after confirmation.


Validation: requires all fields, ensures employee ID is unique.


Loading: on initialization and after operations, loads CSV into table.



ValidatedPayrollGUI.java
Similar to Employees GUI, but columns: Employee ID, Amount.


Add: appends payroll entry; ensures numeric amount.


Update / Delete: quick in-place operations on selected entry.


Reloads table on data modification.



ValidatedLeaveRequestGUI.java
Columns: Employee ID, Start Date, End Date, Reason.


Submit (Add): adds new row; requires all fields.


Update: allow editing of start/end date + reason.


Delete: confirmation before removal.


CSV file reloaded after each operation.



5. Edge Cases & Validation
ID collision: prevented via popup alerts.


Empty field submission: alerts user to fill required inputs.


Invalid number in Amount (Payroll): shows a warning popup.


Date fields: user responsibility; code does not enforce date pattern but warns if empty.


Files handle both missing & existing CSVs: header is recreated if file doesn't exist.



6. Recommended Improvements (Future Work)
Replace CSV backend with a proper database via JDBC (e.g., SQLite or MySQL).


Add date picker UI for leave date selection.


Improve UI/UX with layout constraints, form clearing, input validation feedback.


Add search/filter capabilities in tables.


Support user roles—e.g., manager vs employee permissions.


Deployable JAR packaging for easy distribution.

7.  Summary
All code lives in feature/gui-crud. Main branch is untouched.


Complete GUI with login and 3 tabs.


CRUD fully implemented with data persistence.
