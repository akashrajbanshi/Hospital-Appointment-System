/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stratford.model;

/**
 *
 * @author Akash Rajbanshi
 */
public class Nurse extends User {
    

    public Nurse(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public Nurse(){
        
    }
    

    @Override
    public String toString() {
        return getName();
    }
    
  
}
