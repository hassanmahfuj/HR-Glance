package hum.employees;

import hum.main.PanelSwitch;
import hum.util.PanelSetter;
import hum.util.config;
import hum.util.db;
import hum.util.tools;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class EmployeeEdit extends javax.swing.JPanel {

    PanelSetter setter;
    PanelSwitch ps;
    private String empId;

    public EmployeeEdit(PanelSwitch ps, String empId) {
        this.ps = ps;
        this.empId = empId;
        initComponents();
        setter = new PanelSetter(body);
        menuSwitch("tabPersonal", null);
        tools.setIcon(iconBack, "back-black.png", 35, 35);
        getEmpName();
    }

    void getEmpName() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT first_name, last_name FROM employees WHERE emp_id = ?", empId);
            while (rs.next()) {
                empName.setText(rs.getString(1) + " " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void menuSwitch(String item, MouseEvent evt) {
        switch (item) {
            case "tabPersonal":
                setter.set(new EmployeeAdd(empId));
                break;
            case "tabAddress":
                setter.set(new EmpAddress(empId));
                break;
            case "tabEducation":
                setter.set(new EmpEducation(empId));
                break;
            case "tabExperience":
                setter.set(new EmpExperience(empId));
                break;
            case "tabBank":
                setter.set(new EmpBank(empId));
                break;
            case "tabSalary":
                setter.set(new EmpSalary(empId));
                break;
        }

        if (evt == null) {
            return;
        }
        resetMenuSelection();
        ((JLabel) evt.getSource()).setBorder(new MatteBorder(0, 0, 4, 0, config.COLOR_SECONDARY));
        ((JLabel) evt.getSource()).setForeground(config.COLOR_SECONDARY);
    }

    private void resetMenuSelection() {
        tabPersonal.setBorder(null);
        tabAddress.setBorder(null);
        tabEducation.setBorder(null);
        tabExperience.setBorder(null);
        tabBank.setBorder(null);
        tabSalary.setBorder(null);

        tabPersonal.setForeground(new Color(102, 102, 102));
        tabAddress.setForeground(new Color(102, 102, 102));
        tabEducation.setForeground(new Color(102, 102, 102));
        tabExperience.setForeground(new Color(102, 102, 102));
        tabBank.setForeground(new Color(102, 102, 102));
        tabSalary.setForeground(new Color(102, 102, 102));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        empName = new javax.swing.JLabel();
        tabPersonal = new javax.swing.JLabel();
        tabAddress = new javax.swing.JLabel();
        tabEducation = new javax.swing.JLabel();
        tabExperience = new javax.swing.JLabel();
        tabBank = new javax.swing.JLabel();
        tabSalary = new javax.swing.JLabel();
        iconBack = new javax.swing.JLabel();
        body = new javax.swing.JPanel();

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        empName.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        empName.setForeground(new java.awt.Color(51, 51, 51));
        empName.setText("Employees");
        header.add(empName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, -1));

        tabPersonal.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabPersonal.setForeground(new java.awt.Color(126, 186, 150));
        tabPersonal.setText("Personal Info");
        tabPersonal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(126, 186, 150)));
        tabPersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPersonalMouseClicked(evt);
            }
        });
        header.add(tabPersonal, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, 30));

        tabAddress.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabAddress.setForeground(new java.awt.Color(102, 102, 102));
        tabAddress.setText("Address");
        tabAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabAddressMouseClicked(evt);
            }
        });
        header.add(tabAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, 30));

        tabEducation.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabEducation.setForeground(new java.awt.Color(102, 102, 102));
        tabEducation.setText("Education");
        tabEducation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabEducationMouseClicked(evt);
            }
        });
        header.add(tabEducation, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 30));

        tabExperience.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabExperience.setForeground(new java.awt.Color(102, 102, 102));
        tabExperience.setText("Experience");
        tabExperience.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabExperienceMouseClicked(evt);
            }
        });
        header.add(tabExperience, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, 30));

        tabBank.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabBank.setForeground(new java.awt.Color(102, 102, 102));
        tabBank.setText("Bank Account");
        tabBank.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabBankMouseClicked(evt);
            }
        });
        header.add(tabBank, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, -1, 30));

        tabSalary.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabSalary.setForeground(new java.awt.Color(102, 102, 102));
        tabSalary.setText("Salary");
        tabSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabSalaryMouseClicked(evt);
            }
        });
        header.add(tabSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, 30));

        iconBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconBackMouseClicked(evt);
            }
        });
        header.add(iconBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 35, 35));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tabPersonalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPersonalMouseClicked
        menuSwitch("tabPersonal", evt);
    }//GEN-LAST:event_tabPersonalMouseClicked

    private void tabAddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabAddressMouseClicked
        menuSwitch("tabAddress", evt);
    }//GEN-LAST:event_tabAddressMouseClicked

    private void tabEducationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabEducationMouseClicked
        menuSwitch("tabEducation", evt);
    }//GEN-LAST:event_tabEducationMouseClicked

    private void tabExperienceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabExperienceMouseClicked
        menuSwitch("tabExperience", evt);
    }//GEN-LAST:event_tabExperienceMouseClicked

    private void tabBankMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabBankMouseClicked
        menuSwitch("tabBank", evt);
    }//GEN-LAST:event_tabBankMouseClicked

    private void tabSalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabSalaryMouseClicked
        menuSwitch("tabSalary", evt);
    }//GEN-LAST:event_tabSalaryMouseClicked

    private void iconBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconBackMouseClicked
        ps.change(new EmpMain(ps));
    }//GEN-LAST:event_iconBackMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JLabel empName;
    private javax.swing.JPanel header;
    private javax.swing.JLabel iconBack;
    private javax.swing.JLabel tabAddress;
    private javax.swing.JLabel tabBank;
    private javax.swing.JLabel tabEducation;
    private javax.swing.JLabel tabExperience;
    private javax.swing.JLabel tabPersonal;
    private javax.swing.JLabel tabSalary;
    // End of variables declaration//GEN-END:variables
}
