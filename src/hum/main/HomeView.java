package hum.main;

import chart.ModelPolarAreaChart;
import hum.util.db;
import hum.util.tools;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;

public class HomeView extends javax.swing.JPanel {
    
    private DefaultListModel<String> listModel;
    
    public HomeView() {
        initComponents();
        tools.setIcon(icoProfile, "user.png", 35, 35);
        txtFullname.setText(User.fullname);
        txtDesignation.setText(User.designation);
        txtTodayDate.setText(new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
        
        listModel = new DefaultListModel<>();
        lateList.setModel(listModel);

        getEmployeeCount();
        getGenderCount();
        getLateList();
    }

    void getEmployeeCount() {
        try {
            String s = "SELECT COUNT(emp_id) FROM employees";
            ResultSet rs = db.get().executeQuery(s);
            if (rs.next()) {
                txtEmployeeCount.setText(rs.getString(1));
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }

    void getGenderCount() {
        try {
            String s = "SELECT gender, COUNT(*) FROM employees GROUP BY gender";
            ResultSet rs = db.get().executeQuery(s);
            if (rs.next()) {
                polarAreaChartGender.addItem(new ModelPolarAreaChart(new Color(52, 148, 203), rs.getString(1), Double.parseDouble(rs.getString(2))));
            }
            if (rs.next()) {
                polarAreaChartGender.addItem(new ModelPolarAreaChart(new Color(175, 67, 237), rs.getString(1), Double.parseDouble(rs.getString(2))));
            }
            polarAreaChartGender.start();
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    void getLateList() {
        try {
            String s = "SELECT CONCAT(first_name, ' ', last_name), COUNT(first_name) FROM attendance a JOIN employees USING(emp_id) WHERE is_late = 'yes' && date LIKE ? GROUP BY first_name, last_name";
            String m = new SimpleDateFormat("yyyy-MM%").format(new Date());
            ResultSet rs = db.get().executeQuery(s, m);
            while (rs.next()) {
                String d = rs.getString(1) + " (" + rs.getString(2) + ")";
                listModel.addElement(d);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        polarAreaChartGender = new chart.PolarAreaChart();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lateList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtEmployeeCount = new javax.swing.JLabel();

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

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 290, 10));

        jLabel2.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gender");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 30));
        jPanel2.add(polarAreaChartGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 210, 180));

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, 250));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 360, 10));

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Late This Month");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 30));

        jScrollPane2.setBorder(null);
        jScrollPane2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lateList.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(lateList);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 340, 180));

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 360, 250));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 290, 10));

        jLabel5.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total Employee");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 30));

        txtEmployeeCount.setFont(new java.awt.Font("JetBrains Mono", 0, 48)); // NOI18N
        txtEmployeeCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEmployeeCount.setText("0");
        jPanel4.add(txtEmployeeCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, 160));

        jPanel5.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 250));

        jScrollPane1.setViewportView(jPanel5);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 590));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 980, 590));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel header;
    private javax.swing.JLabel icoProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JList<String> lateList;
    private chart.PolarAreaChart polarAreaChartGender;
    private javax.swing.JLabel txtDesignation;
    private javax.swing.JLabel txtEmployeeCount;
    private javax.swing.JLabel txtFullname;
    private javax.swing.JLabel txtTodayDate;
    // End of variables declaration//GEN-END:variables
}
