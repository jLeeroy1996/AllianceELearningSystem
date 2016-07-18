package com.aes.dao.impl;

import com.aes.dao.CourseDao;
import com.aes.model.Course;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Created on: Sep 4, 2015 9:07:33 PM
 *
 */

@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(Course course) {
        session.getCurrentSession().save(course);
    }

    @Override
    public void update(Course course) {
        course.setDateEdited(null);
        session.getCurrentSession().update(course);
    }

    @Override
    public void delete(Course course) {
        session.getCurrentSession().delete(course);
    }

    @Override
    public Course get(int id) {
        return (Course)session.getCurrentSession().get(Course.class, id);        
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(Course.class).addOrder(Order.desc("dateEdited")).list();        
    }

}
