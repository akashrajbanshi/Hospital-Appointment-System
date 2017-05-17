/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.model.Login;
import com.stratford.model.Patient;

/**
 *
 * @author Akash Rajbanshi
 */
public interface PatientService {
    
    public void registerPatient(Patient patient, Login login);
}
