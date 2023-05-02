package hum.attendance;

import hum.util.db;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AttendAll extends javax.swing.JFrame {

    public AttendAll() {
        initComponents();
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/hum/icons/logo.png"));
        setIconImage(icon.getImage());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        date = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bunch Attendance");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 260, 40));

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("ATTEND ALL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 170, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String[] empIds = {"1","2","4","5","6","7","8","9","10","11","12","13","14","15"};
            java.sql.Date d = new java.sql.Date(date.getDate().getTime());
            String sin = "08:00 AM";
            String sout = "04:00 PM";
            String workingHour = "8";
            String overtimeHour = "0";
            String isLate = "no";
            
            for (String empId : empIds) {
                db.get().executeUpdate("INSERT INTO attendance (emp_id, date, signin, signout, w_hour, o_hour, is_late) VALUES (?,?,?,?,?,?,?)", empId, d, sin, sout, workingHour, overtimeHour, isLate);
            }
            
            JOptionPane.showMessageDialog(null, "All employees attended!");
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
