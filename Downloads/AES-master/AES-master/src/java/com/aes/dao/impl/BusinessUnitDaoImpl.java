/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao.impl;

import com.aes.dao.BusinessUnitDao;
import com.aes.model.BusinessUnit;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bryan
 */
@Repository
public class BusinessUnitDaoImpl implements BusinessUnitDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(BusinessUnit bu) {
        session.getCurrentSession().save(bu);
    }

    @Override
    public void update(BusinessUnit bu) {
        bu.setDateEdited(null);
        session.getCurrentSession().update(bu);
    }

    @Override
    public void delete(BusinessUnit bu) {
        session.getCurrentSession().delete(bu);
    }

    @Override
    public BusinessUnit get(int id) {
        BusinessUnit bu = (BusinessUnit)session.getCurrentSession().get(BusinessUnit.class, id);
        return bu;
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(BusinessUnit.class).list();        
    }
    
}
