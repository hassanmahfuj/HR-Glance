package hum.main.emp;

import hum.main.User;
import hum.util.db;
import hum.util.tools;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeView extends javax.swing.JPanel {

    String currentMonth;
    
    public HomeView() {
        initComponents();
        tools.setIcon(icoProfile, "user.png", 35, 35);
        txtFullname.setText(User.fullname);
        txtDesignation.setText(User.designation);
        txtTodayDate.setText(new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
        currentMonth = new SimpleDateFormat("yyyy-MM-01").format(new Date());
        
        getAttendCount();
        getLateCount();
        getLeavesCount();
    }
    
    void getAttendCount() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT COUNT(*) FROM attendance WHERE date <= LAST_DAY(?) AND date >= ? AND emp_id = ?", currentMonth, currentMonth, User.empId);
            if (rs.next()) {
                txtAttendanceCount.setText(rs.getString(1));
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    void getLateCount() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT COUNT(*) FROM attendance WHERE date <= LAST_DAY(?) AND date >= ? AND is_late = 'yes' AND emp_id = ?", currentMonth, currentMonth, User.empId);
            if (rs.next()) {
                txtLateCount.setText(rs.getString(1));
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    void getLeavesCount() {
        try {
            ResultSet rs1 = db.get().executeQuery("SELECT COUNT(*) FROM leaves WHERE NOT status = 'Pending' AND start_date <= LAST_DAY(?) AND end_date >= CONCAT(?) AND emp_id = ?", currentMonth, currentMonth, User.empId);
            if (rs1.next()) {
                txtLeavesCount.setText(rs1.getString(1));
            }
            
            ResultSet rs2 = db.get().executeQuery("SELECT COUNT(*) FROM leaves WHERE status = 'Pending' AND start_date <= LAST_DAY(?) AND end_date >= CONCAT(?) AND emp_id = ?", currentMonth, currentMonth, User.empId);
            if (rs2.next()) {
                txtLeavesPending.setText("Pending " + rs2.getString(1));
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        icoProfile = new javax.swing.JLabel();
        txtFullname = new javax.swing.JLabel();
        txtDesignation = new javax.swing.JLabel();
        txtTodayDate = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtLeavesPending = new javax.swing.JLabel();
        txtLeavesCount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtLateCount = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtAttendanceCount = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Dashboard");
        header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));
        header.add(icoProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 35, 35));

        txtFullname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtFullname.setText("jLabel2");
        header.add(txtFullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 42, 200, 30));

        txtDesignation.setText("jLabel2");
        header.add(txtDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 200, -1));

        txtTodayDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTodayDate.setText("jLabel3");
        header.add(txtTodayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 210, -1));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 130));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLeavesPending.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        txtLeavesPending.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLeavesPending.setText("Pending 0");
        jPanel4.add(txtLeavesPending, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 270, 30));

        txtLeavesCount.setFont(new java.awt.Font("JetBrains Mono", 0, 48)); // NOI18N
        txtLeavesCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLeavesCount.setText("0");
        jPanel4.add(txtLeavesCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, 90));

        jLabel7.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Leaves This Month");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 50));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 300, 200));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLateCount.setFont(new java.awt.Font("JetBrains Mono", 0, 48)); // NOI18N
        txtLateCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLateCount.setText("0");
        jPanel5.add(txtLateCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 130));

        jLabel8.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Late This Month");
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 50));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 300, 200));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAttendanceCount.setFont(new java.awt.Font("JetBrains Mono", 0, 48)); // NOI18N
        txtAttendanceCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAttendanceCount.setText("0");
        jPanel6.add(txtAttendanceCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 130));

        jLabel9.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Attendance This Month");
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 50));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 200));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 980, 590));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel header;
    private javax.swing.JLabel icoProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel txtAttendanceCount;
    private javax.swing.JLabel txtDesignation;
    private javax.swing.JLabel txtFullname;
    private javax.swing.JLabel txtLateCount;
    private javax.swing.JLabel txtLeavesCount;
    private javax.swing.JLabel txtLeavesPending;
    private javax.swing.JLabel txtTodayDate;
    // End of variables declaration//GEN-END:variables
}
