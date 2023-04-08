package hum.payroll;

import hum.util.config;
import hum.util.db;
import hum.util.tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayrollGenerator extends javax.swing.JFrame {
    
    String empId;
    Date month;
    double grossSalary;
    
    public PayrollGenerator(String empId, Date month) {
        initComponents();
        setLocationRelativeTo(null);
        this.empId = empId;
        this.month = month;
        dcPayDate.setDate(new Date());
        
        getEmp();
        dcMonth.setDate(month);
        txtBusinessDays.setText(String.valueOf(tools.getBusinessDays(month)));
        getHoliday();
        setWorkingHour();
        getEmpWorkedHour();
        getOvertimeHour();
        calcLeaveHour();
        generatePayroll();
    }
    
    private void getEmp() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT CONCAT(first_name, ' ', last_name) as name, sal_basic, sal_insurance, sal_others FROM employees WHERE emp_id = ?", empId);
            while (rs.next()) {
                txtName.setText(rs.getString(1));
                txtBasic.setText(rs.getString("sal_basic"));
                txtInsurance.setText(rs.getString("sal_insurance"));
                txtOthers.setText(rs.getString("sal_others"));
            }
            calcSalary();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void calcSalary() {
        double basic;
        double houseRent;
        double medical;
        double conveyance;
        double provident;
        double tax;
        double insurance;
        double others;
        
        try {
            basic = Double.parseDouble(txtBasic.getText());
            houseRent = basic * .5;
            medical = basic * .1;
            conveyance = basic * .1;
            provident = basic * .12;
            tax = basic * .05;
            
            insurance = Double.parseDouble(txtInsurance.getText());
            others = Double.parseDouble(txtOthers.getText());
                    
            
            grossSalary = basic + houseRent + medical + conveyance - provident - tax - insurance - others;
            
            txtHouseRent.setText(String.valueOf(houseRent));
            txtMedical.setText(String.valueOf(medical));
            txtConveyance.setText(String.valueOf(conveyance));
            txtProvident.setText(String.valueOf(provident));
            txtTax.setText(String.valueOf(tax));
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    void getHoliday() {
        String y = new SimpleDateFormat("yyyy").format(month.getTime());
        String m = new SimpleDateFormat("MM").format(month.getTime());
        ResultSet rs = db.get().executeQuery("SELECT DATEDIFF(LEAST(LAST_DAY(CONCAT(?,'-', ?,'-01')), end_date), GREATEST(CONCAT(?,'-', ?,'-01'), start_date)) AS num_days FROM holiday WHERE start_date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND end_date >= CONCAT(?,'-', ?,'-01')", y, m, y, m, y, m, y, m);
        try {
            while (rs.next()) {
                txtHoliday.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    void setWorkingHour() {
        int b = Integer.parseInt(txtBusinessDays.getText());
        int h = Integer.parseInt(txtHoliday.getText());
        txtWorkingHour.setText(String.valueOf((b - h) * config.OFFICE_HOUR));
    }
    
    void getEmpWorkedHour() {
        String y = new SimpleDateFormat("yyyy").format(month.getTime());
        String m = new SimpleDateFormat("MM").format(month.getTime());
        ResultSet rs = db.get().executeQuery("SELECT SUM(w_hour) FROM attendance WHERE emp_id = ? AND date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND date >= CONCAT(?,'-', ?,'-01')", empId, y, m, y, m);
        try {
            while (rs.next()) {
                txtHoursWorked.setText(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void getOvertimeHour() {
        
    }
    
    void calcLeaveHour() {
        String y = new SimpleDateFormat("yyyy").format(month.getTime());
        String m = new SimpleDateFormat("MM").format(month.getTime());
        
        int workingHour = Integer.parseInt(txtWorkingHour.getText());
        int attendance = Integer.parseInt(txtHoursWorked.getText());
        int earnedLeaves = 0;
        
        ResultSet rs = db.get().executeQuery("SELECT SUM(hours) FROM leaves WHERE emp_id = ? AND start_date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND end_date >= CONCAT(?,'-', ?,'-01') AND status = 'Approved'", empId, y, m, y, m);
        try {
            while(rs.next()) {
                earnedLeaves = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        int leaveHours = workingHour - attendance + earnedLeaves;
        txtLeaveHour.setText(String.valueOf(leaveHours));
    }
    
    private void generatePayroll() {
        double workingHour = Double.parseDouble(txtWorkingHour.getText());
        double hourlyRate = grossSalary / workingHour;
        
        double overtimeHour = Double.parseDouble(txtOvertimeHour.getText());
        double overtimeEarned = (hourlyRate * 1.5) * overtimeHour;
        txtOvertime.setText(String.format("%.1f", overtimeEarned));
        
        double leaveHour = Double.parseDouble(txtLeaveHour.getText());
        double leaveDeduction = hourlyRate * leaveHour;
        txtLeave.setText(String.format("%.1f", leaveDeduction));
        
        double basic = Double.parseDouble(txtBasic.getText());
        double houseRent = Double.parseDouble(txtHouseRent.getText());
        double medical = Double.parseDouble(txtMedical.getText());
        double conveyance = Double.parseDouble(txtConveyance.getText());
        double overtime = Double.parseDouble(txtOvertime.getText());
        
        double totalEarn = basic + houseRent + medical + conveyance + overtime;
        txtTotalEarnings.setText(String.format("%.1f", totalEarn));

        double provident = Double.parseDouble(txtProvident.getText());
        double insurance = Double.parseDouble(txtInsurance.getText());
        double tax = Double.parseDouble(txtTax.getText());
        double others = Double.parseDouble(txtOthers.getText());
        double leave = Double.parseDouble(txtLeave.getText());
        
        double totalDeduction = provident + insurance + tax + others + leave;
        txtTotalDeductions.setText(String.format("%.1f", totalDeduction));
        
        double netPay = totalEarn - totalDeduction;
        txtNetPay.setText(String.format("%.1f", netPay));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        txtBasic = new javax.swing.JLabel();
        txtHouseRent = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtConveyance = new javax.swing.JLabel();
        txtMedical = new javax.swing.JLabel();
        txtOthers = new javax.swing.JLabel();
        txtTax = new javax.swing.JLabel();
        txtInsurance = new javax.swing.JLabel();
        txtProvident = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtNetPay = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtLeave = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        txtOvertime = new javax.swing.JLabel();
        txtTotalEarnings = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel45 = new javax.swing.JLabel();
        txtTotalDeductions = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtBusinessDays = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtHoliday = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtWorkingHour = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtHoursWorked = new javax.swing.JLabel();
        dcPayDate = new com.toedter.calendar.JDateChooser();
        btnPay = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        dcMonth = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        txtOvertimeHour = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txtLeaveHour = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Payroll Generator");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Employee Name");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Month");

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtName.setFocusable(false);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Description");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Earnings");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 200, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Deductions");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 200, 30));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 600, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("House Rent");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 170, 30));

        txtBasic.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtBasic.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtBasic.setText("0.0");
        jPanel3.add(txtBasic, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 170, 30));

        txtHouseRent.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtHouseRent.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtHouseRent.setText("0.0");
        jPanel3.add(txtHouseRent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 170, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Medical");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 110, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Conveyance");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 110, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Provident Fund");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 110, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Insurance");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 110, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Tax");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 110, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Others");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 110, 30));

        txtConveyance.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtConveyance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtConveyance.setText("0.0");
        jPanel3.add(txtConveyance, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 170, 30));

        txtMedical.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMedical.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtMedical.setText("0.0");
        jPanel3.add(txtMedical, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 170, 30));

        txtOthers.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtOthers.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtOthers.setText("0.0");
        jPanel3.add(txtOthers, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 170, 30));

        txtTax.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTax.setText("0.0");
        jPanel3.add(txtTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 170, 30));

        txtInsurance.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtInsurance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtInsurance.setText("0.0");
        jPanel3.add(txtInsurance, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 170, 30));

        txtProvident.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtProvident.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtProvident.setText("0.0");
        jPanel3.add(txtProvident, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 170, 30));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel30.setText("Basic Salary");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 170, 30));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel38.setText("Overtime");
        jPanel3.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 110, 30));

        txtNetPay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtNetPay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtNetPay.setText("0.0");
        jPanel3.add(txtNetPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 170, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Net Pay");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 110, -1));

        txtLeave.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtLeave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLeave.setText("0.0");
        jPanel3.add(txtLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 170, 30));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 600, 20));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 600, 10));

        txtOvertime.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtOvertime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtOvertime.setText("0.0");
        jPanel3.add(txtOvertime, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 170, 30));

        txtTotalEarnings.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTotalEarnings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalEarnings.setText("0.0");
        jPanel3.add(txtTotalEarnings, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 170, -1));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 600, 10));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel45.setText("Total");
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 110, -1));

        txtTotalDeductions.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTotalDeductions.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalDeductions.setText("0.0");
        jPanel3.add(txtTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 170, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel47.setText("Leave");
        jPanel3.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 110, 30));

        txtBusinessDays.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtBusinessDays.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtBusinessDays.setText("0");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel31.setText("Bussiness Days");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel32.setText("Holiday");

        txtHoliday.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtHoliday.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtHoliday.setText("0");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel34.setText("Working Hour");

        txtWorkingHour.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtWorkingHour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtWorkingHour.setText("0");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel36.setText("Hours Worked");

        txtHoursWorked.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtHoursWorked.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtHoursWorked.setText("0");

        dcPayDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnPay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnPay.setText("Pay");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel48.setText("Pay Date");

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        dcMonth.setDateFormatString("MMM - y");
        dcMonth.setEnabled(false);
        dcMonth.setFocusable(false);
        dcMonth.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel35.setText("Overtime Hour");

        txtOvertimeHour.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtOvertimeHour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtOvertimeHour.setText("0");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel37.setText("Leave Hour");

        txtLeaveHour.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtLeaveHour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLeaveHour.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(dcPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dcMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtBusinessDays, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtHoliday, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtWorkingHour, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtOvertimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtLeaveHour, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(dcMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusinessDays, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoliday, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWorkingHour, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOvertimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLeaveHour, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcPayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPayActionPerformed
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PayrollGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayrollGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayrollGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayrollGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new PayrollGenerator("2").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPay;
    private com.toedter.calendar.JDateChooser dcMonth;
    private com.toedter.calendar.JDateChooser dcPayDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel txtBasic;
    private javax.swing.JLabel txtBusinessDays;
    private javax.swing.JLabel txtConveyance;
    private javax.swing.JLabel txtHoliday;
    private javax.swing.JLabel txtHoursWorked;
    private javax.swing.JLabel txtHouseRent;
    private javax.swing.JLabel txtInsurance;
    private javax.swing.JLabel txtLeave;
    private javax.swing.JLabel txtLeaveHour;
    private javax.swing.JLabel txtMedical;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtNetPay;
    private javax.swing.JLabel txtOthers;
    private javax.swing.JLabel txtOvertime;
    private javax.swing.JLabel txtOvertimeHour;
    private javax.swing.JLabel txtProvident;
    private javax.swing.JLabel txtTax;
    private javax.swing.JLabel txtTotalDeductions;
    private javax.swing.JLabel txtTotalEarnings;
    private javax.swing.JLabel txtWorkingHour;
    // End of variables declaration//GEN-END:variables
}
