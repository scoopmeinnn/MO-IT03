# MotorPH Employee App

This is a simple Java-based payroll management and employee app system built for the MotorPH IT03 project.

**Project Overview**
The MotorPH Employee Management System represents a Java-based desktop application specifically designed to efficiently manage employee payroll, attendance, and salary components for MotorPH. This system features a graphical user interface (GUI) developed using Java Swing, which facilitates the streamlining of various human resources tasks, including attendance logging, payroll calculation, and reporting.
MotorPH Employee App (GUI Version) – Code Documentation
 Repository Link: https://github.com/scoopmeinnn/MO-IT03.git
 
1.Branch Name: feature/gui-crud
Java Swing desktop application implementing an Employee Management System with CRUD operations for Employees, Payroll, and Leave Requests. It uses CSV files (employees.csv, payroll.csv, leaverequests.csv) for data persistence. Full Create, Read, Update, and Delete functionality is included.

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
└─ src/com/motorph/employeeapp/
   ├─ AppGUI.java              # Main launcher with login and tab panes
   ├─ LoginDialog.java        # Modal login dialog
   ├─ Department.java         # Shared model class for department
   ├─ ValidatedEmployeeGUI.java       # GUI, CRUD, CSV I/O for employees
   ├─ ValidatedPayrollGUI.java        # GUI, CRUD, CSV I/O for payroll
   └─ ValidatedLeaveRequestGUI.java   # GUI, CRUD, CSV I/O for leave requests

CSV Files (auto-created on first use):
- `employees.csv`
- `payroll.csv`
- `leaverequests.csv`





