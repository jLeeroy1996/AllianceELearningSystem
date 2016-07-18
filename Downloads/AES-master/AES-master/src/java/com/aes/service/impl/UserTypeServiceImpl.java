/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service.impl;

import com.aes.dao.UserTypeDao;
import com.aes.model.UserType;
import com.aes.service.UserTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bryan
 */
@Service
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserTypeDao typeDao;
    
    @Transactional
    @Override
    public List getAll() {
        return typeDao.getAll();
    }
    
    @Transactional
    @Override
    public void add(UserType userType){
        typeDao.add(userType);
    }
    
    @Transactional
    @Override
    public void update(UserType userType){
        typeDao.update(userType);
    }
    
    @Transactional
    @Override
    public void delete(UserType userType){
        typeDao.delete(userType);
    }

    @Transactional
    @Override
    public UserType get(int id) {
        return typeDao.get(id);
    }
       
}
