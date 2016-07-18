/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao.impl;
import com.aes.dao.UserTypeDao;
import com.aes.model.UserType;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bryan
 */
@Repository
public class UserTypeDaoImpl implements UserTypeDao {
    
    @Autowired
    private SessionFactory session;

    @Override
    public void add(UserType userType) {
        session.getCurrentSession().save(userType);
    }

    @Override
    public void update(UserType userType) {
        session.getCurrentSession().update(userType);
    }

    @Override
    public void delete(UserType userType) {
        session.getCurrentSession().delete(userType);
    }

    @Override
    public UserType get(int id) {
        return (UserType)session.getCurrentSession().get(UserType.class, id);        
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(UserType.class).list();
    }
        
}
