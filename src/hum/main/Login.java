package hum.main;

import hum.main.emp.EmpDashboard;
import hum.util.db;
import hum.util.tools;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Login extends javax.swing.JFrame {
    
    public Login() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/hum/icons/logo.png"));
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        tools.setIcon(iconClose, "close.png");
        getCompanyDetails();
        textAnimation();
    }
    
    void loginAction() {
        try {
            String s = "SELECT emp_id, username, role, CONCAT(first_name, ' ', last_name) as fullname, des_name FROM employees JOIN designations ON designation = id WHERE username = ? AND password = ?";
            ResultSet rs = db.get().executeQuery(s, txtUsername.getText(), String.valueOf(txtPassword.getPassword()));
            if(rs != null && rs.next()) {
                User.empId = rs.getString(1);
                User.username = rs.getString(2);
                User.role = rs.getString(3);
                User.fullname = rs.getString(4);
                User.designation = rs.getString(5);
                
                if(rs.getString(3).equals("HR Manager"))
                    new Dashboard().setVisible(true);
                else
                    new EmpDashboard().setVisible(true);
                dispose();
            } else {
                new Message("ERROR", "Username / Password is incorrect", 0).setVisible(true);
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
            new Message("ERROR", e.toString(), 0).setVisible(true);
        }
    }
    
    void getCompanyDetails() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT name, address, phone, email FROM company");
            if(rs.next()) {
                Company.name = rs.getString(1);
                Company.address = rs.getString(2);
                Company.phone = rs.getString(3);
                Company.email = rs.getString(4);
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    Timer timer;
    int counter = 6;
    String tagLine;
    private void textAnimation() {
        tagLine = "<html>Empowering your workforce,<br><br>Empowering your success.</html>";
        timer = new Timer(40, (ActionEvent e) -> {
            if (counter > tagLine.length()) {
                timer.stop();
            } else {
                txtTagline.setText(tagLine.substring(0, counter++));
            }
        });
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTagline = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        iconClose = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HRGlance Login");
        setUndecorated(true);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(29, 35, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTagline.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        txtTagline.setForeground(new java.awt.Color(242, 242, 242));
        jPanel4.add(txtTagline, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 300, 230));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 300, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(101, 143, 255));
        jLabel6.setText("HR Glance");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 200, 50));

        jPanel3.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(101, 143, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });
        jPanel5.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 300, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Username");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, 30));

        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel5.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 90, 30));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });
        jPanel5.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 300, 30));

        iconClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconCloseMouseClicked(evt);
            }
        });
        jPanel5.add(iconClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 35, 35));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("HERE");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 100, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("LOGIN");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 120, 50));

        jPanel3.add(jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_iconCloseMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        loginAction();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        if(evt.getKeyCode() == 10) loginAction();
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if(evt.getKeyCode() == 10) loginAction();
    }//GEN-LAST:event_txtPasswordKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel iconClose;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel txtTagline;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
