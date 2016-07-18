
package com.aes.service.impl;

import com.aes.dao.UserDao;
import com.aes.misc.ORMUtil;
import com.aes.model.User;
import com.aes.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 8:59:09 AM
 * 
 */

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao dao;

    @Transactional
    @Override
    public void add (User user) {
        dao.add(user);
    }

    @Transactional
    @Override
    public void update (User user) {
        dao.update(user);
    }

    @Transactional
    @Override
    public void delete (User user) {
        dao.delete(user);
    }

    @Transactional
    @Override
    public User get (String username) {
        User user = dao.get(username);
        Hibernate.initialize(user.getBusinessUnit());
        Hibernate.initialize(user.getUserType());
        user.setBusinessUnit(ORMUtil.initializeAndUnproxy(user.getBusinessUnit()));
        user.setUserType(ORMUtil.initializeAndUnproxy(user.getUserType()));
        return user;
    }

    @Transactional
    @Override
    public List getAll () {
         List<User> list = dao.getAll();
         for (User user: list){
           Hibernate.initialize(user.getBusinessUnit());
           Hibernate.initialize(user.getUserType());
           user.setBusinessUnit(ORMUtil.initializeAndUnproxy(user.getBusinessUnit()));
           user.setUserType(ORMUtil.initializeAndUnproxy(user.getUserType()));
        }
        return list;
    }

    @Transactional
    @Override
    public boolean checkCredentials (User user) {
        return dao.checkCredentials(user);
    }

    @Transactional
    @Override
    public User getUserDetails (String username) {
        User user = dao.getUserDetails(username);
        Hibernate.initialize(user.getBusinessUnit());
        Hibernate.initialize(user.getCourses());
        Hibernate.initialize(user.getCoursesTakens());
        Hibernate.initialize(user.getExamScoreses());
        Hibernate.initialize(user.getUserType());
        return user;
    }

    @Transactional
    @Override
    public void resetPassword (User user) {
        dao.resetPassword(user);
    }

    @Transactional
    @Override    
    public List getExamScores (String username) {
        return dao.getExamScores(username);
    }

    @Transactional
    @Override
    public void setUserInactive(String username) {
        dao.setUserInactive(username);
    }

    @Transactional
    @Override
    public List searchByName(String name) {
        return dao.searchByName(name);
    }

    @Transactional
    @Override
    public List getAllTrainers() {
        return dao.getAllTrainers();
    }

    @Transactional
    @Override
    public List getAllWithBU() {
        List<User> list = dao.getAllWithBU();        
        for (User user : list){
            Hibernate.initialize(user.getBusinessUnit());
        }
        return list;
    }

    @Transactional
    @Override
    public List getAllUserDetails() {
        return dao.getAllUserDetails();
    }

    @Transactional
    @Override
    public List getAllAssignedCourses(String username) {
        return dao.getAllAssignedCourses(username);
    }

    @Transactional
    @Override
    public boolean isExisting(String username) {
        return dao.isExisting(username);
    }
        
}
