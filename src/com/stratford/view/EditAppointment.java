/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.view;

import com.stratford.controller.AppointmentController;
import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Akash Rajbanshi
 */
public final class EditAppointment extends javax.swing.JDialog {

    AppointmentController appointmentController;
    JTable jTable;
    JButton deleteAppointmentButton;

    /**
     * Creates new form AddAppointment
     *
     * @param parent
     * @param modal
     * @param jTable
     * @param deleteAppointmentButton
     * @param model
     */
    public EditAppointment(java.awt.Frame parent, boolean modal, JTable jTable, JButton deleteAppointmentButton) {
        super(parent, modal);
        this.jTable = jTable;
        this.deleteAppointmentButton = deleteAppointmentButton;
        initComponents();
        this.setResizable(false);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/addAppointment.png")));
        this.loadDoctor();
        this.setTitle("Edit Appointment");

    }

    public void loadAppointmentListTable(String patientID, JTable patientAppointmentTable, JButton deleteAppointmentButton) throws SQLException {
        appointmentController = new AppointmentController();
        List<Appointment> appointmentList = appointmentController.listAppointmentForPatient(patientID);

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

        if (patientAppointmentTable.getRowCount() < 0) {
            deleteAppointmentButton.setEnabled(false);
        }
        patientAppointmentTable.removeColumn(patientAppointmentTable.getColumnModel().getColumn(0));
        patientAppointmentTable.revalidate();
        patientAppointmentTable.repaint();

    }

    private EditAppointment(JFrame jFrame, boolean b) {

    }

    public void loadDoctor() {
        appointmentController = new AppointmentController();
        List<MedicalDoctor> doctors = appointmentController.loadDoctors();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        doctors.stream().forEach((md) -> {
            model.addElement(new MedicalDoctor(md.getId(), md.getName()));
        });

        this.editdoctorNameComboBox.setModel(model);
    }

    public void loadPatientDetails(Appointment appointment, String role) {
        this.loadDoctor();
        this.appointmentIdLabel.setText(Integer.toString(appointment.getId()));
        this.patientIdLabel.setText(Integer.toString(appointment.getPatient().getId()));
        this.editfirstNameTextField.setText(appointment.getPatient().getFirstName());
        this.editlastNameTextField.setText(appointment.getPatient().getLastName());
        this.editdobDateChooser.setDate(appointment.getPatient().getDateOfBirth());

        this.editappointmentDateChooser.setDate(appointment.getAppointmentDate());
        this.editappointmentTimeSpinner.setValue(appointment.getAppointmentTime());

        this.editdoctorNameComboBox.getModel().setSelectedItem(appointment.getDoctor());

        this.editfirstNameTextField.setEnabled(false);
        this.editlastNameTextField.setEnabled(false);
        this.editdobDateChooser.setEnabled(false);
        this.appointmentIdLabel.setVisible(false);
        this.patientIdLabel.setVisible(false);
        if (role != null) {
            if (role.equalsIgnoreCase("Medical Doctor")) {
                this.editdoctorNameComboBox.setEnabled(false);
            }
        }
    }

    public void loadProgressBar() {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth() + 100) / 2;
        final int y = (screenSize.height - this.getHeight() + 250) / 2;

        JDialog dlgProgress = new JDialog(this, "Please wait...", true);//true means that the dialog created is modal
        JLabel lblStatus = new JLabel("Sending Email..."); // this is just a label in which you can indicate the state of the processing

        JProgressBar pbProgress = new JProgressBar(0, 100);
        pbProgress.setIndeterminate(true); //we'll use an indeterminate progress bar

        dlgProgress.add(BorderLayout.NORTH, lblStatus);
        dlgProgress.add(BorderLayout.CENTER, pbProgress);
        dlgProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // prevent the user from closing the dialog
        dlgProgress.setSize(300, 90);
        dlgProgress.setLocation(x, y);

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                sendEmailAndUpdateAppointment();
                return null;
            }

            @Override
            protected void done() {
                dlgProgress.dispose();//close the modal dialog
            }
        };

        sw.execute(); // this will start the processing on a separate thread
        dlgProgress.setVisible(true); //this will block user input as long as the processing task is working
    }

    public void sendEmailAndUpdateAppointment() {
        appointmentController = new AppointmentController();

        Appointment appointment = new Appointment();
        appointment.setId(Integer.parseInt(this.appointmentIdLabel.getText()));
        appointment.getPatient().setId(Integer.parseInt(this.patientIdLabel.getText()));
        appointment.getDoctor().setName(this.editdoctorNameComboBox.getModel().getSelectedItem().toString());
        appointment.setAppointmentDate(this.editappointmentDateChooser.getDate());
        Date date = new Date(editappointmentTimeSpinner.getValue().toString());
        Time time = new Time(date.getTime());
        appointment.setAppointmentTime(time);
        appointmentController.updateAppointment(appointment, "Appointment Updated!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addAppointentPanel = new javax.swing.JPanel();
        editfirstNameLabel = new javax.swing.JLabel();
        editlastNameLabel = new javax.swing.JLabel();
        editdateOfBirthLabel = new javax.swing.JLabel();
        editdoctorNameLabel = new javax.swing.JLabel();
        editappointmentDateLabel = new javax.swing.JLabel();
        editappointmentTimeLabel = new javax.swing.JLabel();
        editfirstNameTextField = new javax.swing.JTextField();
        editlastNameTextField = new javax.swing.JTextField();
        editdoctorNameComboBox = new javax.swing.JComboBox<>();
        editaddPatientSubmitBtn = new javax.swing.JButton();
        editaddPatientCancelBtn = new javax.swing.JButton();
        editdobDateChooser = new com.toedter.calendar.JDateChooser();
        editappointmentDateChooser = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel sm =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        editappointmentTimeSpinner = new javax.swing.JSpinner(sm);
        appointmentIdLabel = new javax.swing.JLabel();
        patientIdLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Make Appointment");
        setResizable(false);

        editfirstNameLabel.setText("First Name:");

        editlastNameLabel.setText("Last Name:");

        editdateOfBirthLabel.setText("Date of Birth:");

        editdoctorNameLabel.setText("Doctor Name:");

        editappointmentDateLabel.setText("Appointment Date:");

        editappointmentTimeLabel.setText("Appointment Time:");

        editfirstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editfirstNameTextFieldActionPerformed(evt);
            }
        });

        editdoctorNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        editdoctorNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editdoctorNameComboBoxActionPerformed(evt);
            }
        });

        editaddPatientSubmitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/submit.png"))); // NOI18N
        editaddPatientSubmitBtn.setText("Submit");
        editaddPatientSubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaddPatientSubmitBtnActionPerformed(evt);
            }
        });

        editaddPatientCancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
        editaddPatientCancelBtn.setText("Cancel");
        editaddPatientCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaddPatientCancelBtnActionPerformed(evt);
            }
        });

        editdobDateChooser.setDateFormatString("yyyy-MM-dd");

        editappointmentDateChooser.setDateFormatString("yyyy-MM-dd");

        JSpinner.DateEditor de = new JSpinner.DateEditor(editappointmentTimeSpinner, "HH:mm:ss");
        editappointmentTimeSpinner.setEditor(de);

        appointmentIdLabel.setText("jLabel1");

        patientIdLabel.setText("jLabel1");

        javax.swing.GroupLayout addAppointentPanelLayout = new javax.swing.GroupLayout(addAppointentPanel);
        addAppointentPanel.setLayout(addAppointentPanelLayout);
        addAppointentPanelLayout.setHorizontalGroup(
            addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAppointentPanelLayout.createSequentialGroup()
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(appointmentIdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientIdLabel))
                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(editfirstNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(editlastNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(editdateOfBirthLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addAppointentPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(editdoctorNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(editappointmentTimeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(addAppointentPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(editappointmentDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editappointmentTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editappointmentDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editlastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editfirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editdobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editdoctorNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addAppointentPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(editaddPatientSubmitBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editaddPatientCancelBtn)
                .addGap(26, 26, 26))
        );

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editappointmentDateChooser, editappointmentTimeSpinner, editdobDateChooser, editdoctorNameComboBox, editfirstNameTextField, editlastNameTextField});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editaddPatientCancelBtn, editaddPatientSubmitBtn});

        addAppointentPanelLayout.setVerticalGroup(
            addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAppointentPanelLayout.createSequentialGroup()
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(appointmentIdLabel)
                    .addComponent(patientIdLabel))
                .addGap(17, 17, 17)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editfirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editfirstNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editlastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editlastNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editdobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editdateOfBirthLabel))
                .addGap(12, 12, 12)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editdoctorNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editdoctorNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editappointmentDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editappointmentDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editappointmentTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editappointmentTimeLabel))
                .addGap(18, 18, 18)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editaddPatientCancelBtn)
                    .addComponent(editaddPatientSubmitBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editappointmentDateChooser, editappointmentTimeSpinner, editdobDateChooser, editdoctorNameComboBox, editfirstNameTextField, editlastNameTextField});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editappointmentDateLabel, editappointmentTimeLabel, editdateOfBirthLabel, editdoctorNameLabel, editfirstNameLabel, editlastNameLabel});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editaddPatientCancelBtn, editaddPatientSubmitBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addAppointentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addAppointentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editaddPatientCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaddPatientCancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_editaddPatientCancelBtnActionPerformed

    private void editaddPatientSubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaddPatientSubmitBtnActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you really want to update the appointment?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                loadProgressBar();
                JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "SUCCESS",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loadAppointmentListTable(this.patientIdLabel.getText(), jTable, deleteAppointmentButton);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(EditAppointment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_editaddPatientSubmitBtnActionPerformed


    private void editfirstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editfirstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editfirstNameTextFieldActionPerformed

    private void editdoctorNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editdoctorNameComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editdoctorNameComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(EditAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditAppointment dialog = new EditAppointment(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addAppointentPanel;
    private javax.swing.JLabel appointmentIdLabel;
    private javax.swing.JButton editaddPatientCancelBtn;
    private javax.swing.JButton editaddPatientSubmitBtn;
    private com.toedter.calendar.JDateChooser editappointmentDateChooser;
    private javax.swing.JLabel editappointmentDateLabel;
    private javax.swing.JLabel editappointmentTimeLabel;
    private javax.swing.JSpinner editappointmentTimeSpinner;
    private javax.swing.JLabel editdateOfBirthLabel;
    private com.toedter.calendar.JDateChooser editdobDateChooser;
    private javax.swing.JComboBox<String> editdoctorNameComboBox;
    private javax.swing.JLabel editdoctorNameLabel;
    private javax.swing.JLabel editfirstNameLabel;
    private javax.swing.JTextField editfirstNameTextField;
    private javax.swing.JLabel editlastNameLabel;
    private javax.swing.JTextField editlastNameTextField;
    private javax.swing.JLabel patientIdLabel;
    // End of variables declaration//GEN-END:variables
}
