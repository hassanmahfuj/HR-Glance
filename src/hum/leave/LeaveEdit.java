package hum.leave;

import hum.util.Callback;
import hum.util.db;
import hum.util.tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class LeaveEdit extends javax.swing.JFrame {

    private String leaveId;
    private ArrayList<String> empIds;
    private Callback refresh;
    
    public LeaveEdit(String leaveId, String empId, Callback refresh) {
        initComponents();
        setLocationRelativeTo(null);
        
        this.leaveId = leaveId;
        this.refresh = refresh;
        
        empIds = new ArrayList<>();
        startDate.setDate(new Date());
        endDate.setDate(new Date());

        if(leaveId.equals(""))
            getEmp(empId);
        else
            getLeave();
    }
    
    void getEmp(String empid) {
        try {
            ResultSet rs = db.get().executeQuery("SELECT emp_id, first_name, last_name FROM employees");
            empIds.clear();
            while(rs.next()) {
                empIds.add(rs.getString(1));
                String fullname = rs.getString(2) + " " + rs.getString(3);
                cbEmpName.addItem(fullname);
            }
            if(!empid.equals("")) {
                cbEmpName.setSelectedIndex(empIds.indexOf(empid));
                cbEmpName.setEnabled(false);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    void getLeave() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM leaves WHERE leave_id = ?", leaveId);
            while(rs.next()) {
                getEmp(rs.getString(2));
                txtReason.setText(rs.getString(3));
                startDate.setDate(rs.getDate(4));
                endDate.setDate(rs.getDate(5));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void save() {
        try {
            String emp = empIds.get(cbEmpName.getSelectedIndex());
            String r = txtReason.getText();
            java.sql.Date startDate1 = new java.sql.Date(startDate.getDate().getTime());
            java.sql.Date endDate1 = new java.sql.Date(endDate.getDate().getTime());
            String hours = String.valueOf(tools.calcLeaveHour(startDate.getDate(), endDate.getDate()));
            
            if(leaveId.isEmpty())
                db.get().executeUpdate("INSERT INTO leaves (emp_id, reason, start_date, end_date, hours, status) VALUES (?,?,?,?,?, 'Pending')", emp, r, startDate1, endDate1, hours);
            else
                db.get().executeUpdate("UPDATE leaves SET reason = ?, start_date = ?, end_date = ?, hours = ? WHERE leave_id = ?", r, startDate1, endDate1, hours, leaveId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        cbEmpName = new javax.swing.JComboBox<>();
        endDate = new com.toedter.calendar.JDateChooser();
        startDate = new com.toedter.calendar.JDateChooser();
        txtReason = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Leave");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(126, 186, 150)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText(" Manage Leave");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(126, 186, 150)));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 410, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("End Date");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Employee Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 30));

        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 110, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Start Date");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Reason");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 110, 40));

        cbEmpName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(cbEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 370, 30));

        endDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(endDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 370, 30));

        startDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(startDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 370, 30));

        txtReason.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 370, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
        refresh.something();
        dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbEmpName;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTextField txtReason;
    // End of variables declaration//GEN-END:variables
}
