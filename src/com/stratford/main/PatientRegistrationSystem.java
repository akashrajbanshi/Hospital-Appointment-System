/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.main;

import com.stratford.view.LoginForm;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Akash Rajbanshi
 */
public class PatientRegistrationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //        try {
//            WebLookAndFeel.install();
//            UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
//
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(PatientRegistrationSystem.class.getName()).log(Level.SEVERE, null, ex);
//        }
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PatientRegistrationSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoginForm login = new LoginForm();
        login.setVisible(true);
    }

}
