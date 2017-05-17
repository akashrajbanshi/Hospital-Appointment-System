/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.model.Appointment;
import com.stratford.model.MedicalDoctor;
import com.stratford.model.Patient;
import java.util.List;

/**
 *
 * @author Akash Rajbanshi
 */
public interface AppointmentService {

    public void createAppointment(Appointment appointment,String subject);

    public void deleteAppointment(List<String> appointmentIds);

  

    public void updateAppointment(Appointment appointment,String subject);
}
