
package com.aes.service.impl;

import com.aes.dao.CoursesTakenDao;
import com.aes.misc.ORMUtil;
import com.aes.model.CoursesTaken;
import com.aes.model.User;
import com.aes.service.CoursesTakenService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 10:30:27 PM
 * 
 */

@Service
public class CoursesTakenServiceImpl implements CoursesTakenService {

    @Autowired
    CoursesTakenDao dao;
    
    @Transactional
    @Override
    public void add(CoursesTaken courseTaken) {        
        dao.add(courseTaken);
    }

    @Transactional
    @Override
    public void update(CoursesTaken courseTaken) {
        dao.update(courseTaken);
    }

    @Transactional
    @Override
    public void delete(CoursesTaken courseTaken) {
        dao.delete(courseTaken);
    }

    @Transactional
    @Override
    public CoursesTaken get(int id) {
        CoursesTaken taken = dao.get(id);
        if (taken!=null) {
            Hibernate.initialize(taken.getUser());
            taken.setUser(ORMUtil.initializeAndUnproxy(taken.getUser()));
            Hibernate.initialize(taken.getUser().getBusinessUnit());
            taken.getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getUser().getBusinessUnit()));
            Hibernate.initialize(taken.getUser().getUserType());
            taken.getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getUser().getUserType()));
            Hibernate.initialize(taken.getCourse());
            taken.setCourse(ORMUtil.initializeAndUnproxy(taken.getCourse()));
            Hibernate.initialize(taken.getCourse().getCourseCategory());
            taken.getCourse().setCourseCategory(taken.getCourse().getCourseCategory());
            Hibernate.initialize(taken.getCourse().getUser());
            taken.getCourse().setUser(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser()));
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getBusinessUnit()));
            Hibernate.initialize(taken.getCourse().getUser().getUserType());
            taken.getCourse().getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getUserType()));
        }
        return taken;
    }

    @Transactional
    @Override
    public List getAll() {
        return dao.getAll();
    }

    @Transactional
    @Override
    public void setInactive(int courseId, String username){
        dao.setInactive(courseId, username);
    }

    @Transactional
    @Override
    public List getCurrentCoursesByUser(String username) {
        List<CoursesTaken> list = dao.getCurrentCoursesByUser(username);
        for (CoursesTaken taken : list){
            Hibernate.initialize(taken.getUser());
            taken.setUser(ORMUtil.initializeAndUnproxy(taken.getUser()));                        
            Hibernate.initialize(taken.getUser().getBusinessUnit());
            Hibernate.initialize(taken.getUser().getUserType());            
            taken.getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getUser().getBusinessUnit()));
            taken.getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getUser().getUserType()));            
            Hibernate.initialize(taken.getCourse());            
            taken.setCourse(ORMUtil.initializeAndUnproxy(taken.getCourse()));                        
            Hibernate.initialize(taken.getCourse().getCourseCategory());
            taken.getCourse().setCourseCategory(ORMUtil.initializeAndUnproxy(taken.getCourse().getCourseCategory()));            
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().setUser(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser()));            
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getBusinessUnit()));
            Hibernate.initialize(taken.getCourse().getUser().getUserType());
            taken.getCourse().getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getUserType()));
        }
        return list;
    }

    @Transactional
    @Override
    public List getPastCoursesByUser(String username) {
        List<CoursesTaken> list = dao.getPastCoursesByUser(username);
        for (CoursesTaken taken : list){
            Hibernate.initialize(taken.getUser());
            Hibernate.initialize(taken.getCourse());
            taken.setCourse(ORMUtil.initializeAndUnproxy(taken.getCourse()));
            taken.setUser(ORMUtil.initializeAndUnproxy(taken.getUser()));
            Hibernate.initialize(taken.getCourse().getCourseCategory());
            taken.getCourse().setCourseCategory(ORMUtil.initializeAndUnproxy(taken.getCourse().getCourseCategory()));
            Hibernate.initialize(taken.getCourse().getUser());
            taken.getCourse().setUser(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser()));
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().getUser().setBusinessUnit(taken.getCourse().getUser().getBusinessUnit());
            Hibernate.initialize(taken.getCourse().getUser().getUserType());
            taken.getCourse().getUser().setUserType(taken.getCourse().getUser().getUserType());
        }
        return list;
    }

    @Transactional
    @Override
    public boolean isAlreadyAssigned(String username, int courseId) {
        return dao.isAlreadyAssigned(username, courseId);
    }

    @Transactional
    @Override
    public List getAllUserCourses(String username) {
        List<CoursesTaken> list =  dao.getAllUserCourses(username);
        for (CoursesTaken taken : list){
            Hibernate.initialize(taken.getUser());
            taken.setUser(ORMUtil.initializeAndUnproxy(taken.getUser()));                        
            Hibernate.initialize(taken.getUser().getBusinessUnit());
            Hibernate.initialize(taken.getUser().getUserType());            
            taken.getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getUser().getBusinessUnit()));
            taken.getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getUser().getUserType()));            
            Hibernate.initialize(taken.getCourse());            
            taken.setCourse(ORMUtil.initializeAndUnproxy(taken.getCourse()));                        
            Hibernate.initialize(taken.getCourse().getCourseCategory());
            taken.getCourse().setCourseCategory(ORMUtil.initializeAndUnproxy(taken.getCourse().getCourseCategory()));            
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().setUser(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser()));            
            Hibernate.initialize(taken.getCourse().getUser().getBusinessUnit());
            taken.getCourse().getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getBusinessUnit()));
            Hibernate.initialize(taken.getCourse().getUser().getUserType());
            taken.getCourse().getUser().setUserType(ORMUtil.initializeAndUnproxy(taken.getCourse().getUser().getUserType()));
        }
        return list;
    }

    @Transactional
    @Override
    public List getAssignedUsers(int courseId) {
        List<CoursesTaken> list = dao.getAssignedUsers(courseId);
        List<User> newList = new ArrayList<User>();
        for (CoursesTaken coursesTaken: list){
            Hibernate.initialize(coursesTaken.getUser());
            newList.add(coursesTaken.getUser());
        }
        return newList;
    }

    @Transactional
    @Override
    public void unassign(String username, int courseId) {
        dao.unassign(username, courseId);
    }

}
