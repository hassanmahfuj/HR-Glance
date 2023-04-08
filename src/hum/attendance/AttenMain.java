package hum.attendance;

import hum.util.PanelSetter;
import hum.util.config;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class AttenMain extends javax.swing.JPanel {

    PanelSetter setter;
    
    public AttenMain() {
        initComponents();
        setter = new PanelSetter(body);
        menuSwitch("list", null);
    }
    
    private void menuSwitch(String item, MouseEvent evt) {
        switch(item) {
            case "list":
                setter.set(new AttenList(""));
                break;
            case "report":
//                setter.set(new EmployeeAdd(""));
                break;
        }
        
        if(evt == null) return;
        resetMenuSelection();
        ((JLabel)evt.getSource()).setBorder(new MatteBorder(0,0,4,0, config.COLOR_SECONDARY));
        ((JLabel)evt.getSource()).setForeground(config.COLOR_SECONDARY);
    }
    
    private void resetMenuSelection() {
        tabList.setBorder(null);
        tabReport.setBorder(null);
        
        tabList.setForeground(new Color(102,102,102));
        tabReport.setForeground(new Color(102,102,102));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabReport = new javax.swing.JLabel();
        tabList = new javax.swing.JLabel();
        body = new javax.swing.JPanel();

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Attendance");
        header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        tabReport.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabReport.setForeground(new java.awt.Color(102, 102, 102));
        tabReport.setText("Attendance Report");
        tabReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabReportMouseClicked(evt);
            }
        });
        header.add(tabReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 30));

        tabList.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabList.setForeground(new java.awt.Color(126, 186, 150));
        tabList.setText("Attendance List");
        tabList.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(126, 186, 150)));
        tabList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabListMouseClicked(evt);
            }
        });
        header.add(tabList, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, 30));

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

    private void tabListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabListMouseClicked
        menuSwitch("list", evt);
    }//GEN-LAST:event_tabListMouseClicked

    private void tabReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabReportMouseClicked
        menuSwitch("report", evt);
    }//GEN-LAST:event_tabReportMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel tabList;
    private javax.swing.JLabel tabReport;
    // End of variables declaration//GEN-END:variables
}
