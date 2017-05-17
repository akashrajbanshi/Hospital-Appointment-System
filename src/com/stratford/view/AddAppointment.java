/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.view;

import com.stratford.controller.AppointmentController;
import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import com.stratford.model.Patient;
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
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Akash Rajbanshi
 */
public final class AddAppointment extends javax.swing.JDialog {

    AppointmentController appointmentController;
    JTable jTable;

    /**
     * Creates new form AddAppointment
     *
     * @param parent
     * @param modal
     * @param jTable
     * @param tableModel
     */
    public AddAppointment(java.awt.Frame parent, boolean modal, JTable jTable) {
        super(parent, modal);
        this.jTable = jTable;

        initComponents();
        this.setResizable(false);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/addAppointment.png")));
        this.loadDoctor();

    }

    public void loadAppointmentListTable(String patientID, JTable patientAppointmentTable) throws SQLException {
        appointmentController = new AppointmentController();
        List<Appointment> appointmentList = appointmentController.listAppointmentForPatient(patientID);
        String col[] = {"Patient ID", "Appointment Id", "First Name", "Last Name", "Date of Birth", "Doctor Name", "Appoinment Date", "Appointment Time"};

        DefaultTableModel temptable = new DefaultTableModel(col, 0);

        for (Appointment appointment : appointmentList) {
            Object[] data = {appointment.getPatient().getId(), appointment.getId(), appointment.getPatient().getFirstName(), appointment.getPatient().getLastName(),
                appointment.getPatient().getDateOfBirth(), appointment.getDoctor().getName(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime()
            };
            temptable.addRow(data);
        }
        patientAppointmentTable.setModel(temptable);
        patientAppointmentTable.repaint();

        patientAppointmentTable.removeColumn(patientAppointmentTable.getColumnModel().getColumn(0));

    }

    private AddAppointment(JFrame jFrame, boolean b) {

    }

    public void loadDoctor() {
        appointmentController = new AppointmentController();
        List<MedicalDoctor> doctors = appointmentController.loadDoctors();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        doctors.stream().forEach((md) -> {
            model.addElement(new MedicalDoctor(md.getId(), md.getName()));
        });

        this.doctorNameComboBox.setModel(model);
    }

    public void loadPatientDetails(Patient patient) {
        this.patientIdLabel.setText(Integer.toString(patient.getId()));
        this.firstNameTextField.setText(patient.getFirstName());
        this.lastNameTextField.setText(patient.getLastName());
        this.dobDateChooser.setDate(patient.getDateOfBirth());

        this.firstNameTextField.setEnabled(false);
        this.lastNameTextField.setEnabled(false);
        this.dobDateChooser.setEnabled(false);
        this.patientIdLabel.setVisible(false);
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
                sendEmailAndCreateAppointment();
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

    public void sendEmailAndCreateAppointment() {
        appointmentController = new AppointmentController();

        Appointment appointment = new Appointment();
        appointment.getPatient().setId(Integer.parseInt(this.patientIdLabel.getText()));
        appointment.getDoctor().setName(this.doctorNameComboBox.getSelectedItem().toString());
        appointment.setAppointmentDate(this.appointmentDateChooser.getDate());
        Date date = new Date(appointmentTimeSpinner.getValue().toString());
        Time time = new Time(date.getTime());
        appointment.setAppointmentTime(time);
        appointmentController.createAppointment(appointment, "Appointment Scheduled!");
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
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        dateOfBirthLabel = new javax.swing.JLabel();
        doctorNameLabel = new javax.swing.JLabel();
        appointmentDateLabel = new javax.swing.JLabel();
        appointmentTimeLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        lastNameTextField = new javax.swing.JTextField();
        doctorNameComboBox = new javax.swing.JComboBox<>();
        addPatientSubmitBtn = new javax.swing.JButton();
        addPatientCancelBtn = new javax.swing.JButton();
        dobDateChooser = new com.toedter.calendar.JDateChooser();
        appointmentDateChooser = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel sm =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        appointmentTimeSpinner = new javax.swing.JSpinner(sm);
        patientIdLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Make Appointment");
        setResizable(false);

        firstNameLabel.setText("First Name:");

        lastNameLabel.setText("Last Name:");

        dateOfBirthLabel.setText("Date of Birth:");

        doctorNameLabel.setText("Doctor Name:");

        appointmentDateLabel.setText("Appointment Date:");

        appointmentTimeLabel.setText("Appointment Time:");

        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextFieldActionPerformed(evt);
            }
        });

        doctorNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        doctorNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorNameComboBoxActionPerformed(evt);
            }
        });

        addPatientSubmitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/submit.png"))); // NOI18N
        addPatientSubmitBtn.setText("Submit");
        addPatientSubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientSubmitBtnActionPerformed(evt);
            }
        });

        addPatientCancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
        addPatientCancelBtn.setText("Cancel");
        addPatientCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientCancelBtnActionPerformed(evt);
            }
        });

        dobDateChooser.setDateFormatString("yyyy-MM-dd");

        appointmentDateChooser.setDateFormatString("yyyy-MM-dd");

        JSpinner.DateEditor de = new JSpinner.DateEditor(appointmentTimeSpinner, "HH:mm:ss");
        appointmentTimeSpinner.setEditor(de);

        patientIdLabel.setText("jLabel1");

        javax.swing.GroupLayout addAppointentPanelLayout = new javax.swing.GroupLayout(addAppointentPanel);
        addAppointentPanel.setLayout(addAppointentPanelLayout);
        addAppointentPanelLayout.setHorizontalGroup(
            addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAppointentPanelLayout.createSequentialGroup()
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(patientIdLabel))
                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addAppointentPanelLayout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(firstNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lastNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(dateOfBirthLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addAppointentPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(doctorNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(appointmentTimeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(addAppointentPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(appointmentDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(appointmentTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(appointmentDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doctorNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addAppointentPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addPatientSubmitBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPatientCancelBtn)
                .addGap(26, 26, 26))
        );

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {appointmentDateChooser, appointmentTimeSpinner, dobDateChooser, doctorNameComboBox, firstNameTextField, lastNameTextField});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addPatientCancelBtn, addPatientSubmitBtn});

        addAppointentPanelLayout.setVerticalGroup(
            addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAppointentPanelLayout.createSequentialGroup()
                .addComponent(patientIdLabel)
                .addGap(17, 17, 17)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateOfBirthLabel))
                .addGap(12, 12, 12)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doctorNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doctorNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appointmentDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(appointmentDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(appointmentTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(appointmentTimeLabel))
                .addGap(18, 18, 18)
                .addGroup(addAppointentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPatientCancelBtn)
                    .addComponent(addPatientSubmitBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appointmentDateChooser, appointmentTimeSpinner, dobDateChooser, doctorNameComboBox, firstNameTextField, lastNameTextField});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appointmentDateLabel, appointmentTimeLabel, dateOfBirthLabel, doctorNameLabel, firstNameLabel, lastNameLabel});

        addAppointentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addPatientCancelBtn, addPatientSubmitBtn});

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

    private void addPatientCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientCancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_addPatientCancelBtnActionPerformed

    private void addPatientSubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientSubmitBtnActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you really want to book the appointment?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                loadProgressBar();
                JOptionPane.showMessageDialog(this, "New appointment booked successfully!", "SUCCESS",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                loadAppointmentListTable(this.patientIdLabel.getText(), jTable);
            } catch (SQLException ex) {
                Logger.getLogger(AddAppointment.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        }

    }//GEN-LAST:event_addPatientSubmitBtnActionPerformed


    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void doctorNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorNameComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doctorNameComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(AddAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddAppointment dialog = new AddAppointment(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton addPatientCancelBtn;
    private javax.swing.JButton addPatientSubmitBtn;
    private com.toedter.calendar.JDateChooser appointmentDateChooser;
    private javax.swing.JLabel appointmentDateLabel;
    private javax.swing.JLabel appointmentTimeLabel;
    private javax.swing.JSpinner appointmentTimeSpinner;
    private javax.swing.JLabel dateOfBirthLabel;
    private com.toedter.calendar.JDateChooser dobDateChooser;
    private javax.swing.JComboBox<String> doctorNameComboBox;
    private javax.swing.JLabel doctorNameLabel;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel patientIdLabel;
    // End of variables declaration//GEN-END:variables
}
