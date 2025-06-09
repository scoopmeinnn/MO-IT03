package com.motorph.employeeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Vector;

/**
 * A panel that lets you view/create/update/delete employees
 * (backed by a simple employees.csv file).
 */
public class ValidatedEmployeeGUI extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField idField, nameField, positionField;

    public ValidatedEmployeeGUI(Department dept) {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID","Name","Position"}, 0);
        table      = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Add / Edit Employee"));
        form.add(new JLabel("ID:"));
        idField = new JTextField();
        form.add(idField);
        form.add(new JLabel("Name:"));
        nameField = new JTextField();
        form.add(nameField);
        form.add(new JLabel("Position:"));
        positionField = new JTextField();
        form.add(positionField);

        JButton addBtn    = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton delBtn    = new JButton("Delete");
        form.add(addBtn);
        form.add(updateBtn);
        form.add(delBtn);

        add(form, BorderLayout.SOUTH);

        // load data
        loadEmployees();

        // wiring
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String nm = nameField.getText().trim();
            String pos= positionField.getText().trim();
            if (id.isEmpty()||nm.isEmpty()||pos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "All fields are required.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (exists(id)) {
                JOptionPane.showMessageDialog(this,
                    "ID already exists.",
                    "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (appendCSV("employees.csv", new String[]{id,nm,pos})) {
                loadEmployees();
                clearFields();
            }
        });
        updateBtn.addActionListener(e -> openUpdateDialog());
        delBtn.addActionListener(e -> deleteSelected());
    }

    private void loadEmployees() {
        tableModel.setRowCount(0);
        File f = new File("employees.csv");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String hdr = br.readLine();
            String ln;
            while ((ln = br.readLine()) != null) {
                tableModel.addRow(ln.split(",",-1));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean exists(String id) {
        for (int i=0;i<tableModel.getRowCount();i++)
            if (tableModel.getValueAt(i,0).equals(id))
                return true;
        return false;
    }

    private boolean appendCSV(String file, String[] row) {
        boolean newFile = !new File(file).exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (newFile) {
                bw.write("ID,Name,Position");
                bw.newLine();
            }
            bw.write(String.join(",", row));
            bw.newLine();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        positionField.setText("");
    }

    private void openUpdateDialog() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(
                this,"Select a row to update.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String oldId = tableModel.getValueAt(r,0).toString();
        String oldNm = tableModel.getValueAt(r,1).toString();
        String oldPos= tableModel.getValueAt(r,2).toString();

        JTextField nmF = new JTextField(oldNm);
        JTextField posF= new JTextField(oldPos);
        JPanel p = new JPanel(new GridLayout(2,2,5,5));
        p.add(new JLabel("Name:")); p.add(nmF);
        p.add(new JLabel("Position:")); p.add(posF);
        int res = JOptionPane.showConfirmDialog(this,p,"Update Employee",JOptionPane.OK_CANCEL_OPTION);
        if (res!=JOptionPane.OK_OPTION) return;

        String newNm = nmF.getText().trim();
        String newPos= posF.getText().trim();
        if (newNm.isEmpty()||newPos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Fields required.","Validation Error",JOptionPane.WARNING_MESSAGE);
            return;
        }
        // rewrite CSV
        File in  = new File("employees.csv");
        File out = new File("employees_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {
            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String line;
            while ((line=rIn.readLine())!=null) {
                String[] c = line.split(",",-1);
                if (c[0].equals(oldId)) {
                    wOut.write(oldId + "," + newNm + "," + newPos);
                } else {
                    wOut.write(line);
                }
                wOut.newLine();
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadEmployees();
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(
                this,"Select a row to delete.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = tableModel.getValueAt(r,0).toString();
        int c = JOptionPane.showConfirmDialog(
            this,"Delete employee "+id+"?","Confirm",JOptionPane.YES_NO_OPTION);
        if (c!=JOptionPane.YES_OPTION) return;

        File in  = new File("employees.csv");
        File out = new File("employees_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {
            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String line;
            while ((line=rIn.readLine())!=null) {
                if (!line.startsWith(id + ",")) {
                    wOut.write(line);
                    wOut.newLine();
                }
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadEmployees();
    }
}
