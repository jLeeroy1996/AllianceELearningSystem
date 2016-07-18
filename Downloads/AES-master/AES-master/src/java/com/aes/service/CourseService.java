package com.aes.service;


import com.aes.model.Course;
import java.util.List;


/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Created on: Sep 4, 2015 9:18:41 PM
 * 
 */
public interface CourseService {
    
    public void add (Course course);
    public void update (Course course);
    public void delete (Course course);
    public Course get (int id);
    public List getAll ();  

}
