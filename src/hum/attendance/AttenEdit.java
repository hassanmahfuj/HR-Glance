package hum.attendance;

import hum.util.Callback;
import hum.util.config;
import hum.util.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class AttenEdit extends javax.swing.JFrame {

    private String attenId;
    private ArrayList<String> empIds;
    private Callback refresh;
    
    public AttenEdit(String attenId, String empId, Callback refresh) {
        initComponents();
        setLocationRelativeTo(null);
        this.attenId = attenId;
        this.refresh = refresh;
        empIds = new ArrayList<>();
        date.setDate(new Date());
        
        if(attenId.equals(""))
            getEmp(empId);
        else
            getAtten();
        
        
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void getAtten() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM attendance WHERE atten_id = ?", attenId);
            while(rs.next()) {
                getEmp(rs.getString(2));
                date.setDate(rs.getDate(3));
                cbSignin.setSelectedItem(rs.getString(4));
                cbSignout.setSelectedItem(rs.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void save() {
        try {
            String emp = empIds.get(cbEmpName.getSelectedIndex());
            java.sql.Date d = new java.sql.Date(date.getDate().getTime());
            String sin = cbSignin.getSelectedItem().toString();
            String sout = cbSignout.getSelectedItem().toString();
            String workingHour = String.valueOf(cbSignout.getSelectedIndex() - cbSignin.getSelectedIndex());
            String overtimeHour = "0";
            if(Integer.parseInt(workingHour) > config.OFFICE_HOUR)
                overtimeHour = String.valueOf(Integer.parseInt(workingHour) - config.OFFICE_HOUR);
            String isLate = "no";
            if(cbSignin.getSelectedIndex() > 8) isLate = "yes";
            
            if(attenId.equals(""))
                db.get().executeUpdate("INSERT INTO attendance (emp_id, date, signin, signout, w_hour, o_hour, is_late) VALUES (?,?,?,?,?,?,?)", emp, d, sin, sout, workingHour, overtimeHour, isLate);
            else
                db.get().executeUpdate("UPDATE attendance SET date = ?, signin = ?, signout = ?, w_hour = ?, o_hour = ?, is_late = ? WHERE atten_id = ?", d, sin, sout, workingHour, overtimeHour, isLate, attenId);
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
        cbSignout = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        cbEmpName = new javax.swing.JComboBox<>();
        cbSignin = new javax.swing.JComboBox<>();
        date = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(126, 186, 150)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText(" Manage Attendance");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(126, 186, 150)));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 410, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Sign In");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        cbSignout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbSignout.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM" }));
        cbSignout.setSelectedIndex(16);
        jPanel1.add(cbSignout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 370, 30));

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
        jLabel5.setText("Date");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Sign Out");
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

        cbSignin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbSignin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM" }));
        cbSignin.setSelectedIndex(8);
        jPanel1.add(cbSignin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 370, 30));

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 370, 30));

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
    private javax.swing.JComboBox<String> cbSignin;
    private javax.swing.JComboBox<String> cbSignout;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
