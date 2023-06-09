package hum.attendance;

import hum.main.User;
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
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class AttenList extends javax.swing.JPanel {

    DefaultTableModel dtm;
    ArrayList<String> attenIds;
    ArrayList<String> empIds;
    Callback refresh;
    String em;
    Date attenDate;
    boolean firstAction = true;

    public AttenList() {
        initComponents();
        jTable1.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column != 0) {
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
                if (column != 0) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        int[] columnWidths = {200, 100, 50, 50, 150};
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            TableColumn column = jTable1.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
        
        attenDate = new Date();
        dcAttenDate.setDate(attenDate);

        if (User.role.equals("Employee")) {
            em = User.empId;
            dcAttenDate.setVisible(false);
            btnAddAll.setVisible(false);
            cbEmployess.setVisible(false);
            jLabel1.setVisible(false);
            jLabel2.setVisible(false);
        } else {
            em = "";
//            cbEmployess.setVisible(false);
//            jLabel1.setVisible(false);
        }
        attenIds = new ArrayList<>();
        empIds = new ArrayList<>();
        dtm = (DefaultTableModel) jTable1.getModel();
        getEmployeesData();
        getAtten();

        refresh = () -> {
            getAtten();
        };
    }

    void getAtten() {
        try {
            ResultSet rs;
            java.sql.Date d = new java.sql.Date(dcAttenDate.getDate().getTime());
            if (em.equals("")) {
                rs = db.get().executeQuery("SELECT a.atten_id, a.emp_id, e.first_name, e.last_name, a.date, a.signin, a.signout, a.o_hour, a.is_late FROM employees e LEFT JOIN attendance a ON e.emp_id = a.emp_id AND a.date = ? ORDER BY a.date DESC", d);
            } else {
                rs = db.get().executeQuery("SELECT a.atten_id, a.emp_id, e.first_name, e.last_name, a.date, a.signin, a.signout FROM employees e LEFT JOIN attendance a ON e.emp_id = a.emp_id WHERE a.emp_id = ? ORDER BY a.date DESC", em);
            }
            attenIds.clear();
            dtm.setRowCount(0);
            while (rs.next()) {
                attenIds.add(rs.getString(1));
                String fullName = rs.getString(3) + " " + rs.getString(4);
                Object[] data = new Object[5];
//                System.out.println(rs.getString(1));
                if(rs.getString(1) == null) {
                    data[0] = fullName;
                    data[1] = new SimpleDateFormat("yyyy-MM-dd").format(attenDate);
                    data[2] = "-";
                    data[3] = "-";
                    data[4] = "Absent";
                } else {
                    data[0] = fullName;
                    data[1] = rs.getString(5);
                    data[2] = rs.getString(6);
                    data[3] = rs.getString(7);
                    data[4] = "Present";
                    if (em.equals("")) {
                        if(rs.getInt(8) > 0) {
                            data[4] += " / Overtime";
                        }
                        if(rs.getString(9).equals("yes")) {
                            data[4] += " / Late";
                        }
                    }
                }
                dtm.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e + " getAtten");
        }
    }
    
    void getEmployeesData() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT emp_id, CONCAT(first_name, ' ', last_name) as name FROM employees");
            while(rs.next()) {
                empIds.add(rs.getString(1));
                cbEmployess.addItem(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e + " getEmployeesData");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAddAll = new javax.swing.JButton();
        dcAttenDate = new com.toedter.calendar.JDateChooser();
        cbEmployess = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAdd.setBackground(new java.awt.Color(40, 167, 69));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("ADD");
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 100, 30));

        btnUpdate.setBackground(new java.awt.Color(255, 193, 7));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("UPDATE");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 100, 30));

        btnDelete.setBackground(new java.awt.Color(220, 53, 69));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("DELETE");
        btnDelete.setBorder(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 100, 30));

        btnAddAll.setBackground(new java.awt.Color(40, 167, 69));
        btnAddAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddAll.setForeground(new java.awt.Color(255, 255, 255));
        btnAddAll.setText("ADD ALL");
        btnAddAll.setBorder(null);
        btnAddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAllActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 110, 30));

        dcAttenDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dcAttenDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcAttenDatePropertyChange(evt);
            }
        });
        jPanel1.add(dcAttenDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 180, 30));

        cbEmployess.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbEmployess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmployessActionPerformed(evt);
            }
        });
        jPanel1.add(cbEmployess, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 180, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("By Name");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 10, 60, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("By Date");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 50, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 968, -1));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee Name", "Date", "Sign In", "Sign Out", "Status"
            }
        ));
        jTable1.setRowHeight(30);
        jTable1.setSelectionForeground(new java.awt.Color(220, 53, 69));
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 52, 968, 532));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //ps.change(new EmployeeEdit(ps, empIds.get(jTable1.getSelectedRow())));
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (User.role.equals("HR Manager"))
            new AttenEdit("", "", refresh).setVisible(true);
        else
            new AttenEdit("", User.empId, refresh).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            if(attenIds.get(jTable1.getSelectedRow()) == null) {
                JOptionPane.showMessageDialog(null, "Cannot Update This Item", "Info", 0);
            } else {
                new AttenEdit(attenIds.get(jTable1.getSelectedRow()), "", refresh).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            if (attenIds.get(jTable1.getSelectedRow()) == null) {
                JOptionPane.showMessageDialog(null, "Cannot Delete This Item", "Info", 0);
            } else {
                int r = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", 0);
                if (r == 0) {
                    if (db.get().executeUpdate("DELETE FROM attendance WHERE atten_id = ?", attenIds.get(jTable1.getSelectedRow()))) {
                        getAtten();
                        JOptionPane.showMessageDialog(null, "Attendence Deleted Succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAllActionPerformed
        new AttendAll().setVisible(true);
    }//GEN-LAST:event_btnAddAllActionPerformed

    private void dcAttenDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcAttenDatePropertyChange
        if(dcAttenDate.getDate().getTime() != attenDate.getTime()) {
            attenDate.setTime(dcAttenDate.getDate().getTime());
            em = "";
            getAtten();
        }
    }//GEN-LAST:event_dcAttenDatePropertyChange

    private void cbEmployessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmployessActionPerformed
        if (firstAction) {
            firstAction = false;
        } else {
            em = empIds.get(cbEmployess.getSelectedIndex());
            getAtten();
        }
    }//GEN-LAST:event_cbEmployessActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddAll;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbEmployess;
    private com.toedter.calendar.JDateChooser dcAttenDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
