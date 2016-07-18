/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service;

import com.aes.model.CoursesTaken;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface CoursesTakenService {
    
    public void add (CoursesTaken courseTaken);
    public void update (CoursesTaken courseTaken);
    public void delete (CoursesTaken courseTaken);
    public void setInactive(int courseId, String username);
    public CoursesTaken get (int id);
    public List getAllUserCourses (String username);
    public List getCurrentCoursesByUser(String username);
    public List getPastCoursesByUser(String username);
    public List getAll ();
    public boolean isAlreadyAssigned(String username, int courseId);
    public List getAssignedUsers(int courseId);
    public void unassign (String username, int courseId);
}
