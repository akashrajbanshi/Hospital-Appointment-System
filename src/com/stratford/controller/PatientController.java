/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.controller;

import com.stratford.model.Login;
import com.stratford.model.Patient;
import com.stratford.service.PatientServiceImpl;

/**
 *
 * @author Akash Rajbanshi
 */
public class PatientController {
    
    PatientServiceImpl patientServiceImpl;
    
    public void registerPatient(Patient patient,Login login){
        patientServiceImpl = new PatientServiceImpl();       
        patientServiceImpl.registerPatient(patient, login);
    }
    
    public Patient getPatientInfoWithUserNameAndPassword(String patientId){
        patientServiceImpl = new PatientServiceImpl();       
        return patientServiceImpl.getPatientInfoWithUserNameAndPassword(patientId);
    }
    
    public void updatePatientInfo(Patient patient){
        patientServiceImpl = new PatientServiceImpl();       
        patientServiceImpl.updatePatientInfo(patient);
    }
}
