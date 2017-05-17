/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.dao.PatientDaoImpl;
import com.stratford.model.Login;
import com.stratford.model.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akash Rajbanshi
 */
public class PatientServiceImpl implements PatientService {

    PatientDaoImpl patientDaoImpl;

    @Override
    public void registerPatient(Patient patient, Login login) {
        patientDaoImpl = new PatientDaoImpl();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = patient.getDateOfBirth();
        String pDate = df.format(dt);

        try {
            Date date;
            date = new SimpleDateFormat("yyyy-MM-dd").parse(pDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            patient.setDateOfBirth(sqlDate);
        } catch (ParseException ex) {
            Logger.getLogger(PatientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        patientDaoImpl.registerPatient(patient, login);
    }

    public Patient getPatientInfoWithUserNameAndPassword(String patientId) {
        Patient patient = new Patient();
        try {
            patientDaoImpl = new PatientDaoImpl();
            ResultSet rs = patientDaoImpl.getPatientInfoWithUserNameAndPassword(patientId);
            rs.next();

            patient.setFirstName(rs.getString("fm"));
            patient.setLastName(rs.getString("lm"));
            patient.setDateOfBirth(rs.getDate("dob"));
            patient.setEmail(rs.getString("em"));
            patient.getLogin().setUsername(rs.getString("un"));
            patient.getLogin().setPassword(rs.getString("pwd"));
            patient.getLogin().setId(rs.getInt("lId"));
        } catch (SQLException ex) {
            Logger.getLogger(PatientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }
    
    public void updatePatientInfo(Patient patient){
        try {
            patientDaoImpl = new PatientDaoImpl();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = patient.getDateOfBirth();
            String pDate = df.format(dt);
            Date date;
            date = new SimpleDateFormat("yyyy-MM-dd").parse(pDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            patient.setDateOfBirth(sqlDate);
            patientDaoImpl.updatePatientInfo(patient);
        } catch (ParseException ex) {
            Logger.getLogger(PatientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
