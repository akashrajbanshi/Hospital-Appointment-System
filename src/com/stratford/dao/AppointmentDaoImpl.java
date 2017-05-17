/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Akash Rajbanshi
 */
public class AppointmentDaoImpl implements AppointmentDao {

    DBConnection dBConnection;

    public ResultSet listAppointmentForPatient(String patientId) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select patient.id pId , patient.first_name fm ,patient.last_name lm , "
                + " patient.date_of_birth dob,patient.email em,medical_doctor.id dId,medical_doctor.name dm ,"
                + " appointment_details.id aId , appointment_details.appointment_date aD , appointment_details.appointment_time aT "
                + " FROM patient,medical_doctor,appointment_details "
                + " WHERE patient.id = appointment_details.patient_id "
                + " AND medical_doctor.id = appointment_details.doctor_id "
                + " AND patient.id = '" + patientId + "' "
                + "ORDER BY aId");
    }
    
     public ResultSet listAppointment() {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select patient.id pId , patient.first_name fm ,patient.last_name lm , "
                + " patient.date_of_birth dob,patient.email em,medical_doctor.id dId,medical_doctor.name dm ,"
                + " appointment_details.id aId , appointment_details.appointment_date aD , appointment_details.appointment_time aT "
                + " FROM patient,medical_doctor,appointment_details "
                + " WHERE patient.id = appointment_details.patient_id "
                + " AND medical_doctor.id = appointment_details.doctor_id "
                + " ORDER BY aId");
    }
    
    public ResultSet loadSelectedAppointment(String patientId,String appoinmentId, int doctorId) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select patient.id pId , patient.first_name fm ,patient.last_name lm , "
                + " patient.date_of_birth dob,medical_doctor.id dId ,medical_doctor.name dM , "
                + " appointment_details.id aId , appointment_details.appointment_date aD , appointment_details.appointment_time aT "
                + " FROM patient,medical_doctor,appointment_details "
                + " WHERE patient.id = appointment_details.patient_id "
                + " AND medical_doctor.id = appointment_details.doctor_id "
                + " AND appointment_details.id = '" + appoinmentId + "' ");
    }

    public ResultSet loadDoctors() {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select * FROM medical_doctor");
    }

    public int getDoctorIdByName(MedicalDoctor doctor) throws SQLException {
        dBConnection = new DBConnection();
        ResultSet rs = dBConnection.retrive("Select * FROM medical_doctor WHERE name = '" + doctor.getName() + "'");
        rs.next();
        return rs.getInt("id");
    }

    public int getDoctorIdByName(String doctorName) throws SQLException {
        dBConnection = new DBConnection();
        ResultSet rs = dBConnection.retrive("Select * FROM medical_doctor WHERE name = '" + doctorName + "'");
        rs.next();
        return rs.getInt("id");
    }

    public ResultSet loadPatientDetails(String patientId) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select * FROM patient WHERE id = '" + patientId + "'");
    }

    @Override
    public void createAppointment(Appointment appointment) {
        dBConnection = new DBConnection();
        dBConnection.update("INSERT INTO appointment_details (patient_id,doctor_id,appointment_date,appointment_time) VALUES ('" + appointment.getPatient().getId() + "','" + appointment.getDoctor().getId() + "','" + appointment.getAppointmentDate() + "','" + appointment.getAppointmentTime() + "')");
    }

    @Override
    public void deleteAppointment(String appointmentId) {
        dBConnection = new DBConnection();
        dBConnection.update("Delete FROM appointment_details WHERE id = '" + appointmentId + "'");
    }

    
    @Override
    public void updateAppointment(Appointment appointment) {
        dBConnection = new DBConnection();
        dBConnection.update("UPDATE appointment_details SET doctor_id = '" + appointment.getDoctor().getId() + "',appointment_date ='" + appointment.getAppointmentDate() + "',appointment_time='" + appointment.getAppointmentTime() + "' WHERE id = '"+appointment.getId()+"'");
    }

    public ResultSet listAppointmentForDoctor(String doctorId) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select patient.id pId , patient.first_name fm ,patient.last_name lm , "
                + " patient.date_of_birth dob,patient.email em,medical_doctor.id dId,medical_doctor.name dm ,"
                + " appointment_details.id aId , appointment_details.appointment_date aD , appointment_details.appointment_time aT "
                + " FROM patient,medical_doctor,appointment_details "
                + " WHERE patient.id = appointment_details.patient_id "
                + " AND medical_doctor.id = appointment_details.doctor_id "
                + " AND medical_doctor.id = '" + doctorId + "' "
                + "ORDER BY aId");
    }

}
