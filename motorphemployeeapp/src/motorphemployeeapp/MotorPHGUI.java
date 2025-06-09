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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.*;

public class MotorPHGUI extends JFrame {
    private final JTextField txtEmpNumber;
    private final JTextField txtFirstName;
    private final JTextField txtLastName;
    private final JTextField txtBirthday;
    private final JTextField txtHourlyRate;
    private final JTable employeeTable;
    private final DefaultTableModel tableModel;
    private final JButton btnUpdate;
    private final JButton btnDelete;

    public MotorPHGUI() {
        setTitle("MotorPH Employee App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model
        String[] columns = {"Employee Number", "First Name", "Last Name", "Birthday", "Hourly Rate"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Employee Records"));

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        txtEmpNumber = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtBirthday = new JTextField();
        txtHourlyRate = new JTextField();

        inputPanel.add(new JLabel("Employee Number (e.g., EMP001):"));
        inputPanel.add(txtEmpNumber);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(txtFirstName);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(txtLastName);
        inputPanel.add(new JLabel("Birthday (YYYY-MM-DD):"));
        inputPanel.add(txtBirthday);
        inputPanel.add(new JLabel("Hourly Rate:"));
        inputPanel.add(txtHourlyRate);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        JButton btnExit = new JButton("Exit");

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExit);

        // Add components to frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnExit.addActionListener(e -> System.exit(0));

        // Add table selection listener
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtEmpNumber.setText((String) tableModel.getValueAt(selectedRow, 0));
                    txtFirstName.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtLastName.setText((String) tableModel.getValueAt(selectedRow, 2));
                    txtBirthday.setText((String) tableModel.getValueAt(selectedRow, 3));
                    txtHourlyRate.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    btnUpdate.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }

    private void addEmployee() {
        if (!validateInput()) return;

        String[] rowData = {
            txtEmpNumber.getText().trim(),
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtBirthday.getText().trim(),
            txtHourlyRate.getText().trim()
        };

        tableModel.addRow(rowData);
        clearInputFields();
    }

    private void updateEmployee() {
        if (!validateInput()) return;

        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(txtEmpNumber.getText().trim(), selectedRow, 0);
            tableModel.setValueAt(txtFirstName.getText().trim(), selectedRow, 1);
            tableModel.setValueAt(txtLastName.getText().trim(), selectedRow, 2);
            tableModel.setValueAt(txtBirthday.getText().trim(), selectedRow, 3);
            tableModel.setValueAt(txtHourlyRate.getText().trim(), selectedRow, 4);
            clearInputFields();
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                clearInputFields();
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
            }
        }
    }

    private boolean validateInput() {
        if (!txtEmpNumber.getText().trim().matches("EMP\\d{3}")) {
            showError("Invalid Employee Number. Format must be EMP###.");
            return false;
        }

        if (txtFirstName.getText().trim().isEmpty()) {
            showError("First Name is required.");
            return false;
        }

        if (txtLastName.getText().trim().isEmpty()) {
            showError("Last Name is required.");
            return false;
        }

        try {
            LocalDate.parse(txtBirthday.getText().trim());
        } catch (DateTimeParseException e) {
            showError("Invalid birthday format. Use YYYY-MM-DD.");
            return false;
        }

        try {
            double rate = Double.parseDouble(txtHourlyRate.getText().trim());
            if (rate <= 0) {
                showError("Hourly rate must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Invalid hourly rate. Please enter a valid number.");
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        txtEmpNumber.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtBirthday.setText("");
        txtHourlyRate.setText("");
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
}
