package com.motorph.employeeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

/**
 * A panel that lets you view/create/update/delete payroll entries
 * (backed by a simple payroll.csv file).
 */
public class ValidatedPayrollGUI extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField empField, amtField;

    public ValidatedPayrollGUI(Department dept) {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Employee ID","Amount"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Add / Edit Payroll"));
        form.add(new JLabel("Employee ID:"));
        empField = new JTextField();
        form.add(empField);
        form.add(new JLabel("Amount:"));
        amtField = new JTextField();
        form.add(amtField);

        JButton addBtn    = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton delBtn    = new JButton("Delete");
        form.add(addBtn);
        form.add(updateBtn);
        form.add(delBtn);
        add(form, BorderLayout.SOUTH);

        loadPayroll();

        addBtn.addActionListener(e -> {
            String id = empField.getText().trim();
            String amt= amtField.getText().trim();
            if (id.isEmpty()||amt.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Both fields required.","Validation Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Double.parseDouble(amt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Amount must be numeric.","Validation Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            appendCSV("payroll.csv", new String[]{id,amt});
            loadPayroll();
            empField.setText(""); amtField.setText("");
        });

        updateBtn.addActionListener(e -> openUpdate());
        delBtn.addActionListener(e -> openDelete());
    }

    private void loadPayroll() {
        model.setRowCount(0);
        File f = new File("payroll.csv");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String ln;
            while ((ln=br.readLine())!=null) {
                model.addRow(ln.split(",",-1));
            }
        } catch(IOException ex){ex.printStackTrace();}
    }

    private void appendCSV(String file, String[] row) {
        boolean nf = !new File(file).exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
            if (nf) {
                bw.write("EmployeeID,Amount"); bw.newLine();
            }
            bw.write(row[0] + "," + row[1]); bw.newLine();
        } catch(IOException ex){ex.printStackTrace();}
    }

    private void openUpdate() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(this,
                "Select an entry to update.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String oldId = model.getValueAt(r,0).toString();
        String oldAmt= model.getValueAt(r,1).toString();

        JTextField amtF = new JTextField(oldAmt);
        JPanel p = new JPanel(new GridLayout(1,2,5,5));
        p.add(new JLabel("Amount:")); p.add(amtF);
        int res = JOptionPane.showConfirmDialog(this,p,"Update Payroll",JOptionPane.OK_CANCEL_OPTION);
        if (res!=JOptionPane.OK_OPTION) return;
        String newAmt = amtF.getText().trim();
        try { Double.parseDouble(newAmt); }
        catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Amount must be numeric.","Validation Error",JOptionPane.WARNING_MESSAGE);
            return;
        }

        // rewrite CSV
        File in  = new File("payroll.csv");
        File out = new File("payroll_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {

            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String ln;
            while ((ln=rIn.readLine())!=null) {
                String[] c = ln.split(",",-1);
                if (c[0].equals(oldId) && c[1].equals(oldAmt)) {
                    wOut.write(oldId + "," + newAmt);
                } else {
                    wOut.write(ln);
                }
                wOut.newLine();
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadPayroll();
    }

    private void openDelete() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(this,
                "Select an entry to delete.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id  = model.getValueAt(r,0).toString();
        String amt = model.getValueAt(r,1).toString();
        int c = JOptionPane.showConfirmDialog(this,
            "Delete payroll for " + id + " amount " + amt + "?",
            "Confirm",JOptionPane.YES_NO_OPTION);
        if (c!=JOptionPane.YES_OPTION) return;

        File in  = new File("payroll.csv");
        File out = new File("payroll_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {

            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String ln;
            while ((ln=rIn.readLine())!=null) {
                if (!ln.equals(id + "," + amt)) {
                    wOut.write(ln);
                    wOut.newLine();
                }
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadPayroll();
    }
}
