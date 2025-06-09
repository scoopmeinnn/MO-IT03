MotorPH Employee App (GUI Version)
Overview
This is a Java Swing desktop application that allows you to manage Employees, Payroll, and Leave Requests using full Create/Read/Update/Delete (CRUD) functionality. A simple login screen secures access. All data is stored in plain CSV files:

employees.csv

payroll.csv

leaverequests.csv

Features
1. Login Screen

Username: admin

Password: admin123

If login fails or is canceled, the application exits.

2. Employees Tab

View: displays all employees (ID, Name, Position)

Add: enter ID, Name, Position and click Add

Update: select a row, click Update, edit fields, and save

Delete: select a row and click Delete to remove that employee

3. Payroll Tab

View: displays all payroll entries (Employee ID, Amount)

Add: enter Employee ID and Amount and click Add

Update: select a row, click Update, enter new amount, and save

Delete: select a row and click Delete to remove that entry

4. Leave Requests Tab

View: displays all leave requests (Employee ID, Start Date, End Date, Reason)

Submit: fill in fields and click Submit to add a request

Update: select a row, click Update, edit dates/reason, and save

Delete: select a row and click Delete to remove that request

Prerequisites
Java Development Kit (JDK) 8 or higher installed

A command-line terminal (PowerShell, CMD, bash, etc.)

How to Compile & Run
Open a terminal in the project root (where this README.md lives):
cd path/to/MO-IT03

Compile all source files:
javac -d . motorphemployeeapp/src/com/motorph/employeeapp/*.java

Run the application:
java com.motorph.employeeapp.AppGUI

Login when prompted:
Username: admin
Password: admin123

CSV Files
employees.csv

Header: ID,Name,Position

Auto-created on first use

payroll.csv

Header: EmployeeID,Amount

Auto-created on first use

leaverequests.csv

Header: EmployeeID,StartDate,EndDate,Reason

Auto-created on first use

Tip: Editing a CSV manually will be reflected on next CRUD operation.

Notes & Validation
Employee ID must be unique

Amount must be numeric (e.g. 1500.00)

Dates must follow YYYY-MM-DD format

Validation errors show via popup dialogs

Project Structure
MO-IT03/
├─ .github/
├─ motorphemployeeapp/
│ └─ src/com/motorph/employeeapp/
│ ├─ AppGUI.java
│ ├─ LoginDialog.java
│ ├─ Department.java
│ ├─ ValidatedEmployeeGUI.java
│ ├─ ValidatedPayrollGUI.java
│ └─ ValidatedLeaveRequestGUI.java
├─ employees.csv
├─ payroll.csv
├─ leaverequests.csv
└─ README.md

Author / Contact
Ghian Renzen Arboleda
lr.grarboleda@mmdc.mcl.edu.ph

Catherine Kate Plenos
lr.ckplenos@mmdc.mcl.edu.ph








