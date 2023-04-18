package hum.employees;

import hum.main.User;
import hum.util.config;
import hum.util.db;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EmployeeAdd extends javax.swing.JPanel {

    private ArrayList<String> depIds;
    private ArrayList<String> desIds;
    private String empId;
    
    public EmployeeAdd(String empId) {
        initComponents();
        
        if(User.role.equals("Employee")) {
            cbDepartment.setEnabled(false);
            cbDesignation.setEnabled(false);
            cbRole.setEnabled(false);
            dateJoin.setEnabled(false);
            dateLeave.setEnabled(false);
        }
        
        this.empId = empId;
        depIds = new ArrayList<>();
        desIds = new ArrayList<>();
        
        getDep();
        getDes();
        
        if(!empId.equals("")) getEmp();
    }
    
    void getDep() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM departments");
            while(rs.next()) {
                depIds.add(rs.getString(1));
                cbDepartment.addItem(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void getDes() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM designations");
            while(rs.next()) {
                desIds.add(rs.getString(1));
                cbDesignation.addItem(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void getEmp() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM employees WHERE emp_id = ?", empId);
            while(rs.next()) {
                txtFirstname.setText(rs.getString("first_name"));
                txtLastname.setText(rs.getString("last_name"));
                cbDepartment.setSelectedIndex(depIds.indexOf(rs.getString("department")));
                cbDesignation.setSelectedIndex(desIds.indexOf(rs.getString("designation")));                
                cbGender.setSelectedItem(rs.getString("gender"));
                cbBloodgroup.setSelectedItem(rs.getString("blood_group"));
                txtNid.setText(rs.getString("nid"));
                txtEmail.setText(rs.getString("email"));
                txtContact.setText(rs.getString("contact"));
                dateBirth.setDate(rs.getDate("dob"));
                dateJoin.setDate(rs.getDate("do_join"));
                dateLeave.setDate(rs.getDate("do_leave"));
                txtUsername.setText(rs.getString("username"));
                txtPassword.setText(rs.getString("password"));
                cbRole.setSelectedItem(rs.getString("role"));
            }
        } catch (Exception e) {
            if(config.DEBUG) System.out.println("getEmp" + e);
        }
    }
    
    boolean save() {
        String firstName = txtFirstname.getText();
        String lastName = txtLastname.getText();
        String dep = depIds.get(cbDepartment.getSelectedIndex());
        String des = desIds.get(cbDesignation.getSelectedIndex());
        String gender = cbGender.getSelectedItem().toString();
        String blood = cbBloodgroup.getSelectedItem().toString();
        String nid = txtNid.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        Date dob = dateBirth.getDate() == null ? null : new Date(dateBirth.getDate().getTime());
        Date doJoin = dateJoin.getDate() == null ? null : new Date(dateJoin.getDate().getTime());
        Date doLeave = dateLeave.getDate() == null ? null : new Date(dateLeave.getDate().getTime());
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String role = cbRole.getSelectedItem().toString();
        
        try {
            if(empId.equals("")) {
                String s = "INSERT INTO employees (first_name, last_name, department, designation, gender, blood_group, nid, email, contact, dob, do_join, do_leave, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                return db.get().executeUpdate(s, firstName, lastName, dep, des, gender, blood, nid, email, contact, dob, doJoin, doLeave, username, password, role);
            } else {
                String s = "UPDATE employees SET first_name = ?, last_name = ?, department = ?, designation = ?, gender = ?, blood_group = ?, nid = ?, email = ?, contact = ?, dob = ?, do_join = ?, do_leave = ?, username = ?, password = ?, role = ? WHERE emp_id = ?";
                return db.get().executeUpdate(s, firstName, lastName, dep, des, gender, blood, nid, email, contact, dob, doJoin, doLeave, username, password, role, empId);
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFirstname = new javax.swing.JTextField();
        txtLastname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        cbDesignation = new javax.swing.JComboBox<>();
        cbRole = new javax.swing.JComboBox<>();
        cbBloodgroup = new javax.swing.JComboBox<>();
        cbDepartment = new javax.swing.JComboBox<>();
        cbGender = new javax.swing.JComboBox<>();
        dateLeave = new com.toedter.calendar.JDateChooser();
        dateBirth = new com.toedter.calendar.JDateChooser();
        dateJoin = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("First Name");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 80, 30));

        txtFirstname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtFirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 250, -1));

        txtLastname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtLastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 250, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Last Name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 80, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Department");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 90, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Designation");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 90, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Role");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 80, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Gender");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 80, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Blood Group");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 100, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("NID");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 80, 30));

        txtNid.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 250, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Contact Number");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 130, 30));

        txtContact.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 250, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Date of Birth");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 100, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Date of Joining");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 110, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setText("Date of Leaving");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 120, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Email");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 80, 30));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 250, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Username");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, 80, 30));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 250, -1));

        cbDesignation.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(cbDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 250, -1));

        cbRole.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HR Manager", "Employee" }));
        jPanel1.add(cbRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 250, -1));

        cbBloodgroup.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbBloodgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));
        jPanel1.add(cbBloodgroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 250, -1));

        cbDepartment.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(cbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 250, -1));

        cbGender.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jPanel1.add(cbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 250, -1));

        dateLeave.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(dateLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 310, 250, 30));

        dateBirth.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(dateBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 250, 30));

        dateJoin.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(dateJoin, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 250, 30));

        jButton1.setBackground(new java.awt.Color(126, 186, 150));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SAVE");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 140, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Password");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 80, 30));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 400, 250, -1));

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(save()) {
            JOptionPane.showMessageDialog(null, "Employee Data Saved Succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbBloodgroup;
    private javax.swing.JComboBox<String> cbDepartment;
    private javax.swing.JComboBox<String> cbDesignation;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbRole;
    private com.toedter.calendar.JDateChooser dateBirth;
    private com.toedter.calendar.JDateChooser dateJoin;
    private com.toedter.calendar.JDateChooser dateLeave;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtNid;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
