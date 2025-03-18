/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Form;

import Tabbed.TabbedForm;
import java.sql.*;
import raven.toast.Notifications;

/**
 *
 * @author blesDev
 */
public class AddTestt extends TabbedForm {
    
    ResultSet rs;
    Connection con;
    PreparedStatement ps;
    
    String url = "jdbc:mysql://localhost:3306/stums";
    String username = "root";
    String password = "";
    
    String checkQuery = "SELECT * FROM studentt WHERE LRN = ?";
    String insertQuery = "INSERT INTO studentt (LRN, LastName, FirstName, MiddleName, Strand, Sex, SchoolYr, Semester, Scholar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateQuery = "UPDATE studentt SET LastName = ?, FirstName = ?, MiddleName = ?, Strand = ?, Sex = ?, SchoolYr = ?, Scholar = ?, Semester = ? WHERE LRN = ?";
    
    public AddTestt() {
        initComponents();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtLRN = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtLN = new javax.swing.JTextField();
        txtFN = new javax.swing.JTextField();
        txtMN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnADD = new javax.swing.JButton();
        cmboxStrand = new javax.swing.JComboBox<>();
        cmboxSex = new javax.swing.JComboBox<>();
        cmboxSchoolYr = new javax.swing.JComboBox<>();
        cmboxSem = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmboxCase = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmboxScholar = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(1270, 620));
        jPanel1.setPreferredSize(new java.awt.Dimension(1270, 620));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLRN.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        txtLRN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLRNActionPerformed(evt);
            }
        });
        jPanel1.add(txtLRN, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 157, 170, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("LRN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 137, 70, -1));

        txtLN.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jPanel1.add(txtLN, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 227, 170, 40));

        txtFN.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jPanel1.add(txtFN, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 170, 40));

        txtMN.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jPanel1.add(txtMN, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 170, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Last Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 197, 100, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("First Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 267, 100, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Middle Name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 337, 100, 40));

        btnADD.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnADD.setText("ADD");
        btnADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADDActionPerformed(evt);
            }
        });
        jPanel1.add(btnADD, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, 100, 40));

        cmboxStrand.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxStrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STEM", "GAS", "ABM", "ICT", "HE", "ADAS" }));
        jPanel1.add(cmboxStrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 227, 130, 40));

        cmboxSex.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jPanel1.add(cmboxSex, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 227, 140, 40));

        cmboxSchoolYr.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxSchoolYr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020-2021", "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026" }));
        jPanel1.add(cmboxSchoolYr, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 157, 150, 40));

        cmboxSem.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxSem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1st Sem", "2nd Sem" }));
        jPanel1.add(cmboxSem, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 157, 120, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Strand");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 197, 50, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("School Year");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 137, 90, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Semester");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 137, 90, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Sex");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 197, 30, 40));

        cmboxCase.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxCase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Bullying", "Classroom Dishonest", "Acadamic Dishonesty", "Selling of Alcoholic Bevarages", "Drinking of Alcholic Bevarages" }));
        cmboxCase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxCaseActionPerformed(evt);
            }
        });
        jPanel1.add(cmboxCase, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, 278, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Scholar");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 340, 70, 30));

        cmboxScholar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        cmboxScholar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "MSD", "4Ps", "Barangay" }));
        cmboxScholar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxScholarActionPerformed(evt);
            }
        });
        jPanel1.add(cmboxScholar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 278, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Violation");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 270, 70, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDActionPerformed
        txtLRN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String lrn = txtLRN.getText().trim();
                if (lrn.isEmpty()) {
                    txtLN.setText("");
                    txtFN.setText("");
                    txtMN.setText("");
                    cmboxStrand.setSelectedIndex(0);
                    cmboxSex.setSelectedIndex(0);
                    cmboxSchoolYr.setSelectedIndex(0);
                    cmboxSem.setSelectedIndex(0);
                    cmboxCase.setSelectedIndex(0);
                    cmboxScholar.setSelectedIndex(0);
                    btnADD.setText("ADD");
                    return;
                }

                try (
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM studentt WHERE LRN = ?")
                ) {
                    ps.setString(1, lrn);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        txtLN.setText(rs.getString("LastName"));
                        txtFN.setText(rs.getString("FirstName"));
                        txtMN.setText(rs.getString("MiddleName"));
                        cmboxStrand.setSelectedItem(rs.getString("Strand"));
                        cmboxSex.setSelectedItem(rs.getString("Sex"));
                        cmboxSchoolYr.setSelectedItem(rs.getString("SchoolYr"));
                        cmboxSem.setSelectedItem(rs.getString("Semester"));
                        cmboxCase.setSelectedItem(rs.getString("Case"));
                        cmboxScholar.setSelectedItem(rs.getString("Scholar"));
                        btnADD.setText("UPDATE");
                    } else {
                        txtLN.setText("");
                        txtFN.setText("");
                        txtMN.setText("");
                        cmboxStrand.setSelectedIndex(0);
                        cmboxSex.setSelectedIndex(0);
                        cmboxSchoolYr.setSelectedIndex(0);
                        cmboxSem.setSelectedIndex(0);
                        cmboxCase.setSelectedIndex(0);
                        cmboxScholar.setSelectedIndex(0);
                        btnADD.setText("ADD");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnADD.addActionListener(e -> {
            try {
                String lrn = txtLRN.getText().trim();
                if (lrn.isEmpty() || !lrn.matches("\\d+")) {
                    return;
                }

                try (
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
                    PreparedStatement ps = ("UPDATE".equals(btnADD.getText()))
                        ? con.prepareStatement("UPDATE studentt SET LastName = ?, FirstName = ?, MiddleName = ?, Strand = ?, Sex = ?, SchoolYr = ?, Semester = ?, Scholar = ?, `Case` = ? WHERE LRN = ?")
                        : con.prepareStatement("INSERT INTO studentt (LRN, LastName, FirstName, MiddleName, Strand, Sex, SchoolYr, Semester, Scholar, `Case`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                ) {
                    if ("UPDATE".equals(btnADD.getText())) {
                        ps.setString(1, txtLN.getText());
                        ps.setString(2, txtFN.getText());
                        ps.setString(3, txtMN.getText());
                        ps.setString(4, cmboxStrand.getSelectedItem().toString());
                        ps.setString(5, cmboxSex.getSelectedItem().toString());
                        ps.setString(6, cmboxSchoolYr.getSelectedItem().toString());
                        ps.setString(7, cmboxSem.getSelectedItem().toString());
                        ps.setString(8, cmboxScholar.getSelectedItem().toString());
                        ps.setString(9, cmboxCase.getSelectedItem().toString());
                        ps.setString(10, lrn);
                    } else {
                        ps.setString(1, lrn);
                        ps.setString(2, txtLN.getText());
                        ps.setString(3, txtFN.getText());
                        ps.setString(4, txtMN.getText());
                        ps.setString(5, cmboxStrand.getSelectedItem().toString());
                        ps.setString(6, cmboxSex.getSelectedItem().toString());
                        ps.setString(7, cmboxSchoolYr.getSelectedItem().toString());
                        ps.setString(8, cmboxSem.getSelectedItem().toString());
                        ps.setString(9, cmboxScholar.getSelectedItem().toString());
                        ps.setString(10, cmboxCase.getSelectedItem().toString());
                    }
                    ps.executeUpdate();
                    Notifications.getInstance().show(Notifications.Type.INFO, ("UPDATE".equals(btnADD.getText())) ? "Updated Successfully" : "Added Successfully");
                }

                txtLRN.setText("");
                txtLN.setText("");
                txtFN.setText("");
                txtMN.setText("");
                cmboxStrand.setSelectedIndex(0);
                cmboxSex.setSelectedIndex(0);
                cmboxSchoolYr.setSelectedIndex(0);
                cmboxSem.setSelectedIndex(0);
                cmboxCase.setSelectedIndex(0);
                cmboxScholar.setSelectedIndex(0);
                btnADD.setText("ADD");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }//GEN-LAST:event_btnADDActionPerformed

    private void txtLRNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLRNActionPerformed
        
    }//GEN-LAST:event_txtLRNActionPerformed

    private void cmboxCaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxCaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmboxCaseActionPerformed

    private void cmboxScholarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxScholarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmboxScholarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnADD;
    private javax.swing.JComboBox<String> cmboxCase;
    private javax.swing.JComboBox<String> cmboxScholar;
    private javax.swing.JComboBox<String> cmboxSchoolYr;
    private javax.swing.JComboBox<String> cmboxSem;
    private javax.swing.JComboBox<String> cmboxSex;
    private javax.swing.JComboBox<String> cmboxStrand;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txtLRN;
    private javax.swing.JTextField txtMN;
    // End of variables declaration//GEN-END:variables
}
