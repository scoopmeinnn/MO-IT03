/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorphemployeeapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kate
 */
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MotorPHGUI extends JFrame {

    private final JTextField txtEmpNumber;

    private final JTextField txtStartDate;
    private final JTextField txtEndDate;
    private final JTextArea txtResult;

    public MotorPHGUI() {
        setTitle("MotorPH Employee App");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Payslip Generator"));

        txtEmpNumber = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();

        inputPanel.add(new JLabel("Employee Number (e.g., EMP001):"));
        inputPanel.add(txtEmpNumber);
        inputPanel.add(new JLabel("Pay Start Date (YYYY-MM-DD):"));
        inputPanel.add(txtStartDate);
        inputPanel.add(new JLabel("Pay End Date (YYYY-MM-DD):"));
        inputPanel.add(txtEndDate);

        JButton btnGenerate = new JButton("Generate Payslip");
        JButton btnExit = new JButton("Exit");

        inputPanel.add(btnGenerate);
        inputPanel.add(btnExit);

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResult);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnGenerate.addActionListener(e -> generatePayslip());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void generatePayslip() {
        String empNum = txtEmpNumber.getText().trim();
        String startDateStr = txtStartDate.getText().trim();
        String endDateStr = txtEndDate.getText().trim();

        if (!empNum.matches("EMP\\d{3}")) {
            showError("Invalid Employee Number. Format must be EMP###.");
            return;
        }

        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
            if (endDate.isBefore(startDate)) {
                showError("End date must not be before start date.");
                return;
            }
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        Employee emp = new Employee(empNum, "Plenos", "Catherine", "Agent", 200.0);

        // Clear previous logs (for demo purposes)
        AttendanceLog.clearLogs();

        // Sample logs within range
        AttendanceLog.addAttendance(new AttendanceLog(empNum,
                LocalDateTime.of(2025, 5, 14, 9, 0),
                LocalDateTime.of(2025, 5, 14, 17, 0)));

        AttendanceLog.addAttendance(new AttendanceLog(empNum,
                LocalDateTime.of(2025, 5, 15, 9, 30),
                LocalDateTime.of(2025, 5, 15, 18, 0)));

        PayrollSystem payrollSystem = new PayrollSystem();
        double netPay = payrollSystem.generatePayslip(emp);

        txtResult.setText("Payslip for: " + emp.getFullName() + "\n" +
                "Employee Number: " + emp.getEmployeeNumber() + "\n" +
                "Net Pay: PHP " + String.format("%.2f", netPay));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MotorPHGUI app = new MotorPHGUI();
            app.setVisible(true);
        });
    }

    private static class Employee {
        private final String employeeNumber;
        private final String lastName;
        private final String firstName;
        private final double hourlyRate;

        public Employee(String employeeNumber, String lastName, String firstName, String position, double hourlyRate) {
            this.employeeNumber = employeeNumber;
            this.lastName = lastName;
            this.firstName = firstName;
            this.hourlyRate = hourlyRate;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getEmployeeNumber() {
            return employeeNumber;
        }

        public double getHourlyRate() {
            return hourlyRate;
        }
    }

    private static class AttendanceLog {
        private final String employeeNumber;
        private final LocalDateTime timeIn;
        private final LocalDateTime timeOut;

        private static final List<AttendanceLog> logs = new ArrayList<>();

        public AttendanceLog(String employeeNumber, LocalDateTime timeIn, LocalDateTime timeOut) {
            this.employeeNumber = employeeNumber;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
        }

        public static void addAttendance(AttendanceLog log) {
            logs.add(log);
        }

        public static List<AttendanceLog> getLogsForEmployee(String empNum) {
            List<AttendanceLog> result = new ArrayList<>();
            for (AttendanceLog log : logs) {
                if (log.employeeNumber.equals(empNum)) {
                    result.add(log);
                }
            }
            return result;
        }

        public long getWorkedHours() {
            return Duration.between(timeIn, timeOut).toHours();
        }

        public static void clearLogs() {
            logs.clear();
        }
    }

    private static class PayrollSystem {
        public double generatePayslip(Employee emp) {
            double totalHours = 0.0;

            for (AttendanceLog log : AttendanceLog.getLogsForEmployee(emp.getEmployeeNumber())) {
                totalHours += log.getWorkedHours();
            }

            return totalHours * emp.getHourlyRate();
        }
    }
}
