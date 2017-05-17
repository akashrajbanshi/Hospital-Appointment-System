/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import com.stratford.model.Login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akash Rajbanshi
 */
public class LoginDaoImpl implements LoginDao {

    DBConnection dBConnection;

    @Override
    public ResultSet authentication(Login loginModel) {
        dBConnection = new DBConnection();
        return dBConnection.retrive("SELECT * FROM login "
                + "WHERE "
                + "username = '" + loginModel.getUsername() + "' "
                + "AND password = '" + loginModel.getPassword() + "'"
                + " AND role='" + loginModel.getUserRole() + "'");
    }

    @Override
    public int getUserId(Login loginModel) {
        dBConnection = new DBConnection();
        int patientId = 0;
        try {
            ResultSet rs = dBConnection.retrive("SELECT * FROM login "
                    + "WHERE "
                    + "username = '" + loginModel.getUsername() + "'");

            rs.next();
            int loginId = rs.getInt(1);

            ResultSet rs1 = dBConnection.retrive("SELECT * FROM patient_login "
                    + "WHERE "
                    + "login_id = '" + loginId + "'");
            rs1.next();
            patientId = rs1.getInt(2);

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patientId;
    }

    public int getDoctorId(Login loginModel) {
        dBConnection = new DBConnection();
        int doctorId = 0;
        try {
            ResultSet rs = dBConnection.retrive("SELECT * FROM login "
                    + "WHERE "
                    + "username = '" + loginModel.getUsername() + "'");

            rs.next();
            int loginId = rs.getInt(1);

            ResultSet rs1 = dBConnection.retrive("SELECT * FROM medical_doctor_login "
                    + "WHERE "
                    + "login_id = '" + loginId + "'");
            rs1.next();
            doctorId = rs1.getInt(2);

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doctorId;
    }
}
