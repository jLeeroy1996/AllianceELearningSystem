
package com.aes.dao.impl;

import com.aes.dao.UserDao;
import com.aes.misc.DatabaseDefaults;
import com.aes.misc.ORMUtil;
import com.aes.misc.PasswordUtil;
import com.aes.model.Course;
import com.aes.model.CoursesTaken;
import com.aes.model.ExamScores;
import com.aes.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 8:55:33 AM
 * 
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(User user) {
        user.setPassword(PasswordUtil.getSecurePassword(DatabaseDefaults.DEFAULT_PASSWORD));
        session.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        user.setDateEdited(null);
        session.getCurrentSession().update(user);
    }

    @Override
    public void delete(User user) {
        session.getCurrentSession().delete(user);
    }

    @Override
    public User get(String username) {
        return (User)session.getCurrentSession().get(User.class, username);        
    }

    @Override
    public List getAllWithBU() {
        return session.getCurrentSession().createCriteria(User.class).list();
    }
    
    @Override
    public List getAll() {
       return session.getCurrentSession().createCriteria(User.class)
               .addOrder(Order.asc("name")).list();                
    }    

    @Override
    public boolean checkCredentials(User user) {
        User temp = (User)session.getCurrentSession().get(User.class, user.getUserName());
        if (temp != null){
            if (temp.getPassword().equals(PasswordUtil.getSecurePassword(user.getPassword()))){
                return true;
            }
        }
        return false;    
    }

    @Override
    // Initializes all user dependencies
    public User getUserDetails(String username) {
        return (User)session.getCurrentSession().get(User.class, username);        
    }

    @Override
    public void resetPassword(User user) {
        user.setPassword(PasswordUtil.getSecurePassword(DatabaseDefaults.DEFAULT_PASSWORD));
        session.getCurrentSession().update(user);
    }
    
    @Override
    public List getExamScores (String username) {
        User user = this.get(username);
        Hibernate.initialize(user.getExamScoreses());
        List<ExamScores> list = new ArrayList();
        list.addAll(user.getExamScoreses());
        for (ExamScores obj : list){
            Hibernate.initialize(obj.getExam());
        }
        return list;
    }

    @Override
    public void setUserInactive(String username) {
        User user = (User)session.getCurrentSession().get(User.class, username);
        user.setActive(false);
        this.update(user);
    }

    @Override
    public List searchByName(String name) {
        Query query = session.getCurrentSession().createQuery("From User where "
                + "name LIKE :name2 or user_name LIKE :name3 order by name");
        query.setParameter("name2", "%" + name + "%");
        query.setParameter("name3", "%" + name + "%");
        List<User> list =  query.list();    
        for (User user: list){
            Hibernate.initialize(user.getBusinessUnit());
            Hibernate.initialize(user.getUserType());
            user.setBusinessUnit(ORMUtil.initializeAndUnproxy(user.getBusinessUnit()));
            user.setUserType(ORMUtil.initializeAndUnproxy(user.getUserType()));
        }
        return list;
    }

    @Override
    public List getAllTrainers() {
        Query query = session.getCurrentSession().createQuery("From User where "
                + "user_type_id=:value or user_type_id=:value2");
        query.setParameter("value", DatabaseDefaults.TRAINER);
        query.setParameter("value2", DatabaseDefaults.ADMIN);
        List<User> list =  query.list();    
        for (User user: list){
            Hibernate.initialize(user.getBusinessUnit());
            Hibernate.initialize(user.getUserType());
            user.setBusinessUnit(ORMUtil.initializeAndUnproxy(user.getBusinessUnit()));
            user.setUserType(ORMUtil.initializeAndUnproxy(user.getUserType()));
        }
        return list;
    }

    @Override
    public List getAllUserDetails() {
        List<User> list = this.getAll(); 
        for (User user: list){
            Hibernate.initialize(user.getBusinessUnit());
            Hibernate.initialize(user.getUserType());
            user.setBusinessUnit(ORMUtil.initializeAndUnproxy(user.getBusinessUnit()));
            user.setUserType(ORMUtil.initializeAndUnproxy(user.getUserType()));
        }
        return list;
    }

    @Override
    public List getAllAssignedCourses(String username) {
        User user = this.get(username);
        Hibernate.initialize(user.getCoursesTakens());
        List<CoursesTaken> list = new ArrayList();
        list.addAll(user.getCoursesTakens());
        List<Course> courses = new ArrayList();
        for (CoursesTaken taken : list) {
            Hibernate.initialize(taken.getCourse());
            courses.add(taken.getCourse());
        }
        return courses;
    }

    @Override
    public boolean isExisting(String username) {
        return (session.getCurrentSession().get(User.class, username)) != null;
    }
    
}
