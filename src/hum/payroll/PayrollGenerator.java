package hum.payroll;

import hum.util.Callback;
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
    String y;
    String m;
    Callback refresh;
    
    public PayrollGenerator(String empId, Date month, Callback refresh) {
        initComponents();
        setLocationRelativeTo(null);
        this.empId = empId;
        this.month = month;
        this.refresh = refresh;
        dcPayDate.setDate(new Date());
        y = new SimpleDateFormat("yyyy").format(month.getTime());
        m = new SimpleDateFormat("MM").format(month.getTime());
        
        getEmp();
        txtMonth.setText(new SimpleDateFormat("MMM - yyyy").format(month.getTime()));
        txtBusinessDays.setText(String.valueOf(tools.getBusinessDays(month)));
        getHoliday();
        setWorkingHour();
        getEmpWorkedHour();
        getOvertimeHour();
        getLateCount();
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
            if(config.DEBUG) System.out.println("getEmp" + e);
        }
    }
    
    private void calcSalary() {
        double basic;
        double houseRent;
        double medical;
        double conveyance;
        double provident;
        double tax;
        
        try {
            basic = Double.parseDouble(txtBasic.getText());
            houseRent = basic * .5;
            medical = basic * .1;
            conveyance = basic * .1;
            provident = basic * .12;
            tax = basic * .05;

            txtHouseRent.setText(String.valueOf(houseRent));
            txtMedical.setText(String.valueOf(medical));
            txtConveyance.setText(String.valueOf(conveyance));
            txtProvident.setText(String.valueOf(provident));
            txtTax.setText(String.valueOf(tax));
        } catch (NumberFormatException e) {
            if(config.DEBUG) System.out.println("calcSalary" + e);
        }
    }
    
    void getHoliday() {
        ResultSet rs = db.get().executeQuery("SELECT DATEDIFF(LEAST(LAST_DAY(CONCAT(?,'-', ?,'-01')), end_date), GREATEST(CONCAT(?,'-', ?,'-01'), start_date)) + 1 AS num_days FROM holiday WHERE start_date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND end_date >= CONCAT(?,'-', ?,'-01')", y, m, y, m, y, m, y, m);
        try {
            int days = 0;
            while (rs.next()) {
                days += Integer.parseInt(rs.getString(1));
            }
            txtHoliday.setText(String.valueOf(days));
        } catch (SQLException e) {
            if(config.DEBUG) System.out.println("getHoliday" + e);
        }
    }
    
    void setWorkingHour() {
        int b = Integer.parseInt(txtBusinessDays.getText());
        int h = Integer.parseInt(txtHoliday.getText());
        txtWorkingHour.setText(String.valueOf((b - h) * config.OFFICE_HOUR));
    }
    
    void getEmpWorkedHour() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT SUM(w_hour) FROM attendance WHERE emp_id = ? AND date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND date >= CONCAT(?,'-', ?,'-01')", empId, y, m, y, m);
            while (rs.next()) {
                if(rs.getString(1) == null) {
                    txtHoursWorked.setText("0");
                } else {
                    txtHoursWorked.setText(rs.getString(1));
                }
            }
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("getEmpWorkedHour " + e);
        }
    }
    
    void getOvertimeHour() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT SUM(o_hour) FROM attendance WHERE emp_id = ? AND date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND date >= CONCAT(?,'-', ?,'-01')", empId, y, m, y, m);
            while (rs.next()) {
                if(rs.getString(1) == null) {
                    txtOvertimeHour.setText("0");
                } else {
                    txtOvertimeHour.setText(rs.getString(1));
                }
            }
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("getOvertimeHour " + e);
        }
    }
    
    void getLateCount() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT COUNT(is_late) FROM attendance WHERE emp_id = ? AND is_late = 'yes' AND date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND date >= CONCAT(?,'-', ?,'-01')", empId, y, m, y, m);
            while (rs.next()) {
                txtLateCount.setText(rs.getString(1));
            }
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("getLateCount " + e);
        }
    }
    
    void calcLeaveHour() {
        int workingHour = 0;
        int attendance = 0;
        int earnedLeaves = 0;
        
        try {
            workingHour = Integer.parseInt(txtWorkingHour.getText());
            attendance = Integer.parseInt(txtHoursWorked.getText());
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("calcLeaveHour1" + e);
        }
        
        try {
            String s = "SELECT DATEDIFF(LEAST(LAST_DAY(CONCAT(?,'-', ?,'-01')), end_date), GREATEST(CONCAT(?,'-', ?,'-01'), start_date)) + 1 AS num_days FROM leaves WHERE emp_id = ? AND status = 'Approved' AND start_date <= LAST_DAY(CONCAT(?,'-', ?,'-01')) AND end_date >= CONCAT(?,'-', ?,'-01')";
            ResultSet rs = db.get().executeQuery(s, y, m, y, m, empId, y, m, y, m);
            int days = 0;
            while(rs.next()) {
                days += Integer.parseInt(rs.getString(1));
            }
            earnedLeaves = days * config.OFFICE_HOUR;
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("calcLeaveHour2" + e);
        }
        
        int leaveHours = workingHour - attendance + earnedLeaves;
        txtLeaveHour.setText(String.valueOf(leaveHours));
    }
    
    private void generatePayroll() {
        double basicSalary = Double.parseDouble(txtBasic.getText());
        double workingHour = Double.parseDouble(txtWorkingHour.getText());
        double hourlyRate = basicSalary / workingHour;
        
        double overtimeHour = Double.parseDouble(txtOvertimeHour.getText());
        double overtimeEarned = (hourlyRate * 1.5) * overtimeHour;
        txtOvertime.setText(String.format("%.1f", overtimeEarned));
        
        int lateCount = Integer.parseInt(txtLateCount.getText());
        int lateStrike = (int) Math.floor(lateCount / 3);
        double lateDeduction = hourlyRate * lateStrike;
        txtLate.setText(String.format("%.1f", lateDeduction));
        
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
        double late = Double.parseDouble(txtLate.getText());
        double leave = Double.parseDouble(txtLeave.getText());
        
        double totalDeduction = provident + insurance + tax + others + late + leave;
        txtTotalDeductions.setText(String.format("%.1f", totalDeduction));
        
        double netPay = totalEarn - totalDeduction;
        txtNetPay.setText(String.format("%.1f", netPay));
    }
    
    void pay() {
        //empId
        //month
        String hours = txtHoursWorked.getText();
        String grossPay = txtTotalEarnings.getText();
        String deductions = txtTotalDeductions.getText();
        String netPay = txtNetPay.getText();
        java.sql.Date payDate = new java.sql.Date(dcPayDate.getDate().getTime());
        
        String overtimeHour = txtOvertimeHour.getText();
        String leaveHour = txtLeaveHour.getText();
        String lateCount = txtLateCount.getText();
        
        String basic = txtBasic.getText();
        String houseRent = txtHouseRent.getText();
        String medical = txtMedical.getText();
        String conveyance = txtConveyance.getText();
        String providentFund = txtProvident.getText();
        String insurance = txtInsurance.getText();
        String tax = txtTax.getText();
        String others = txtOthers.getText();
        String overtime = txtOvertime.getText();
        String late = txtLate.getText();
        String leave = txtLeave.getText();
        
        String s = "INSERT INTO payroll (emp_id, month, hours, gross_pay, deductions, net_pay, pay_date, overtime_hour, leave_hour, late_count, basic, house_rent, medical, conveyance, provident_fund, insurance, tax, others, overtime, late, leave1) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        db.get().executeUpdate(s, empId, new java.sql.Date(month.getTime()), hours, grossPay, deductions, netPay, payDate, overtimeHour, leaveHour, lateCount, basic, houseRent, medical, conveyance, providentFund, insurance, tax, others, overtime, late, leave);
        
        refresh.something();
        dispose();
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
        jLabel49 = new javax.swing.JLabel();
        txtLate = new javax.swing.JLabel();
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
        jLabel35 = new javax.swing.JLabel();
        txtOvertimeHour = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txtLeaveHour = new javax.swing.JLabel();
        txtMonth = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtLateCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Payroll Generator");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Employee Name");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Month");

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtName.setFocusable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
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
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 600, 10));

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
        jPanel3.add(txtNetPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 440, 170, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Net Pay");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 110, -1));

        txtLeave.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtLeave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLeave.setText("0.0");
        jPanel3.add(txtLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 170, 30));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 600, 20));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 600, 10));

        txtOvertime.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtOvertime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtOvertime.setText("0.0");
        jPanel3.add(txtOvertime, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 170, 30));

        txtTotalEarnings.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTotalEarnings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalEarnings.setText("0.0");
        jPanel3.add(txtTotalEarnings, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 170, -1));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 600, 10));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel45.setText("Total");
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 110, -1));

        txtTotalDeductions.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTotalDeductions.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalDeductions.setText("0.0");
        jPanel3.add(txtTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, 170, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel47.setText("Leave");
        jPanel3.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 110, 30));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel49.setText("Late (3L/1D)");
        jPanel3.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 110, 30));

        txtLate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtLate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLate.setText("0.0");
        jPanel3.add(txtLate, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 170, 30));

        txtBusinessDays.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtBusinessDays.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtBusinessDays.setText("0");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel31.setText("Business Days");

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

        txtMonth.setEditable(false);
        txtMonth.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMonth.setFocusable(false);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel39.setText("Late Count");

        txtLateCount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtLateCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLateCount.setText("0");

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
                        .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtLateCount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtOvertimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLateCount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
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
        pay();
    }//GEN-LAST:event_btnPayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPay;
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
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
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
    private javax.swing.JLabel txtLate;
    private javax.swing.JLabel txtLateCount;
    private javax.swing.JLabel txtLeave;
    private javax.swing.JLabel txtLeaveHour;
    private javax.swing.JLabel txtMedical;
    private javax.swing.JTextField txtMonth;
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
