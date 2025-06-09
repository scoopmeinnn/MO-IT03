package com.motorph.employeeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

/**
 * A panel that lets you view/create/update/delete leave requests
 * (backed by a simple leaverequests.csv file).
 */
public class ValidatedLeaveRequestGUI extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField empField, startField, endField, reasonField;

    public ValidatedLeaveRequestGUI(Department dept) {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
            new String[]{"Employee ID","Start Date","End Date","Reason"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(5,2,5,5));
        form.setBorder(BorderFactory.createTitledBorder("Submit / Edit Leave Request"));
        form.add(new JLabel("Employee ID:"));
        empField = new JTextField(); form.add(empField);
        form.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startField = new JTextField(); form.add(startField);
        form.add(new JLabel("End Date (YYYY-MM-DD):"));
        endField = new JTextField(); form.add(endField);
        form.add(new JLabel("Reason:"));
        reasonField = new JTextField(); form.add(reasonField);

        JButton addBtn    = new JButton("Submit");
        JButton updateBtn = new JButton("Update");
        JButton delBtn    = new JButton("Delete");
        form.add(addBtn);
        form.add(updateBtn);
        form.add(delBtn);
        add(form, BorderLayout.SOUTH);

        loadRequests();

        addBtn.addActionListener(e -> {
            String id = empField.getText().trim();
            String sd = startField.getText().trim();
            String ed = endField.getText().trim();
            String rs = reasonField.getText().trim();
            if (id.isEmpty()||sd.isEmpty()||ed.isEmpty()||rs.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "All fields required.","Validation Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            appendCSV("leaverequests.csv", new String[]{id,sd,ed,rs});
            loadRequests();
            empField.setText(""); startField.setText("");
            endField.setText(""); reasonField.setText("");
        });
        updateBtn.addActionListener(e -> openUpdate());
        delBtn.addActionListener(e -> openDelete());
    }

    private void loadRequests() {
        model.setRowCount(0);
        File f = new File("leaverequests.csv");
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
                bw.write("EmployeeID,StartDate,EndDate,Reason");
                bw.newLine();
            }
            bw.write(String.join(",",row)); bw.newLine();
        } catch(IOException ex){ex.printStackTrace();}
    }

    private void openUpdate() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(this,
                "Select a request to update.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String oldId = model.getValueAt(r,0).toString();
        String oldSd = model.getValueAt(r,1).toString();
        String oldEd = model.getValueAt(r,2).toString();
        String oldRs = model.getValueAt(r,3).toString();

        JTextField sdF = new JTextField(oldSd);
        JTextField edF = new JTextField(oldEd);
        JTextField rsF = new JTextField(oldRs);
        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.add(new JLabel("Start Date:")); p.add(sdF);
        p.add(new JLabel("End Date:"));   p.add(edF);
        p.add(new JLabel("Reason:"));     p.add(rsF);
        int res = JOptionPane.showConfirmDialog(this,p,
            "Update Leave Request",JOptionPane.OK_CANCEL_OPTION);
        if (res!=JOptionPane.OK_OPTION) return;

        String newSd = sdF.getText().trim();
        String newEd = edF.getText().trim();
        String newRs = rsF.getText().trim();
        if (newSd.isEmpty()||newEd.isEmpty()||newRs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "All fields required.","Validation Error",JOptionPane.WARNING_MESSAGE);
            return;
        }

        File in  = new File("leaverequests.csv");
        File out = new File("leaverequests_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {

            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String ln;
            while ((ln=rIn.readLine())!=null) {
                String[] c = ln.split(",",-1);
                if (c[0].equals(oldId)&&c[1].equals(oldSd)&&
                    c[2].equals(oldEd)&&c[3].equals(oldRs)) {

                    wOut.write(oldId + "," + newSd + "," + newEd + "," + newRs);
                } else {
                    wOut.write(ln);
                }
                wOut.newLine();
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadRequests();
    }

    private void openDelete() {
        int r = table.getSelectedRow();
        if (r<0) {
            JOptionPane.showMessageDialog(this,
                "Select a request to delete.","No selection",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id  = model.getValueAt(r,0).toString();
        String sd  = model.getValueAt(r,1).toString();
        String ed  = model.getValueAt(r,2).toString();
        String rs  = model.getValueAt(r,3).toString();
        int c = JOptionPane.showConfirmDialog(this,
            "Delete leave request for " + id + " from " + sd + " to " + ed + "?",
            "Confirm",JOptionPane.YES_NO_OPTION);
        if (c!=JOptionPane.YES_OPTION) return;

        File in  = new File("leaverequests.csv");
        File out = new File("leaverequests_tmp.csv");
        try (BufferedReader rIn = new BufferedReader(new FileReader(in));
             BufferedWriter wOut= new BufferedWriter(new FileWriter(out))) {

            String hdr = rIn.readLine();
            wOut.write(hdr); wOut.newLine();
            String ln;
            while ((ln=rIn.readLine())!=null) {
                if (!ln.equals(id + "," + sd + "," + ed + "," + rs)) {
                    wOut.write(ln);
                    wOut.newLine();
                }
            }
        } catch(IOException ex){ex.printStackTrace();}
        in.delete();
        out.renameTo(in);
        loadRequests();
    }
}
