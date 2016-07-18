/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service;

import com.aes.model.ExamScores;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface ExamScoresService {
    
    public int add (ExamScores score);
    public void update (ExamScores score);
    public void delete (ExamScores score);
    public ExamScores get (int id);
    public List getAll ();  
    public List getByUserId (String username);
    public List getByExamId (int id);
    public int getNumberOfLastTaken(int id, String username);
    
}
