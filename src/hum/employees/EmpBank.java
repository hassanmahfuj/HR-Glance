package hum.employees;

import hum.util.config;
import hum.util.db;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class EmpBank extends javax.swing.JPanel {

    private String empId;

    public EmpBank(String empId) {
        this.empId = empId;

        initComponents();

        if (!empId.equals("")) {
            getBank();
        }
    }

    void getBank() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT bank_holder, bank_name, bank_branch, bank_number, bank_type FROM employees WHERE emp_id = ?", empId);
            while (rs.next()) {
                txtHolderName.setText(rs.getString("bank_holder"));
                txtBankName.setText(rs.getString("bank_name"));
                txtBranch.setText(rs.getString("bank_branch"));
                txtAccountNumber.setText(rs.getString("bank_number"));
                txtAccountType.setText(rs.getString("bank_type"));
            }
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("getBank : " + e);
            }
        }
    }

    boolean save() {
        String bankHolder = txtHolderName.getText();
        String bankName = txtBankName.getText();
        String bankBranch = txtBranch.getText();
        String bankNumber = txtAccountNumber.getText();
        String bankType = txtAccountType.getText();

        try {
            String s = "UPDATE employees SET bank_holder = ?, bank_name = ?, bank_branch = ?, bank_number = ?, bank_type = ? WHERE emp_id = ?";
            return db.get().executeUpdate(s, bankHolder, bankName, bankBranch, bankNumber, bankType, empId);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("save : " + e);
            }
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBranch = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtAccountNumber = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtAccountType = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtHolderName = new javax.swing.JTextField();
        txtBankName = new javax.swing.JTextField();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBranch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtBranch, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 450, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Bank Account Number");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 200, 30));

        txtAccountNumber.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtAccountNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, 450, -1));

        btnSave.setBackground(new java.awt.Color(126, 186, 150));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("SAVE");
        btnSave.setBorder(null);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 140, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Bank Information");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Branch Name");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 120, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Bank Account Type");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 190, 30));

        txtAccountType.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtAccountType, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 450, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel22.setText("Bank Holder Name");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 150, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel23.setText("Bank Name");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 140, 30));

        txtHolderName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtHolderName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 450, -1));

        txtBankName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtBankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 450, -1));

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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(save()) {
            JOptionPane.showMessageDialog(null, "Employee Bank Details Saved Succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAccountNumber;
    private javax.swing.JTextField txtAccountType;
    private javax.swing.JTextField txtBankName;
    private javax.swing.JTextField txtBranch;
    private javax.swing.JTextField txtHolderName;
    // End of variables declaration//GEN-END:variables
}
