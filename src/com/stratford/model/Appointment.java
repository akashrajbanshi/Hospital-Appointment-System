/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Akash Rajbanshi
 */
public class Appointment extends User {

    private Date appointmentDate;
    private Time appointmentTime;

    private Patient patient = new Patient();
    private MedicalDoctor doctor = new MedicalDoctor();

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public MedicalDoctor getDoctor() {
        return doctor;
    }

    public void setDoctor(MedicalDoctor doctor) {
        this.doctor = doctor;
    }

}
