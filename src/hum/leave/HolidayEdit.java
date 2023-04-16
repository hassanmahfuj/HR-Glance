package hum.leave;

import hum.util.Callback;
import hum.util.db;
import hum.util.tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HolidayEdit extends javax.swing.JFrame {

    private String holidayId;
    private Callback refresh;
    
    public HolidayEdit(String holidayId, Callback refresh) {
        initComponents();
        setLocationRelativeTo(null);
        
        this.holidayId = holidayId;
        this.refresh = refresh;
        
        dcStartDate.setDate(new Date());
        dcEndDate.setDate(new Date());
        
        getHoliday();
    }
    
    void getHoliday() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM holiday WHERE holi_id = ?", holidayId);
            while(rs.next()) {
                txtName.setText(rs.getString(2));
                dcStartDate.setDate(rs.getDate(3));
                dcEndDate.setDate(rs.getDate(4));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    void save() {
        try {
            String r = txtName.getText();
            java.sql.Date startDate1 = new java.sql.Date(dcStartDate.getDate().getTime());
            java.sql.Date endDate1 = new java.sql.Date(dcEndDate.getDate().getTime());
            String days = String.valueOf(tools.calcDays(dcStartDate.getDate(), dcEndDate.getDate()));
            
            if(holidayId.isEmpty())
                db.get().executeUpdate("INSERT INTO holiday (name, start_date, end_date, days) VALUES (?,?,?,?)", r, startDate1, endDate1, days);
            else
                db.get().executeUpdate("UPDATE holiday SET name = ?, start_date = ?, end_date = ?, days = ? WHERE holi_id = ?", r, startDate1, endDate1, days, holidayId);
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
        btnCancel = new javax.swing.JButton();
        dcEndDate = new com.toedter.calendar.JDateChooser();
        dcStartDate = new com.toedter.calendar.JDateChooser();
        txtName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Holiday");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(126, 186, 150)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText(" Manage Holiday");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(126, 186, 150)));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 410, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("End Date");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Holiday Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 30));

        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 110, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Start Date");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 30));

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 110, 40));

        dcEndDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(dcEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 370, 30));

        dcStartDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(dcStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 370, 30));

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 370, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
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
    private com.toedter.calendar.JDateChooser dcEndDate;
    private com.toedter.calendar.JDateChooser dcStartDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
