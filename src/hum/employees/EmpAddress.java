package hum.employees;

import hum.util.config;
import hum.util.db;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class EmpAddress extends javax.swing.JPanel {

    private String empId;

    public EmpAddress(String empId) {
        this.empId = empId;

        initComponents();

        if (!empId.equals("")) {
            getAddress();
        }
    }

    void getAddress() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT add_per_address, add_per_city, add_per_country, add_pre_address, add_pre_city, add_pre_country FROM employees WHERE emp_id = ?", empId);
            while (rs.next()) {
                txtPerAddress.setText(rs.getString("add_per_address"));
                txtPerCity.setText(rs.getString("add_per_city"));
                txtPerCountry.setText(rs.getString("add_per_country"));
                txtPreAddress.setText(rs.getString("add_pre_address"));
                txtPreCity.setText(rs.getString("add_pre_city"));
                txtPreCountry.setText(rs.getString("add_pre_country"));
            }
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("getAddress : " + e);
            }
        }
    }

    boolean savePermanent() {
        String perAddress = txtPerAddress.getText();
        String perCity = txtPerCity.getText();
        String perCountry = txtPerCountry.getText();

        try {
            String s = "UPDATE employees SET add_per_address = ?, add_per_city = ?, add_per_country = ? WHERE emp_id = ?";
            return db.get().executeUpdate(s, perAddress, perCity, perCountry, empId);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("savePermanent : " + e);
            }
            return false;
        }
    }

    boolean savePresent() {
        String preAddress = txtPreAddress.getText();
        String preCity = txtPreCity.getText();
        String preCountry = txtPreCountry.getText();

        try {
            String s = "UPDATE employees SET add_pre_address = ?, add_pre_city = ?, add_pre_country = ? WHERE emp_id = ?";
            return db.get().executeUpdate(s, preAddress, preCity, preCountry, empId);
        } catch (Exception e) {
            if (config.DEBUG) {
                System.out.println("savePresent : " + e);
            }
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtPerCity = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPerCountry = new javax.swing.JTextField();
        btnPerSave = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPerAddress = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPreAddress = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        txtPreCity = new javax.swing.JTextField();
        txtPreCountry = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btnPreSave = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(980, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(950, 590));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Address");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 80, 30));

        txtPerCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtPerCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 460, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Country");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 80, 30));

        txtPerCountry.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtPerCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 460, -1));

        btnPerSave.setBackground(new java.awt.Color(126, 186, 150));
        btnPerSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPerSave.setForeground(new java.awt.Color(255, 255, 255));
        btnPerSave.setText("SAVE");
        btnPerSave.setBorder(null);
        btnPerSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnPerSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 140, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Permanent Contact Information");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        txtPerAddress.setColumns(20);
        txtPerAddress.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPerAddress.setRows(5);
        jScrollPane1.setViewportView(txtPerAddress);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 940, 100));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("City");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 80, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Present Contact Information");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel18.setText("Address");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 80, 30));

        txtPreAddress.setColumns(20);
        txtPreAddress.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPreAddress.setRows(5);
        jScrollPane2.setViewportView(txtPreAddress);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 940, 100));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setText("City");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 80, 30));

        txtPreCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtPreCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 460, -1));

        txtPreCountry.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtPreCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 470, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel20.setText("Country");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 460, 80, 30));

        btnPreSave.setBackground(new java.awt.Color(126, 186, 150));
        btnPreSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPreSave.setForeground(new java.awt.Color(255, 255, 255));
        btnPreSave.setText("SAVE");
        btnPreSave.setBorder(null);
        btnPreSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnPreSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 140, 40));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 590));
    }// </editor-fold>//GEN-END:initComponents

    private void btnPerSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerSaveActionPerformed
        if(savePermanent()) {
            JOptionPane.showMessageDialog(null, "Employee Address Saved Succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPerSaveActionPerformed

    private void btnPreSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreSaveActionPerformed
        if(savePresent()) {
            JOptionPane.showMessageDialog(null, "Employee Address Saved Succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPreSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPerSave;
    private javax.swing.JButton btnPreSave;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtPerAddress;
    private javax.swing.JTextField txtPerCity;
    private javax.swing.JTextField txtPerCountry;
    private javax.swing.JTextArea txtPreAddress;
    private javax.swing.JTextField txtPreCity;
    private javax.swing.JTextField txtPreCountry;
    // End of variables declaration//GEN-END:variables
}
