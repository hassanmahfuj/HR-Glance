package hum.main.emp;

import hum.attendance.AttenMain;
import hum.employees.EmployeeEdit;
import hum.leave.LeaveMain;
import hum.main.Login;
import hum.main.PanelSwitch;
import hum.main.User;
import hum.payroll.PayrollMain;
import hum.util.PanelSetter;
import hum.util.config;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmpDashboard extends javax.swing.JFrame {

    PanelSetter setter;
    PanelSwitch ps;

    public EmpDashboard() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/hum/icons/logo.png"));
        setIconImage(icon.getImage());
        setupMenuHoverEffect();

        setter = new PanelSetter(mainPanel);
        menuSwitch("dashboard", null);

        ps = (JPanel panel) -> {
            setter.set(new HomeView());
            resetMenuSelection();
            itemDashboard.setOpaque(true);
            itemDashboard.setBackground(Color.WHITE);
            itemDashboard.setForeground(config.COLOR_PRIMARY);
        };
    }

    private void menuSwitch(String item, MouseEvent evt) {
        switch (item) {
            case "dashboard":
                setter.set(new HomeView());
                break;
            case "profile":
                setter.set(new EmployeeEdit(ps, User.empId));
                break;
            case "attendance":
                setter.set(new AttenMain());
                break;
            case "leave":
                setter.set(new LeaveMain());
                break;
            case "payroll":
                setter.set(new PayrollMain());
                break;
        }

        if (evt == null) {
            return;
        }
        resetMenuSelection();
        ((JLabel) evt.getSource()).setOpaque(true);
        ((JLabel) evt.getSource()).setBackground(Color.WHITE);
        ((JLabel) evt.getSource()).setForeground(config.COLOR_PRIMARY);
    }

    private void resetMenuSelection() {
        itemDashboard.setOpaque(false);
        itemProfile.setOpaque(false);
        itemAttendance.setOpaque(false);
        itemLeave.setOpaque(false);
        itemPayroll.setOpaque(false);

        itemDashboard.setForeground(Color.WHITE);
        itemProfile.setForeground(Color.WHITE);
        itemAttendance.setForeground(Color.WHITE);
        itemLeave.setForeground(Color.WHITE);
        itemPayroll.setForeground(Color.WHITE);
    }
    
    void setupMenuHoverEffect() {
        itemDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemDashboard.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, config.COLOR_SECONDARY));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                itemDashboard.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            }
        });
        itemProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemProfile.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, config.COLOR_SECONDARY));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                itemProfile.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            }
        });
        itemAttendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemAttendance.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, config.COLOR_SECONDARY));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                itemAttendance.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            }
        });
        itemLeave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemLeave.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, config.COLOR_SECONDARY));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                itemLeave.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            }
        });
        itemPayroll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemPayroll.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, config.COLOR_SECONDARY));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                itemPayroll.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            }
        });
        roundPanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                txtLogout.setForeground(config.COLOR_SECONDARY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                txtLogout.setForeground(new Color(60,63,65));
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        itemProfile = new javax.swing.JLabel();
        itemDashboard = new javax.swing.JLabel();
        itemAttendance = new javax.swing.JLabel();
        itemLeave = new javax.swing.JLabel();
        itemPayroll = new javax.swing.JLabel();
        roundPanel1 = new hum.util.RoundPanel();
        txtLogout = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(29, 35, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 768));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel4.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(101, 143, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HR Glance");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 130));

        itemProfile.setBackground(new java.awt.Color(255, 255, 255));
        itemProfile.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        itemProfile.setForeground(new java.awt.Color(255, 255, 255));
        itemProfile.setText("Profile");
        itemProfile.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        itemProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemProfileMouseClicked(evt);
            }
        });
        jPanel2.add(itemProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 280, 50));

        itemDashboard.setBackground(new java.awt.Color(255, 255, 255));
        itemDashboard.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        itemDashboard.setForeground(new java.awt.Color(60, 63, 65));
        itemDashboard.setText("Dashboard");
        itemDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        itemDashboard.setOpaque(true);
        itemDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemDashboardMouseClicked(evt);
            }
        });
        jPanel2.add(itemDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 280, 50));

        itemAttendance.setBackground(new java.awt.Color(255, 255, 255));
        itemAttendance.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        itemAttendance.setForeground(new java.awt.Color(255, 255, 255));
        itemAttendance.setText("Attendance");
        itemAttendance.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        itemAttendance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemAttendanceMouseClicked(evt);
            }
        });
        jPanel2.add(itemAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 280, 50));

        itemLeave.setBackground(new java.awt.Color(255, 255, 255));
        itemLeave.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        itemLeave.setForeground(new java.awt.Color(255, 255, 255));
        itemLeave.setText("Leave");
        itemLeave.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        itemLeave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemLeaveMouseClicked(evt);
            }
        });
        jPanel2.add(itemLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 280, 50));

        itemPayroll.setBackground(new java.awt.Color(255, 255, 255));
        itemPayroll.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        itemPayroll.setForeground(new java.awt.Color(255, 255, 255));
        itemPayroll.setText("Payroll");
        itemPayroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        itemPayroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemPayrollMouseClicked(evt);
            }
        });
        jPanel2.add(itemPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 280, 50));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roundPanel1MouseClicked(evt);
            }
        });
        roundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLogout.setFont(new java.awt.Font("JetBrains Mono", 1, 20)); // NOI18N
        txtLogout.setForeground(new java.awt.Color(60, 63, 65));
        txtLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLogout.setText("Logout");
        roundPanel1.add(txtLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 220, -1));

        jPanel2.add(roundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 630, 220, 50));

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemDashboardMouseClicked
        menuSwitch("dashboard", evt);
    }//GEN-LAST:event_itemDashboardMouseClicked

    private void itemProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemProfileMouseClicked
        menuSwitch("profile", evt);
    }//GEN-LAST:event_itemProfileMouseClicked

    private void itemAttendanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemAttendanceMouseClicked
        menuSwitch("attendance", evt);
    }//GEN-LAST:event_itemAttendanceMouseClicked

    private void itemLeaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemLeaveMouseClicked
        menuSwitch("leave", evt);
    }//GEN-LAST:event_itemLeaveMouseClicked

    private void itemPayrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemPayrollMouseClicked
        menuSwitch("payroll", evt);
    }//GEN-LAST:event_itemPayrollMouseClicked

    private void roundPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roundPanel1MouseClicked
        dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_roundPanel1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel itemAttendance;
    private javax.swing.JLabel itemDashboard;
    private javax.swing.JLabel itemLeave;
    private javax.swing.JLabel itemPayroll;
    private javax.swing.JLabel itemProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel mainPanel;
    private hum.util.RoundPanel roundPanel1;
    private javax.swing.JLabel txtLogout;
    // End of variables declaration//GEN-END:variables
}
