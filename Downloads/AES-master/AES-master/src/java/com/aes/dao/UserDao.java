/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao;

import com.aes.model.User;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface UserDao {
        
    public void add (User user);
    public void update (User user);
    public void delete (User user);
    public User get (String username);
    public User getUserDetails (String username);
    public boolean checkCredentials (User user);
    public List getAll ();
    public void resetPassword (User user);
    public List getExamScores (String username);
    public void setUserInactive(String username);
    public List searchByName(String name);
    public List getAllTrainers();
    public List getAllWithBU ();
    public List getAllUserDetails();
    public List getAllAssignedCourses(String username);
    public boolean isExisting(String username);
    
}
