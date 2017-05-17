/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.dao.AppointmentDaoImpl;
import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import com.stratford.model.Patient;
import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Akash Rajbanshi
 */
public class AppointmentServiceImpl implements AppointmentService {

    AppointmentDaoImpl appointmentDaoImpl;

    public List<Appointment> listAppointmentForPatient(String patient) throws SQLException {
        appointmentDaoImpl = new AppointmentDaoImpl();
        ResultSet rs = appointmentDaoImpl.listAppointmentForPatient(patient);
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.getPatient().setId(rs.getInt("pId"));
            appointment.getPatient().setFirstName(rs.getString("fm"));
            appointment.getPatient().setLastName(rs.getString("lm"));
            appointment.getPatient().setDateOfBirth(rs.getDate("dob"));
            appointment.getPatient().setEmail(rs.getString("em"));
            appointment.getDoctor().setId(rs.getInt("dId"));
            appointment.getDoctor().setName(rs.getString("dm"));
            appointment.setId(rs.getInt("aId"));
            appointment.setAppointmentDate(rs.getDate("aD"));
            appointment.setAppointmentTime(rs.getTime("aT"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    public void sendEmailToPatient(Appointment appointment,String subject) throws SQLException, MessagingException {
        Patient emailPatient = this.loadPatientDetails(String.valueOf(appointment.getPatient().getId()));
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("arajbanshi1000@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailPatient.getEmail(), false));

        msg.setSubject(subject);
        msg.setText("Hello " + emailPatient.getFirstName() + "," + "\n" + "Your Appointment has been scheduled for "
                + appointment.getAppointmentDate() + " at "
                + appointment.getAppointmentTime(), "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

        t.connect("smtp.gmail.com", "arajbanshi1000", "akash123123");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

    public List<MedicalDoctor> loadDoctors() {
        List<MedicalDoctor> medicalDoctorList = new ArrayList<>();
        appointmentDaoImpl = new AppointmentDaoImpl();
        ResultSet rs = appointmentDaoImpl.loadDoctors();
        try {
            while (rs.next()) {
                MedicalDoctor doctor = new MedicalDoctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                medicalDoctorList.add(doctor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return medicalDoctorList;
    }

    public Patient loadPatientDetails(String patientId) throws SQLException {
        appointmentDaoImpl = new AppointmentDaoImpl();
        ResultSet rs = appointmentDaoImpl.loadPatientDetails(patientId);
        rs.next();

        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setDateOfBirth(rs.getDate("date_of_birth"));
        patient.setEmail(rs.getString("email"));
        return patient;
    }

    @Override
    public void createAppointment(Appointment appointment,String subject) {
        try {
            appointmentDaoImpl = new AppointmentDaoImpl();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = appointment.getAppointmentDate();
            String pDate = df.format(dt);
            try {
                appointment.getDoctor().setId(appointmentDaoImpl.getDoctorIdByName(appointment.getDoctor()));
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(pDate);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                appointment.setAppointmentDate(sqlDate);
            } catch (ParseException ex) {
                Logger.getLogger(PatientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            appointmentDaoImpl.createAppointment(appointment);
            this.sendEmailToPatient(appointment, subject);
        } catch (SQLException | MessagingException ex) {
            Logger.getLogger(AppointmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAppointment(List<String> appointmentIds) {
        appointmentDaoImpl = new AppointmentDaoImpl();
        appointmentIds.stream().forEach((appointmentId) -> {
            appointmentDaoImpl.deleteAppointment(appointmentId);
        });
 
        
    }

   public List<Appointment> listAppointmentTable() throws SQLException {
        appointmentDaoImpl = new AppointmentDaoImpl();
        ResultSet rs = appointmentDaoImpl.listAppointment();
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.getPatient().setId(rs.getInt("pId"));
            appointment.getPatient().setFirstName(rs.getString("fm"));
            appointment.getPatient().setLastName(rs.getString("lm"));
            appointment.getPatient().setDateOfBirth(rs.getDate("dob"));
            appointment.getPatient().setEmail(rs.getString("em"));
            appointment.getDoctor().setId(rs.getInt("dId"));
            appointment.getDoctor().setName(rs.getString("dm"));
            appointment.setId(rs.getInt("aId"));
            appointment.setAppointmentDate(rs.getDate("aD"));
            appointment.setAppointmentTime(rs.getTime("aT"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    @Override
    public void updateAppointment(Appointment appointment,String subject) {
        appointmentDaoImpl = new AppointmentDaoImpl();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = appointment.getAppointmentDate();
        String pDate = df.format(dt);
        try {
            int doctorId = appointmentDaoImpl.getDoctorIdByName(appointment.getDoctor());
            Date date;
            date = new SimpleDateFormat("yyyy-MM-dd").parse(pDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            appointment.setAppointmentDate(sqlDate);
            appointment.getDoctor().setId(doctorId);
            appointmentDaoImpl.updateAppointment(appointment);
            this.sendEmailToPatient(appointment, subject);
        } catch (SQLException | ParseException | MessagingException ex) {
            Logger.getLogger(AppointmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Appointment loadSelectedAppointment(String patientId, String appointmentId, String doctorName) throws SQLException {
        appointmentDaoImpl = new AppointmentDaoImpl();
        int doctorId = appointmentDaoImpl.getDoctorIdByName(doctorName);
        ResultSet rs = appointmentDaoImpl.loadSelectedAppointment(patientId, appointmentId, doctorId);
        rs.next();

        Appointment appointment = new Appointment();
        appointment.getPatient().setId(rs.getInt("pId"));
        appointment.getPatient().setFirstName(rs.getString("fm"));
        appointment.getPatient().setLastName(rs.getString("lm"));
        appointment.getPatient().setDateOfBirth(rs.getDate("dob"));
        appointment.setAppointmentDate(rs.getDate("aD"));
        appointment.setAppointmentTime(rs.getTime("aT"));
        appointment.getDoctor().setId(rs.getInt("dId"));
        appointment.getDoctor().setName(rs.getString("dM"));
        
        appointment.setId(rs.getInt("aId"));

        return appointment;
    }

    public List<Appointment> listAppointmentForDoctor(String doctorId) throws SQLException {
        appointmentDaoImpl = new AppointmentDaoImpl();
        ResultSet rs = appointmentDaoImpl.listAppointmentForDoctor(doctorId);
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.getPatient().setId(rs.getInt("pId"));
            appointment.getPatient().setFirstName(rs.getString("fm"));
            appointment.getPatient().setLastName(rs.getString("lm"));
            appointment.getPatient().setDateOfBirth(rs.getDate("dob"));
            appointment.getPatient().setEmail(rs.getString("em"));
            appointment.getDoctor().setId(rs.getInt("dId"));
            appointment.getDoctor().setName(rs.getString("dm"));
            appointment.setId(rs.getInt("aId"));
            appointment.setAppointmentDate(rs.getDate("aD"));
            appointment.setAppointmentTime(rs.getTime("aT"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

}
