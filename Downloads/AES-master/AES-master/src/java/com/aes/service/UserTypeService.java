/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service;

import com.aes.model.UserType;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface UserTypeService {
    
    public void add (UserType userType);
    public void update (UserType userType);
    public void delete (UserType userType);
    public UserType get (int id);
    public List getAll (); 
    
    
    
}
