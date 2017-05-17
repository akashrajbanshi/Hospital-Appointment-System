/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.service;

import com.stratford.model.Login;

/**
 *
 * @author Akash Rajbanshi
 */
public interface LoginService {
    public boolean authentication(Login loginModel);
    public int getUserId(Login loginModel);
}
