/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service;

import com.aes.model.Chapter;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface ChapterService {
    
    public void add (Chapter chapter);
    public void update (Chapter chapter);
    public void delete (Chapter chapter);
    public Chapter get (int id);
    public List getAll ();  
    public List getByCourseId(int id);
    
    
}
