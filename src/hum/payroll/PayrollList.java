package hum.payroll;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hum.util.Callback;
import hum.util.db;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PayrollList extends javax.swing.JPanel {

    DefaultTableModel dtm;
    ArrayList<String> payIds;
    Callback refresh;
    String em;
    String month;

    public PayrollList(String em) {
        this.em = em;
        payIds = new ArrayList<>();
        initComponents();
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        dtm = (DefaultTableModel) jTable1.getModel();
        setCurrentMonth();
        getPay();

        refresh = () -> {
            getPay();
        };
    }

    void setCurrentMonth() {
        Calendar c = Calendar.getInstance();
        cbMonth.setSelectedItem(new SimpleDateFormat("MMMM").format(c.getTime()));
        cbYear.setSelectedItem(new SimpleDateFormat("yyyy").format(c.getTime()));
    }

    void setMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(cbYear.getSelectedItem().toString()), cbMonth.getSelectedIndex(), 1);
        month = new SimpleDateFormat("yyyy-MM%").format(c.getTime());
    }

    void getPay() {
        try {
            ResultSet rs;
            if (em.equals("")) {
                rs = db.get().executeQuery("SELECT a.pay_id, a.emp_id, e.first_name, e.last_name, a.month, a.hours, a.gross_pay, a.deductions, a.net_pay, a.pay_date FROM payroll a LEFT JOIN employees e USING(emp_id) WHERE a.month LIKE ?", month);
            } else {
                rs = db.get().executeQuery("SELECT a.pay_id, a.emp_id, e.first_name, e.last_name, a.month, a.hours, a.gross_pay, a.deductions, a.net_pay, a.pay_date FROM payroll a LEFT JOIN employees e USING(emp_id) WHERE a.emp_id = ? AND a.month LIKE ?", em, month);
            }
            payIds.clear();
            dtm.setRowCount(0);
            while (rs.next()) {
                payIds.add(rs.getString(1));
                String fullName = rs.getString(3) + " " + rs.getString(4);
                Object[] data = {rs.getString(2), fullName, rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)};
                dtm.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void printAll() {
        String reportMonth = cbYear.getSelectedItem().toString() + " - " + cbMonth.getSelectedItem().toString();

        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File(reportMonth + " Payroll Report.pdf"));
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();

            try {
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(8);
                myDocument.open();

                float[] columnWidths = new float[]{3, 10, 6, 6, 6, 6, 6, 6};
                table.setWidths(columnWidths);

                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Employees List", FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD)));
                myDocument.add(new Paragraph(new Date().toString()));

                table.addCell(new PdfPCell(new Paragraph("ID", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Employee Name", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Month", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Hours", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Gross Pay", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Deductions", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Net Pay", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Pay Date", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));

                for(int i=0; i<dtm.getRowCount(); i++) {
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 0).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 1).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 2).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 3).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 4).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 5).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 6).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(dtm.getValueAt(i, 7).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.PLAIN))));
                }

                myDocument.add(table);
                myDocument.close();
                JOptionPane.showMessageDialog(null, "Report was successfully generated");
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    void printSelected(String empId) {
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnPrintAll = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnPrint1 = new javax.swing.JButton();
        cbMonth = new javax.swing.JComboBox<>();
        cbYear = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPrintAll.setText("PRINT ALL");
        btnPrintAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintAllActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrintAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 100, 30));

        btnDelete.setText("DELETE SELECTED");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 130, 30));

        btnPrint1.setText("PRINT SELECTED");
        btnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrint1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrint1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 130, 30));

        cbMonth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });
        jPanel1.add(cbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 130, 30));

        cbYear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });
        jPanel1.add(cbYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 130, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 960, 30));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmpID", "Employee Name", "Month", "Hours", "Gross Pay", "Deductions", "Net Pay", "Pay Date"
            }
        ));
        jTable1.setRowHeight(30);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 960, 532));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //ps.change(new EmployeeEdit(ps, empIds.get(jTable1.getSelectedRow())));
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnPrintAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintAllActionPerformed
        printAll();
    }//GEN-LAST:event_btnPrintAllActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            int r = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
            if(r == 0) {
                db.get().executeUpdate("DELETE FROM payroll WHERE pay_id = ?", payIds.get(jTable1.getSelectedRow()));
                getPay();
                JOptionPane.showMessageDialog(null, "Item Deleted", "Delete", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrint1ActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            printSelected(dtm.getValueAt(jTable1.getSelectedRow(), 0).toString());
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnPrint1ActionPerformed

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed
        setMonth();
        getPay();
    }//GEN-LAST:event_cbMonthActionPerformed

    private void cbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbYearActionPerformed
        setMonth();
        getPay();
    }//GEN-LAST:event_cbYearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnPrintAll;
    private javax.swing.JComboBox<String> cbMonth;
    private javax.swing.JComboBox<String> cbYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
