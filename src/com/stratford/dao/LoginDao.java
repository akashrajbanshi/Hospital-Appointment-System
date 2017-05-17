/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import com.stratford.model.Login;
import java.sql.ResultSet;

/**
 *
 * @author Akash Rajbanshi
 */
public interface LoginDao {
    public ResultSet authentication(Login loginModel);
    public int getUserId(Login loginModel);
}
