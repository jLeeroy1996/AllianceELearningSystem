
package com.aes.dao.impl;

import com.aes.dao.CoursesTakenDao;
import com.aes.misc.ORMUtil;
import com.aes.model.Course;
import com.aes.model.CoursesTaken;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 10:24:59 PM
 * 
 */

@Repository
public class CoursesTakenDaoImpl implements CoursesTakenDao{

    @Autowired
    private SessionFactory session;
    
   
    @Override
    public void add(CoursesTaken courseTaken) {
        session.getCurrentSession().save(courseTaken);
    }

    @Override
    public void update(CoursesTaken courseTaken) {
        courseTaken.setDateEdited(null);
        session.getCurrentSession().update(courseTaken);
    }

    @Override
    public void delete(CoursesTaken courseTaken) {
        session.getCurrentSession().delete(courseTaken);
    }

    @Override
    public CoursesTaken get(int id) {
        return (CoursesTaken) session.getCurrentSession().get(CoursesTaken.class, id);        
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(CoursesTaken.class).list();
    }

    @Override
    // Logical delete
    public void setInactive(int courseId, String username) {        
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username and course.id=:courseId");
        query.setParameter("username", username);
        query.setParameter("courseId", courseId);        
        List<CoursesTaken> courses = query.list();
        CoursesTaken obj = courses.get(0);
        obj.setActive(false);
        this.update(obj);
    }    

    @Override
    public List getCurrentCoursesByUser(String username) {        
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username and active=true");
        query.setParameter("username", username);
        return query.list();                        
    }
    
    @Override
    public List getAllUserCourses (String username) {        
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username");
        query.setParameter("username", username);
        return query.list();                        
    }

    @Override
    public List getPastCoursesByUser(String username) {
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username and active=false");
        query.setParameter("username", username);
        return query.list();        
    }

    @Override
    public boolean isAlreadyAssigned(String username, int courseId) {
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username and course.id=:courseId");
        query.setParameter("username", username);
        query.setParameter("courseId", courseId);        
        return !query.list().isEmpty();
    }

    @Override
    public List getAssignedUsers(int courseId) {
        return session.getCurrentSession().createCriteria(CoursesTaken.class)
               .add(Restrictions.eq("course", session.getCurrentSession().get(Course.class, courseId))).list();           
    }
    
    @Override
    public void unassign (String username, int courseId) {
        Query query = session.getCurrentSession().createQuery("From CoursesTaken where user.userName=:username and course.id=:courseId");
        query.setParameter("username", username);
        query.setParameter("courseId", courseId);
        CoursesTaken taken = (CoursesTaken)query.uniqueResult();
        session.getCurrentSession().delete(taken);
    }
}
