package hum.leave;

import hum.util.PanelSetter;
import hum.util.config;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class LeaveMain extends javax.swing.JPanel {

    PanelSetter setter;
    
    public LeaveMain() {
        initComponents();
        setter = new PanelSetter(body);
        menuSwitch("list", null);
    }
    
    private void menuSwitch(String item, MouseEvent evt) {
        switch(item) {
            case "list":
                setter.set(new LeaveList());
                break;
            case "holiday":
                setter.set(new HolidayList());
                break;
        }
        
        if(evt == null) return;
        resetMenuSelection();
        ((JLabel)evt.getSource()).setBorder(new MatteBorder(0,0,4,0, config.COLOR_SECONDARY));
        ((JLabel)evt.getSource()).setForeground(config.COLOR_SECONDARY);
    }
    
    private void resetMenuSelection() {
        tabList.setBorder(null);
        tabHoliday.setBorder(null);
        
        tabList.setForeground(new Color(102,102,102));
        tabHoliday.setForeground(new Color(102,102,102));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabHoliday = new javax.swing.JLabel();
        tabList = new javax.swing.JLabel();
        body = new javax.swing.JPanel();

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Leave");
        header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        tabHoliday.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabHoliday.setForeground(new java.awt.Color(102, 102, 102));
        tabHoliday.setText("Holiday");
        tabHoliday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabHolidayMouseClicked(evt);
            }
        });
        header.add(tabHoliday, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, 30));

        tabList.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        tabList.setForeground(new java.awt.Color(126, 186, 150));
        tabList.setText("Leave List");
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

    private void tabHolidayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabHolidayMouseClicked
        menuSwitch("holiday", evt);
    }//GEN-LAST:event_tabHolidayMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel tabHoliday;
    private javax.swing.JLabel tabList;
    // End of variables declaration//GEN-END:variables
}
