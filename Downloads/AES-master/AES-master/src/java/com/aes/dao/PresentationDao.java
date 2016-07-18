/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao;

import com.aes.model.Presentation;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface PresentationDao {
    
    public void add (Presentation presentation);
    public void update (Presentation presentation);
    public void delete (Presentation presentation);
    public Presentation get (int id);
    public List getAll ();  
    public List getByChapterId (int id);  
    public boolean isExisting (String name);
    
}
