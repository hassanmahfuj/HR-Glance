package hum.employees;

import hum.util.config;
import hum.util.db;
import java.awt.Font;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmpEducation extends javax.swing.JPanel {

    private String empId;
    DefaultTableModel dtm;

    public EmpEducation(String empId) {
        this.empId = empId;

        initComponents();
        dtm = (DefaultTableModel) tbl_data.getModel();
        tbl_data.removeColumn(tbl_data.getColumnModel().getColumn(0));
        tbl_data.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        if (!empId.equals("")) {
            getEducation();
        }
    }

    void getEducation() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT edu_id, degree_title, institute, result, passing_year FROM emp_education WHERE emp_id = ? ORDER BY passing_year", empId);
            dtm.setRowCount(0);
            while (rs.next()) {
                Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
                dtm.addRow(data);
            }
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("getEducation : " + e);
            }
        }
    }

    void add() {
        String title = txtTitle.getText();
        String institute = txtInstitute.getText();
        String result = txtResult.getText();
        String year = txtYear.getText();

        try {
            String s = "INSERT INTO emp_education (emp_id, degree_title, institute, result, passing_year) VALUES (?, ?, ?, ?, ?)";
            db.get().executeUpdate(s, empId, title, institute, result, year);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("save : " + e);
            }
        }
    }

    void update() {
        String eduId = dtm.getValueAt(tbl_data.getSelectedRow(), 0).toString();
        String title = txtTitle.getText();
        String institute = txtInstitute.getText();
        String result = txtResult.getText();
        String year = txtYear.getText();

        try {
            String s = "UPDATE emp_education SET degree_title = ?, institute = ?, result = ?, passing_year = ? WHERE edu_id = ?";
            db.get().executeUpdate(s, title, institute, result, year, eduId);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("update : " + e);
            }
        }
    }

    void delete() {
        String eduId = dtm.getValueAt(tbl_data.getSelectedRow(), 0).toString();

        try {
            String s = "DELETE FROM emp_education WHERE edu_id = ?";
            db.get().executeUpdate(s, eduId);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("delete : " + e);
            }
        }
    }

    void resetFields() {
        txtTitle.setText("");
        txtInstitute.setText("");
        txtResult.setText("");
        txtYear.setText("");
    }

    boolean verifyFields() {
        boolean a = txtTitle.getText().trim().equals("");
        boolean b = txtInstitute.getText().trim().equals("");
        boolean c = txtResult.getText().trim().equals("");
        boolean d = txtYear.getText().trim().equals("");

        return !(a || b || c || d);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTitle = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtInstitute = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtResult = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTitle.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 450, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Institute Name");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 120, 30));

        txtInstitute.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtInstitute, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 450, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Degree Title");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Records");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 90, -1));

        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 100, 30));

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 100, 30));

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 100, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Result");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 130, 30));

        txtResult.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 450, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Passing Year");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 120, 30));

        txtYear.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 450, -1));

        tbl_data.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Institute", "Result", "Year"
            }
        ));
        tbl_data.setRowHeight(25);
        tbl_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_dataMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_data);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 920, 350));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (verifyFields()) {
            add();
            resetFields();
            getEducation();
        } else {
            JOptionPane.showMessageDialog(null, "Fill all the fields");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (tbl_data.getSelectedRow() != -1) {
            if (verifyFields()) {
                update();
                resetFields();
                getEducation();
            } else {
                JOptionPane.showMessageDialog(null, "Fill all the fields");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No item selected");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tbl_data.getSelectedRow() != -1) {
            delete();
            resetFields();
            getEducation();
        } else {
            JOptionPane.showMessageDialog(null, "No item selected");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
        if (tbl_data.getSelectedRow() != -1) {
            txtTitle.setText(dtm.getValueAt(tbl_data.getSelectedRow(), 1).toString());
            txtInstitute.setText(dtm.getValueAt(tbl_data.getSelectedRow(), 2).toString());
            txtResult.setText(dtm.getValueAt(tbl_data.getSelectedRow(), 3).toString());
            txtYear.setText(dtm.getValueAt(tbl_data.getSelectedRow(), 4).toString());
        }
    }//GEN-LAST:event_tbl_dataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_data;
    private javax.swing.JTextField txtInstitute;
    private javax.swing.JTextField txtResult;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
