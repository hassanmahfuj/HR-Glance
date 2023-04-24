package hum.payroll;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hum.main.Company;
import hum.main.User;
import hum.util.Callback;
import hum.util.db;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    String payrollPaymentMonth;

    public PayrollList() {
        payIds = new ArrayList<>();
        initComponents();
        if (User.role.equals("Employee")) {
            em = User.empId;
            btnDelete.setVisible(false);
            cbMonth.setVisible(false);
            cbYear.setVisible(false);
        } else {
            em = "";
        }
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
        payrollPaymentMonth = new SimpleDateFormat("MMMM, yyyy").format(c.getTime());
    }

    void getPay() {
        try {
            ResultSet rs;
            if (em.equals("")) {
                rs = db.get().executeQuery("SELECT a.pay_id, a.emp_id, e.first_name, e.last_name, a.month, a.hours, a.gross_pay, a.deductions, a.net_pay, a.pay_date FROM payroll a LEFT JOIN employees e USING(emp_id) WHERE a.month LIKE ?", month);
            } else {
                rs = db.get().executeQuery("SELECT a.pay_id, a.emp_id, e.first_name, e.last_name, a.month, a.hours, a.gross_pay, a.deductions, a.net_pay, a.pay_date FROM payroll a LEFT JOIN employees e USING(emp_id) WHERE a.emp_id = ? ORDER BY a.month DESC", em);
            }
            payIds.clear();
            dtm.setRowCount(0);
            while (rs.next()) {
                payIds.add(rs.getString(1));
                String fullName = rs.getString(3) + " " + rs.getString(4);
                Date m = rs.getDate(5);
                String m2 = new SimpleDateFormat("MMMM-yyyy").format(m);
                Object[] data = {rs.getString(2), fullName, m2, rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)};
                dtm.addRow(data);
            }
        } catch (SQLException e) {
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

                myDocument.add(new Paragraph(Company.name, FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD)));
                myDocument.add(new Paragraph(Company.address, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));
                myDocument.add(new Paragraph("Phone: " + Company.phone, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));
                myDocument.add(new Paragraph("Email: " + Company.email, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));

                Paragraph a = new Paragraph("Payroll Report " + payrollPaymentMonth);
                a.setAlignment(Element.ALIGN_CENTER);
                myDocument.add(a);

                myDocument.add(new Paragraph(" "));

                table.addCell(new PdfPCell(new Paragraph("ID", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Employee Name", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Month", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Hours", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Gross Pay", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Deductions", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Net Pay", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Pay Date", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD))));

                for (int i = 0; i < dtm.getRowCount(); i++) {
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

    void printSelected(String payId, String empId, String name) {
        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File(empId + " " + name + " - " + payrollPaymentMonth + " Payslip.pdf"));
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();
            try {
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                myDocument.open();

                // from employee
                String department = "";
                String designation = "";
                String bankName = "";
                String accountName = "";
                String accountNumber = "";
                ResultSet rs1 = db.get().executeQuery("SELECT dep_name, des_name, bank_name, bank_holder, bank_number FROM employees e JOIN departments dep ON e.department = dep.id JOIN designations des ON e.designation = des.id WHERE emp_id = ?", empId);
                if (rs1.next()) {
                    department = rs1.getString(1);
                    designation = rs1.getString(2);
                    bankName = rs1.getString(3);
                    accountName = rs1.getString(4);
                    accountNumber = rs1.getString(5);
                }

                // from payroll
                String payDate = "";
                String hoursWork = "";
                String basicSalary = "";
                String houseRent = "";
                String medical = "";
                String conveyance = "";
                String providentFund = "";
                String insurance = "";
                String tax = "";
                String others = "";
                String overtime = "";
                String late = "";
                String leave = "";
                String totalEarning = "";
                String totalDedection = "";
                String netPay = "";
                
                ResultSet rs2 = db.get().executeQuery("SELECT pay_date, hours, basic, house_rent, medical, conveyance, provident_fund, insurance, tax, others, overtime, late, leave1, gross_pay, deductions, net_pay FROM payroll WHERE pay_id = ?", payId);
                if (rs2.next()) {
                    payDate = rs2.getString(1);
                    hoursWork = rs2.getString(2);
                    basicSalary = rs2.getString(3);
                    houseRent = rs2.getString(4);
                    medical = rs2.getString(5);
                    conveyance = rs2.getString(6);
                    providentFund = rs2.getString(7);
                    insurance = rs2.getString(8);
                    tax = rs2.getString(9);
                    others = rs2.getString(10);
                    overtime = rs2.getString(11);
                    late = rs2.getString(12);
                    leave = rs2.getString(13);
                    totalEarning = rs2.getString(14);
                    totalDedection = rs2.getString(15);
                    netPay = rs2.getString(16);
                }

                myDocument.add(new Paragraph(Company.name, FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD)));
                myDocument.add(new Paragraph(Company.address, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));
                myDocument.add(new Paragraph("Phone: " + Company.phone, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));
                myDocument.add(new Paragraph("Email: " + Company.email, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.PLAIN)));

                Paragraph a = new Paragraph("Payslip for the period of " + payrollPaymentMonth);
                a.setAlignment(Element.ALIGN_CENTER);
                myDocument.add(a);
                myDocument.add(new Paragraph(" "));

                PdfPTable empInfoTable = new PdfPTable(4);
                empInfoTable.setWidths(new float[]{1, 2, 1, 2});
                empInfoTable.setWidthPercentage(100);
//                empInfoTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

                empInfoTable.addCell(new PdfPCell(new Paragraph("Employee ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(empId, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Employee Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(name, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Department", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(department, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Designation", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(designation, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Pay Period", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(payrollPaymentMonth, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Pay Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(payDate, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Hours Work", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(hoursWork, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Bank Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(bankName, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Account Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(accountName, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                empInfoTable.addCell(new PdfPCell(new Paragraph("Account Number", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                empInfoTable.addCell(new PdfPCell(new Paragraph(accountNumber, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));

                for (int i = 0; i < empInfoTable.getRows().size(); i++) {
                    PdfPCell[] cells = empInfoTable.getRows().get(i).getCells();
                    for (int j = 0; j < cells.length; j++) {
                        cells[j].setBorderWidth(0);
                    }
                }
                myDocument.add(empInfoTable);
                myDocument.add(new Paragraph(" "));
                
                PdfPTable salaryInfoTable = new PdfPTable(3);
                salaryInfoTable.setWidths(new float[]{1, 1, 1});
                salaryInfoTable.setWidthPercentage(100);

                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Description", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Earnings", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Deductions", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Basic Salary", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(basicSalary, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("House Rent", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(houseRent, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Medical", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(medical, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));

                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Conveyance", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(conveyance, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Provident Fund", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(providentFund, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Insurance", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(insurance, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Tax", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(tax, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Others", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(others, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Overtime", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(overtime, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Late", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(late, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Leave", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(leave, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(totalEarning, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(totalDedection, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph("Net Pay", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD))));
                salaryInfoTable.addCell(new PdfPCell(new Paragraph(netPay, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.PLAIN))));
                
                for (int i = 0; i < salaryInfoTable.getRows().size(); i++) {
                    if(i==0 || i==salaryInfoTable.getRows().size()-1 || i==salaryInfoTable.getRows().size()-2) continue;
                    PdfPCell[] cells = salaryInfoTable.getRows().get(i).getCells();
                    for (int j = 0; j < cells.length; j++) {
                        cells[j].setBorderWidthBottom(0);
                        cells[j].setBorderWidthTop(0);
                    }
                }
                myDocument.add(salaryInfoTable);
                
                myDocument.close();
                JOptionPane.showMessageDialog(null, "Report was successfully generated");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnPrintAll = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnPrintSelected = new javax.swing.JButton();
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
        jPanel1.add(btnPrintAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 100, 30));

        btnDelete.setText("DELETE SELECTED");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 130, 30));

        btnPrintSelected.setText("PRINT SELECTED");
        btnPrintSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintSelectedActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrintSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 130, 30));

        cbMonth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });
        jPanel1.add(cbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 130, 30));

        cbYear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });
        jPanel1.add(cbYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 130, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 960, 50));

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
            if (r == 0) {
                db.get().executeUpdate("DELETE FROM payroll WHERE pay_id = ?", payIds.get(jTable1.getSelectedRow()));
                getPay();
                JOptionPane.showMessageDialog(null, "Item Deleted", "Delete", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrintSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintSelectedActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            printSelected(payIds.get(jTable1.getSelectedRow()), dtm.getValueAt(jTable1.getSelectedRow(), 0).toString(), dtm.getValueAt(jTable1.getSelectedRow(), 1).toString());
        } else {
            JOptionPane.showMessageDialog(null, "No item selected", "Info", 0);
        }
    }//GEN-LAST:event_btnPrintSelectedActionPerformed

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
    private javax.swing.JButton btnPrintAll;
    private javax.swing.JButton btnPrintSelected;
    private javax.swing.JComboBox<String> cbMonth;
    private javax.swing.JComboBox<String> cbYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
