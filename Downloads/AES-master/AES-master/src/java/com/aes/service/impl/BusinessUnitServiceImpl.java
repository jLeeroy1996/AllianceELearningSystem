/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service.impl;

import com.aes.dao.BusinessUnitDao;
import com.aes.dao.impl.BusinessUnitDaoImpl;
import com.aes.model.BusinessUnit;
import com.aes.service.BusinessUnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bryan
 */
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService{
    
    @Autowired
    private BusinessUnitDao buDao;
    
    @Transactional   
    @Override
    public void add(BusinessUnit bu) {
        buDao.add(bu);
    }

    @Transactional
    @Override
    public void update(BusinessUnit bu) {
        buDao.update(bu);
    }

    @Transactional
    @Override
    public void delete(BusinessUnit bu) {
        buDao.delete(bu);
    }

    @Transactional
    @Override
    public BusinessUnit get(int id) {
        return buDao.get(id);
    }

    @Transactional
    @Override
    public List getAll() {
        return buDao.getAll();
    }
        
}
