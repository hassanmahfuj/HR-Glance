package hum.payroll;

import hum.util.Callback;
import hum.util.db;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class PayrollGenerate extends javax.swing.JPanel {

    DefaultTableModel dtm;
    ArrayList<String> empIds;
    Callback refresh;
    Date paymentMonth;

    public PayrollGenerate() {
        empIds = new ArrayList<>();
        initComponents();
        
        jTable1.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column != 1) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                setFont(new Font("Segoe UI", Font.BOLD, 16));
                setBackground(new Color(126, 186, 150));
                setForeground(new Color(255,255,255));
                c.setPreferredSize(new Dimension(c.getWidth(), 35));
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });
        
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(255,255,255));
                } else {
                    c.setBackground(new Color(229, 241, 234));
                }
                if (column != 1) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        int[] columnWidths = {20, 300, 200};
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            TableColumn column = jTable1.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
        
        dtm = (DefaultTableModel) jTable1.getModel();
        
        setCurrentPaymentMonth();
        getNonPaidEmp();
        
        refresh = () -> {
            getNonPaidEmp();
        };
    }

    void getNonPaidEmp() {
        try {
            String month = new SimpleDateFormat("yyyy-MM%").format(paymentMonth.getTime());
            ResultSet rs = db.get().executeQuery("SELECT emp_id, first_name, last_name, sal_basic FROM employees WHERE emp_id NOT IN (SELECT emp_id FROM payroll WHERE month LIKE ?)", month);
            empIds.clear();
            dtm.setRowCount(0);
            while (rs.next()) {
                empIds.add(rs.getString(1));
                String fullName = rs.getString(2) + " " + rs.getString(3);
                Object[] data = {rs.getString(1), fullName, rs.getString(4)};
                dtm.addRow(data);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    void setCurrentPaymentMonth() {
        Calendar c = Calendar.getInstance();
        cbMonth.setSelectedItem(new SimpleDateFormat("MMMM").format(c.getTime()));
        cbYear.setSelectedItem(new SimpleDateFormat("yyyy").format(c.getTime()));
    }
    
    void setPaymentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(cbYear.getSelectedItem().toString()), cbMonth.getSelectedIndex(), 1);        
        paymentMonth = c.getTime();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbMonth = new javax.swing.JComboBox<>();
        cbYear = new javax.swing.JComboBox<>();

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Basic Salary"
            }
        ));
        jTable1.setRowHeight(30);
        jTable1.setSelectionForeground(new java.awt.Color(220, 53, 69));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 44, 968, 540));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbMonth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbMonth.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(40, 167, 69)));
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });
        jPanel2.add(cbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 130, 30));

        cbYear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        cbYear.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(40, 167, 69)));
        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });
        jPanel2.add(cbYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 130, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 488, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 968, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        new PayrollGenerator(empIds.get(jTable1.getSelectedRow()), paymentMonth, refresh).setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed
        setPaymentMonth();
        getNonPaidEmp();
    }//GEN-LAST:event_cbMonthActionPerformed

    private void cbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbYearActionPerformed
        setPaymentMonth();
        getNonPaidEmp();
    }//GEN-LAST:event_cbYearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbMonth;
    private javax.swing.JComboBox<String> cbYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
