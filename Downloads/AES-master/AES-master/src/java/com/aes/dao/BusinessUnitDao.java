/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao;

import com.aes.model.BusinessUnit;
import java.util.List;

/**
 *
 * @author Bryan
 */
public interface BusinessUnitDao {
    
    public void add (BusinessUnit bu);
    public void update (BusinessUnit bu);
    public void delete (BusinessUnit bu);
    public BusinessUnit get (int id);
    public List getAll ();  
    
}
