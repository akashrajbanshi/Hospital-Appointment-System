/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.dao;

import java.sql.*;

import javax.swing.JOptionPane;

//this class handles all database connections
public class DBConnection {

    Connection connect = null;
    Statement statement = null;
    ResultSet rs = null;

    /**
     * Creates a new instance of ConnectToDB
     */
    //initialize the variables in this constructor
    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/patient_registration_system", "root", "");

            statement = connect.createStatement();
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, cnfe.getMessage(), "ERROR",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "ERROR",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }

    //method to update database; use this for all insert, update statements
    public void update(String query) {
        
        try {

          
            statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "ERROR",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    //method to retrieve records from db;use this for all select statements
    public  ResultSet retrive(String query) {
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "ERROR",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        return rs;

    }

    //Close the database connection
    public void closeConnection() {
        try {
            statement.close();
            connect.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage(), "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

}
