package com.stratford.controller;

import com.stratford.dao.LoginDaoImpl;
import com.stratford.model.Login;
import com.stratford.service.LoginServiceImpl;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Akash Rajbanshi
 */
public class LoginController {

    LoginServiceImpl loginServiceImpl;

    public boolean authentication(Login loginModel) throws SQLException {
        loginServiceImpl = new LoginServiceImpl();
        return loginServiceImpl.authentication(loginModel);
    }
    
   public int getUserId(Login loginModel){
       loginServiceImpl = new LoginServiceImpl();
       return loginServiceImpl.getUserId(loginModel);
   }
   
   public int getDoctorId(Login loginModel){
       loginServiceImpl = new LoginServiceImpl();
       return loginServiceImpl.getDoctorId(loginModel);
   }
}
