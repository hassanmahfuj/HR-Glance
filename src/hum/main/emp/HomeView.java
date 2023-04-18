package hum.main.emp;

import hum.main.User;
import hum.util.tools;

public class HomeView extends javax.swing.JPanel {

    public HomeView() {
        initComponents();
        tools.setIcon(icoProfile, "user.png", 35, 35);
        txtFullname.setText(User.fullname);
        txtDesignation.setText(User.designation);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        icoProfile = new javax.swing.JLabel();
        txtFullname = new javax.swing.JLabel();
        txtDesignation = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Dashboard");
        header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));
        header.add(icoProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 35, 35));

        txtFullname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtFullname.setText("jLabel2");
        header.add(txtFullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 42, 200, 30));

        txtDesignation.setText("jLabel2");
        header.add(txtDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 200, -1));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 130));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel header;
    private javax.swing.JLabel icoProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel txtDesignation;
    private javax.swing.JLabel txtFullname;
    // End of variables declaration//GEN-END:variables
}
