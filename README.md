# MotorPH Employee App
A user-friendly Java Swing desktop application for managing employee payroll, attendance, and salary components at MotorPH.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Setup & Running](#setup--running)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [CSV Files](#csv-files)
- [Functionality Details](#functionality-details)
- [Edge Cases & Validation](#edge-cases--validation)
- [Recommended Improvements (Future Work)](#recommended-improvements-future-work)
- [Summary](#summary)
- [License](#license)

---

## Project Overview

The MotorPH Employee Management System is a Java-based desktop application that provides an efficient way to manage employee payroll, attendance, and salary for MotorPH. It includes a graphical user interface (GUI) for ease of use and stores data in CSV files.
https://github.com/scoopmeinnn/MO-IT03.git****
---

## Features

- Secure login system
- Employee management (CRUD)
- Payroll management (CRUD)
- Leave request management (CRUD)
- Data persistence using CSV files
- Input validation for important fields
- Friendly tabbed interface

---

## Setup & Running

**Requirements:**
- Java Development Kit (JDK) 8 or higher
- Git

**Clone and Run:**
```sh
git clone https://github.com/scoopmeinnn/MO-IT03.git
cd MO-IT03
git checkout feature/gui-crud
javac -d . motorphemployeeapp/src/com/motorph/employeeapp/*.java
java com.motorph.employeeapp.AppGUI
```

**Default Login Credentials:**
- Username: `admin`
- Password: `admin123`

---

## Usage

After logging in, you will see three tabs:

1. **Employees** - Manage employee records
2. **Payroll** - Manage payroll entries
3. **Leave Requests** - Manage employee leave requests

---

## Code Structure

```
motorphemployeeapp/
└── src/
    └── com/
        └── motorph/
            └── employeeapp/
                ├── AppGUI.java
                ├── LoginDialog.java
                ├── ValidatedEmployeeGUI.java
                ├── ValidatedPayrollGUI.java
                └── ValidatedLeaveRequestGUI.java
```
![App Screenshot](https://github.com/user-attachments/assets/28c0e429-d204-40ca-aedc-2c38dee88a78)

---

## CSV Files

These files are auto-created on first use:

- `employees.csv`
- `payroll.csv`
- `leaverequests.csv`

---

## Functionality Details

### Login

- Modal login dialog with username/password fields
- Hardcoded credentials: `admin` / `admin123`
- Success/failure feedback to user

### Employees

- Display employee data in a table
- **Add**: Append new employee; requires all fields, unique employee ID
- **Update**: Edit selected employee
- **Delete**: Remove employee after confirmation

### Payroll

- Display payroll entries
- **Add**: Append new payroll entry; validates numeric amount
- **Update/Delete**: Edit or remove entries quickly

### Leave Requests

- Display leave requests
- **Add**: Submit new request; all fields required
- **Update/Delete**: Edit or remove requests

---

## Edge Cases & Validation

- Prevents employee ID collision
- Alerts for empty required fields
- Warns if payroll amount is not a number
- CSV header recreated if missing file

---

## Recommended Improvements (Future Work)

- Replace CSV backend with a database (JDBC/SQLite/MySQL)
- Add date picker for leave dates
- Improve UI/UX and validation feedback
- Add search/filter in tables
- Support user roles (manager, employee)
- Package as deployable JAR

---

## Summary

- Complete GUI-based application with login and tabbed interface
- CRUD operations for Employees, Payroll, Leave Requests
- All code in `feature/gui-crud` branch

