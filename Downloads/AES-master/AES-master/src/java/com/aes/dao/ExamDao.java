/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao;

import com.aes.model.Exam;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface ExamDao {
    
    public void add (Exam exam);
    public void update (Exam exam);
    public void delete (Exam exam);
    public Exam get (int id);
    public List getAll ();  
    public List getAllByCourseId(int id);
    
}
