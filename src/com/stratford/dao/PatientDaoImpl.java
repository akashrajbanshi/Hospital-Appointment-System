/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import com.stratford.model.Login;
import com.stratford.model.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akash Rajbanshi
 */
public class PatientDaoImpl implements PatientDao {

    DBConnection dBConnection;

    @Override
    public void registerPatient(Patient patient, Login login) {
        try {
            dBConnection = new DBConnection();
            int patientId, loginId;
            dBConnection.update("INSERT INTO patient (first_name,last_name,date_of_birth,email) VALUES ('" + patient.getFirstName() + "','" + patient.getLastName() + "','" + patient.getDateOfBirth() + "','" + patient.getEmail() + "')");
            ResultSet rs = dBConnection.statement.getGeneratedKeys();
            rs.next();
            patientId = rs.getInt(1);
            dBConnection.update("INSERT INTO login (username,password,role) VALUES ('" + login.getUsername() + "','" + login.getPassword() + "','" + login.getUserRole() + "')");
            ResultSet rs2 = dBConnection.statement.getGeneratedKeys();
            rs2.next();
            loginId = rs2.getInt(1);
            dBConnection.update("INSERT INTO patient_login (patient_id,login_id) VALUES('" + patientId + "','" + loginId + "')");

        } catch (SQLException ex) {
            Logger.getLogger(PatientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getPatientInfoWithUserNameAndPassword(String patientId) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("Select patient.first_name fm ,patient.last_name lm , "
                + " patient.date_of_birth dob,patient.email em,login.id lId ,login.username un ,"
                + " login.password pwd "
                + " FROM patient,login,patient_login "
                + " WHERE patient.id = patient_login.patient_id "
                + " AND login.id = patient_login.login_id "
                + " AND patient.id = '" + patientId + "' ");
    }

    public void updatePatientInfo(Patient patient) {
        dBConnection = new DBConnection();

        dBConnection.update("UPDATE patient SET first_name = '" + patient.getFirstName() + "',last_name = '" + patient.getLastName() + "',date_of_birth = '" + patient.getDateOfBirth() + "',email = '" + patient.getEmail() + "' WHERE id = '" + patient.getId() + "'");

        dBConnection.update("UPDATE login SET username = '" + patient.getLogin().getUsername() + "',password = '" + patient.getLogin().getPassword() + "' WHERE id = '" + patient.getLogin().getId() + "'");
    }

}
