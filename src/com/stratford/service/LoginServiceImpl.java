/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.dao.LoginDaoImpl;
import com.stratford.model.Login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akash Rajbanshi
 */
public class LoginServiceImpl implements LoginService {

    LoginDaoImpl loginDao;

    @Override
    public boolean authentication(Login loginModel) {
        loginDao = new LoginDaoImpl();

        ResultSet rs = loginDao.authentication(loginModel);
        int count = 0;
        try {
            while (rs.next()) {
                count = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count != 0;
    }

    @Override
    public int getUserId(Login loginModel) {
        loginDao = new LoginDaoImpl();
        return loginDao.getUserId(loginModel);
    }

    public int getDoctorId(Login loginModel) {
       loginDao = new LoginDaoImpl();
       return loginDao.getDoctorId(loginModel);
    }

}
