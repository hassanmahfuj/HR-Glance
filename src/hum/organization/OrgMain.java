package hum.organization;

import hum.util.PanelSetter;
import hum.util.config;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class OrgMain extends javax.swing.JPanel {

    PanelSetter setter;
    
    public OrgMain() {
        initComponents();
        setter = new PanelSetter(body);
        menuSwitch("info", null);
    }
    
    private void menuSwitch(String item, MouseEvent evt) {
        switch(item) {
            case "info":
                setter.set(new InformationView());
                break;
            case "dep":
                setter.set(new DepartmentView());
                break;
            case "des":
                setter.set(new DesignationView());
                break;
        }
        
        if(evt == null) return;
        resetMenuSelection();
        ((JLabel)evt.getSource()).setBorder(new MatteBorder(0,0,4,0, config.COLOR_SECONDARY));
        ((JLabel)evt.getSource()).setForeground(config.COLOR_SECONDARY);
    }
    
    private void resetMenuSelection() {
        info.setBorder(null);
        dep.setBorder(null);
        des.setBorder(null);
        
        info.setForeground(new Color(102,102,102));
        dep.setForeground(new Color(102,102,102));
        des.setForeground(new Color(102,102,102));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        des = new javax.swing.JLabel();
        info = new javax.swing.JLabel();
        dep = new javax.swing.JLabel();
        body = new javax.swing.JPanel();

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Organization");
        header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        des.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        des.setForeground(new java.awt.Color(102, 102, 102));
        des.setText("Designations");
        des.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                desMouseClicked(evt);
            }
        });
        header.add(des, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, -1, 30));

        info.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        info.setForeground(new java.awt.Color(126, 186, 150));
        info.setText("Information");
        info.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(126, 186, 150)));
        info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoMouseClicked(evt);
            }
        });
        header.add(info, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, 30));

        dep.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        dep.setForeground(new java.awt.Color(102, 102, 102));
        dep.setText("Departments");
        dep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                depMouseClicked(evt);
            }
        });
        header.add(dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, 30));

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

    private void infoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoMouseClicked
        menuSwitch("info", evt);
    }//GEN-LAST:event_infoMouseClicked

    private void desMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desMouseClicked
        menuSwitch("des", evt);
    }//GEN-LAST:event_desMouseClicked

    private void depMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_depMouseClicked
        menuSwitch("dep", evt);
    }//GEN-LAST:event_depMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JLabel dep;
    private javax.swing.JLabel des;
    private javax.swing.JPanel header;
    private javax.swing.JLabel info;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
