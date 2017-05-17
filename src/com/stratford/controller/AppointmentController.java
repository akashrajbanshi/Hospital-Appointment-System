/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.controller;

import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import com.stratford.model.Patient;
import com.stratford.service.AppointmentServiceImpl;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Akash Rajbanshi
 */
public class AppointmentController {
    
    AppointmentServiceImpl appointmentServiceImpl;
    
    public List<MedicalDoctor> loadDoctors(){
        appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.loadDoctors();
    }
    
    public Patient loadPatientDetails(String patientId) throws SQLException{
        appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.loadPatientDetails(patientId);
    } 
    
    public List<Appointment> listAppointmentForPatient(String patientId) throws SQLException{
        appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.listAppointmentForPatient(patientId);
    }
    
    public List<Appointment> loadAppointmentListTable() throws SQLException{
        appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.listAppointmentTable();
    }
    
    
    public void createAppointment(Appointment appointment,String subject){
        appointmentServiceImpl = new AppointmentServiceImpl();
        appointmentServiceImpl.createAppointment(appointment,subject);
    }
    
    public void deleteAppointment(List<String> appointmentIds){
        appointmentServiceImpl = new AppointmentServiceImpl();
        appointmentServiceImpl.deleteAppointment(appointmentIds);
    }
    
    public void updateAppointment(Appointment appointment,String subject){
        appointmentServiceImpl = new AppointmentServiceImpl();
        appointmentServiceImpl.updateAppointment(appointment,subject);
    }

    public Appointment loadSelectedAppointment(String patientId, String appointmentId, String doctorName) throws SQLException {
        appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.loadSelectedAppointment(patientId,appointmentId,doctorName);
    }

    public List<Appointment> listAppointmentForDoctor(String doctorId) throws SQLException {
       appointmentServiceImpl = new AppointmentServiceImpl();
        return appointmentServiceImpl.listAppointmentForDoctor(doctorId);
    }
   
}
