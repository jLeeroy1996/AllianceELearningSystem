package com.aes.service.impl;

import com.aes.dao.CourseDao;
import com.aes.misc.ORMUtil;
import com.aes.model.Course;
import com.aes.service.CourseService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Created on: Sep 4, 2015 9:10:47 PM
 *
 */

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao dao;
    
    @Transactional
    @Override
    public void add(Course course) {
        dao.add(course);
    }

    @Transactional
    @Override
    public void update(Course course) {
        dao.update(course);
    }

    @Transactional
    @Override
    public void delete(Course course) {
        dao.delete(course);
    }

    @Transactional
    @Override
    public Course get(int id) {
        Course course = dao.get(id);
        Hibernate.initialize(course.getUser());
        Hibernate.initialize(course.getCourseCategory());
        course.setUser(ORMUtil.initializeAndUnproxy(course.getUser()));
        course.setCourseCategory(ORMUtil.initializeAndUnproxy(course.getCourseCategory()));
        Hibernate.initialize(course.getUser().getBusinessUnit());
        Hibernate.initialize(course.getUser().getUserType());
        course.getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(course.getUser().getBusinessUnit()));
        course.getUser().setUserType(ORMUtil.initializeAndUnproxy(course.getUser().getUserType()));
        return course;
    }

    @Transactional
    @Override
    public List getAll() {
        List<Course> list = dao.getAll();
        for (Course course: list){
            Hibernate.initialize(course.getUser());
            Hibernate.initialize(course.getCourseCategory());
            course.setUser(ORMUtil.initializeAndUnproxy(course.getUser()));
            Hibernate.initialize(course.getUser().getBusinessUnit());
            Hibernate.initialize(course.getUser().getUserType());
            course.getUser().setBusinessUnit(ORMUtil.initializeAndUnproxy(course.getUser().getBusinessUnit()));
            course.getUser().setUserType(ORMUtil.initializeAndUnproxy(course.getUser().getUserType()));            
            course.setCourseCategory(ORMUtil.initializeAndUnproxy(course.getCourseCategory()));
        }
        return list;
    }
    
}
