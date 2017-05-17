/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.view;

import com.stratford.controller.AppointmentController;
import com.stratford.model.Appointment;
import com.stratford.model.Patient;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Akash Rajbanshi
 */
public class MainFrame extends javax.swing.JFrame {

    AppointmentController appointmentController;
    
    String doctorRole;

    public void setUserLoginForPatient(String username, int id) throws SQLException {
        this.loginUserLabel.setText(username.toUpperCase());
        this.userIdLabel.setText("ID: " + "(" + Integer.toString(id) + ")");
        this.hiddenUserId.setVisible(false);
        this.hiddenUserId.setText(Integer.toString(id));
        loadAppointmentListTable(String.valueOf(id));
    }

    public void setUserLoginForOthers(String username) throws SQLException {
        this.loginUserLabel.setText(username.toUpperCase());
        this.editPatientInfo.setVisible(false);
        this.userIdLabel.setVisible(false);
        this.hiddenUserId.setVisible(false);
        this.addAppointmentPatientBtn.setEnabled(false);

        loadAppointmentListTable();
    }
    
    public void setUserLoginForDoctor(String username,int id,String role) throws SQLException {
        this.loginUserLabel.setText(username.toUpperCase());
        this.editPatientInfo.setVisible(false);
        this.userIdLabel.setVisible(false);
        this.hiddenUserId.setVisible(false);
        this.addAppointmentPatientBtn.setEnabled(false);
        this.doctorRole = role;
        loadAppointmentListForDoctor(String.valueOf(id));
    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setVisible(true);
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/patient.png")));
        this.setSize(xsize, ysize);
        try {
            loadAppointmentListTable(this.hiddenUserId.getText());
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void loadAppointmentListTable(String patientId) throws SQLException {
        appointmentController = new AppointmentController();
        List<Appointment> appointmentList = appointmentController.listAppointmentForPatient(patientId);
        String col[] = {"Patient ID", "Appointment Id", "First Name", "Last Name", "Date of Birth", "Doctor Name", "Appoinment Date", "Appointment Time"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (Appointment appointment : appointmentList) {
            Object[] data = {appointment.getPatient().getId(), appointment.getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(),
                appointment.getPatient().getDateOfBirth(), appointment.getDoctor().getName(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime()
            };
            tableModel.addRow(data);
        }
        patientAppointmentTable.setModel(tableModel);
        patientAppointmentTable.repaint();
        tableModel.fireTableDataChanged();
        if (patientAppointmentTable.getRowCount() < 1) {
            deleteAppointmentPatientBtn.setEnabled(false);
        }
        patientAppointmentTable.removeColumn(patientAppointmentTable.getColumnModel().getColumn(0));

    }


    public void loadAppointmentListForDoctor(String doctorId) throws SQLException {
        appointmentController = new AppointmentController();
        List<Appointment> appointmentList = appointmentController.listAppointmentForDoctor(doctorId);
        String col[] = {"Patient ID", "Appointment Id", "First Name", "Last Name", "Date of Birth", "Doctor Name", "Appoinment Date", "Appointment Time"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (Appointment appointment : appointmentList) {
            Object[] data = {appointment.getPatient().getId(), appointment.getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(),
                appointment.getPatient().getDateOfBirth(), appointment.getDoctor().getName(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime()
            };
            tableModel.addRow(data);
        }
        patientAppointmentTable.setModel(tableModel);
        patientAppointmentTable.repaint();
        tableModel.fireTableDataChanged();
        if (patientAppointmentTable.getRowCount() < 1) {
            deleteAppointmentPatientBtn.setEnabled(false);
        }
        patientAppointmentTable.removeColumn(patientAppointmentTable.getColumnModel().getColumn(0));

    }

    public void loadAppointmentListTable() throws SQLException {
        appointmentController = new AppointmentController();
        List<Appointment> appointmentList = appointmentController.loadAppointmentListTable();
        String col[] = {"Patient ID", "Appointment Id", "First Name", "Last Name", "Date of Birth", "Doctor Name", "Appoinment Date", "Appointment Time"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (Appointment appointment : appointmentList) {
            Object[] data = {appointment.getPatient().getId(), appointment.getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(),
                appointment.getPatient().getDateOfBirth(), appointment.getDoctor().getName(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime()
            };
            tableModel.addRow(data);
        }
        patientAppointmentTable.setModel(tableModel);
        patientAppointmentTable.repaint();
        tableModel.fireTableDataChanged();
        if (patientAppointmentTable.getRowCount() < 1) {
            deleteAppointmentPatientBtn.setEnabled(false);
        }
        patientAppointmentTable.removeColumn(patientAppointmentTable.getColumnModel().getColumn(0));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addAppointmentPatientBtn = new javax.swing.JButton();
        patientFramePanel = new javax.swing.JPanel();
        helloLabel = new javax.swing.JLabel();
        loginUserLabel = new javax.swing.JLabel();
        userIdLabel = new javax.swing.JLabel();
        hiddenUserId = new javax.swing.JLabel();
        patientLogOutBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        patientAppointmentTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        editPatientInfo = new javax.swing.JButton();
        deleteAppointmentPatientBtn = new javax.swing.JButton();
        searchAppointment = new javax.swing.JTextField();
        patientMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Patient Registration System");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 153, 153));

        addAppointmentPatientBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addAppointment.png"))); // NOI18N
        addAppointmentPatientBtn.setToolTipText("Add Appointment");
        addAppointmentPatientBtn.setFocusable(false);
        addAppointmentPatientBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addAppointmentPatientBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addAppointmentPatientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAppointmentPatientBtnActionPerformed(evt);
            }
        });

        helloLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        helloLabel.setText("HELLO");
        patientFramePanel.add(helloLabel);

        loginUserLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        loginUserLabel.setText("jLabel2 ");
        patientFramePanel.add(loginUserLabel);

        userIdLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        userIdLabel.setText("jLabel1");
        patientFramePanel.add(userIdLabel);

        hiddenUserId.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        hiddenUserId.setText("jLabel1");
        hiddenUserId.setEnabled(false);
        hiddenUserId.setOpaque(true);
        patientFramePanel.add(hiddenUserId);

        patientLogOutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        patientLogOutBtn.setText("Log Out");
        patientLogOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientLogOutBtnActionPerformed(evt);
            }
        });

        patientAppointmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        patientAppointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patientAppointmentTableMouseClicked(evt);
            }
        });
        patientAppointmentTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                patientAppointmentTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(patientAppointmentTable);

        editPatientInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editUser.png"))); // NOI18N
        editPatientInfo.setToolTipText("Edit Profile");
        editPatientInfo.setFocusable(false);
        editPatientInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editPatientInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editPatientInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPatientInfoActionPerformed(evt);
            }
        });

        deleteAppointmentPatientBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        deleteAppointmentPatientBtn.setToolTipText("Delete Appointment");
        deleteAppointmentPatientBtn.setEnabled(false);
        deleteAppointmentPatientBtn.setFocusable(false);
        deleteAppointmentPatientBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteAppointmentPatientBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteAppointmentPatientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAppointmentPatientBtnActionPerformed(evt);
            }
        });

        searchAppointment.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchAppointment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchAppointmentKeyReleased(evt);
            }
        });

        fileMenu.setText("File");

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        patientMenuBar.add(fileMenu);

        setJMenuBar(patientMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addAppointmentPatientBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteAppointmentPatientBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(patientFramePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editPatientInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientLogOutBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addAppointmentPatientBtn)
                                .addComponent(editPatientInfo)
                                .addComponent(deleteAppointmentPatientBtn)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(patientLogOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientFramePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchAppointment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void addAppointmentPatientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAppointmentPatientBtnActionPerformed

        appointmentController = new AppointmentController();
        try {
            Patient patient = appointmentController.loadPatientDetails(this.hiddenUserId.getText());

            AddAppointment addAppointment = new AddAppointment(this, true, patientAppointmentTable);
            addAppointment.loadPatientDetails(patient);
            addAppointment.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_addAppointmentPatientBtnActionPerformed

    private void patientLogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientLogOutBtnActionPerformed
        this.dispose();
        LoginForm login = new LoginForm();
        login.setVisible(true);

    }//GEN-LAST:event_patientLogOutBtnActionPerformed

    private void patientAppointmentTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_patientAppointmentTableKeyPressed
    }//GEN-LAST:event_patientAppointmentTableKeyPressed

    private void editPatientInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPatientInfoActionPerformed
        PatientDialogForm patientDialogForm = new PatientDialogForm(this, true, patientAppointmentTable);
        patientDialogForm.setPatientInfo(hiddenUserId.getText());
        patientDialogForm.setVisible(true);
    }//GEN-LAST:event_editPatientInfoActionPerformed

    private void deleteAppointmentPatientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAppointmentPatientBtnActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you really want to delete the appointment?");
        if (patientAppointmentTable.getRowCount() >= 1) {
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    int[] selectedRows = patientAppointmentTable.getSelectedRows();
                    List<String> appointmentIds = new ArrayList<>();
                    for (int selectedRow : selectedRows) {
                        appointmentIds.add((patientAppointmentTable.getModel().getValueAt(selectedRow, 1)).toString());

                    }
                    appointmentController.deleteAppointment(appointmentIds);
                    JOptionPane.showMessageDialog(this, "Delete Successful!", "SUCCESS",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    loadAppointmentListTable(this.hiddenUserId.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_deleteAppointmentPatientBtnActionPerformed

    private void patientAppointmentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patientAppointmentTableMouseClicked

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editAppointment(doctorRole);
        } else {
            this.deleteAppointmentPatientBtn.setEnabled(true);
        }
    }//GEN-LAST:event_patientAppointmentTableMouseClicked


    private void searchAppointmentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchAppointmentKeyReleased

        String query = searchAppointment.getText();
        this.filter(query);
    }//GEN-LAST:event_searchAppointmentKeyReleased

    public void editAppointment(String role) {
        try {
            appointmentController = new AppointmentController();

            String patientId = (patientAppointmentTable.getModel().getValueAt(patientAppointmentTable.getSelectedRow(), 0)).toString();
            String appointmentId = (patientAppointmentTable.getModel().getValueAt(patientAppointmentTable.getSelectedRow(), 1)).toString();
            String doctorName = (patientAppointmentTable.getModel().getValueAt(patientAppointmentTable.getSelectedRow(), 5)).toString();

            Appointment appointment = appointmentController.loadSelectedAppointment(patientId, appointmentId, doctorName);
            EditAppointment editAppointment = new EditAppointment(this, true, patientAppointmentTable, this.deleteAppointmentPatientBtn);
            editAppointment.loadPatientDetails(appointment,role);
            editAppointment.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>((DefaultTableModel) patientAppointmentTable.getModel());
        patientAppointmentTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAppointmentPatientBtn;
    private javax.swing.JButton deleteAppointmentPatientBtn;
    private javax.swing.JButton editPatientInfo;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel helloLabel;
    private javax.swing.JLabel hiddenUserId;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loginUserLabel;
    private javax.swing.JTable patientAppointmentTable;
    private javax.swing.JPanel patientFramePanel;
    private javax.swing.JButton patientLogOutBtn;
    private javax.swing.JMenuBar patientMenuBar;
    private javax.swing.JTextField searchAppointment;
    private javax.swing.JLabel userIdLabel;
    // End of variables declaration//GEN-END:variables
}
