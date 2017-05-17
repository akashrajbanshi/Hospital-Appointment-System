/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import com.stratford.model.Appointment;

/**
 *
 * @author Akash Rajbanshi
 */
public interface AppointmentDao {
    public void createAppointment(Appointment appointment);
    public void deleteAppointment(String appointmentId);
    public void updateAppointment(Appointment appointment);
}
