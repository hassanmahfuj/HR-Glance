package hum.main;

import chart.ModelPolarAreaChart;
import hum.util.db;
import hum.util.tools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class HomeView extends javax.swing.JPanel {
    
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> holidayListModel;
    String currentMonth;
    
    public HomeView() {
        initComponents();
        tools.setIcon(icoProfile, "user.png", 35, 35);
        txtFullname.setText(User.fullname);
        txtDesignation.setText(User.designation);
        txtTodayDate.setText(new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
        currentMonth = new SimpleDateFormat("yyyy-MM-01").format(new Date());
        
        listModel = new DefaultListModel<>();
        lateList.setModel(listModel);
        
        holidayListModel = new DefaultListModel<>();
        holidayList.setModel(holidayListModel);

        getEmployeeCount();
        getLeavesCount();
        getGenderCount();
        getLateList();
        getHolidaysList();
        getPaidThisYear();
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
    
    void getLeavesCount() {
        try {
            ResultSet rs1 = db.get().executeQuery("SELECT COUNT(*) FROM leaves WHERE NOT status = 'Pending' AND start_date <= LAST_DAY(?) AND end_date >= CONCAT(?)", currentMonth, currentMonth);
            if (rs1.next()) {
                txtLeavesCount.setText(rs1.getString(1));
            }
            
            ResultSet rs2 = db.get().executeQuery("SELECT COUNT(*) FROM leaves WHERE status = 'Pending' AND start_date <= LAST_DAY(?) AND end_date >= CONCAT(?)", currentMonth, currentMonth);
            if (rs2.next()) {
                txtLeavesPending.setText("Pending " + rs2.getString(1));
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
    
    void getHolidaysList() {
        try {
            String s = "SELECT name FROM holiday WHERE start_date <= LAST_DAY(?) AND end_date >= ?";
            String m = new SimpleDateFormat("yyyy-MM-01").format(new Date());
            ResultSet rs = db.get().executeQuery(s, m, m);
            while (rs.next()) {
                String d = rs.getString(1);
                holidayListModel.addElement(d);
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    void getPaidThisYear() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            ResultSet rs = db.get().executeQuery("SELECT DATE_FORMAT(month, '%M'), SUM(net_pay) FROM payroll GROUP BY month LIMIT 6");
            while (rs.next()) {
                dataset.setValue(rs.getInt(2), "Amount", rs.getString(1));
            }
            showBarChart(dataset);
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    void showBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart("","","", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        paidYearBarChart.removeAll();
        paidYearBarChart.add(barpChartPanel, BorderLayout.CENTER);
        paidYearBarChart.validate();
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
        jLabel2 = new javax.swing.JLabel();
        polarAreaChartGender = new chart.PolarAreaChart();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lateList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        txtLeavesPending = new javax.swing.JLabel();
        txtLeavesCount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtEmployeeCount = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        holidayList = new javax.swing.JList<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        paidYearBarChart = new javax.swing.JPanel();

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

        jLabel2.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gender");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 50));
        jPanel2.add(polarAreaChartGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 210, 180));

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, 250));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Late This Month");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 50));

        jScrollPane2.setBorder(null);
        jScrollPane2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lateList.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(lateList);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 300, 130));

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 320, 200));

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
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 50));

        jPanel5.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 270, -1, 200));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total Employee");
        jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 50));

        txtEmployeeCount.setFont(new java.awt.Font("JetBrains Mono", 0, 48)); // NOI18N
        txtEmployeeCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEmployeeCount.setText("0");
        jPanel6.add(txtEmployeeCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, 160));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 250));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Holidays This Month");
        jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 50));

        jScrollPane3.setBorder(null);
        jScrollPane3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        holidayList.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jScrollPane3.setViewportView(holidayList);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 300, 130));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 320, 200));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Paid This Year");
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(126, 186, 150)));
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 50));

        paidYearBarChart.setOpaque(false);
        paidYearBarChart.setLayout(new java.awt.BorderLayout());
        jPanel8.add(paidYearBarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 340, 180));

        jPanel5.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 360, 250));

        jScrollPane1.setViewportView(jPanel5);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 590));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 980, 590));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel header;
    private javax.swing.JList<String> holidayList;
    private javax.swing.JLabel icoProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lateList;
    private javax.swing.JPanel paidYearBarChart;
    private chart.PolarAreaChart polarAreaChartGender;
    private javax.swing.JLabel txtDesignation;
    private javax.swing.JLabel txtEmployeeCount;
    private javax.swing.JLabel txtFullname;
    private javax.swing.JLabel txtLeavesCount;
    private javax.swing.JLabel txtLeavesPending;
    private javax.swing.JLabel txtTodayDate;
    // End of variables declaration//GEN-END:variables
}
