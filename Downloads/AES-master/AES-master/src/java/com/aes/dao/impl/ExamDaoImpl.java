
package com.aes.dao.impl;

import com.aes.dao.ExamDao;
import com.aes.model.Course;
import com.aes.model.Exam;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 10:43:42 PM
 * 
 */

@Repository
public class ExamDaoImpl implements ExamDao {

    @Autowired
    SessionFactory session;
    
    @Override
    public void add(Exam exam) {
        session.getCurrentSession().save(exam);
    }

    @Override
    public void update(Exam exam) {
        exam.setDateEdited(null);
        session.getCurrentSession().update(exam);
    }

    @Override
    public void delete(Exam exam) {
        session.getCurrentSession().delete(exam);
    }

    @Override
    public Exam get(int id) {
        return (Exam)session.getCurrentSession().get(Exam.class, id);       
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(Exam.class).list();        
    }

    @Override
    public List getAllByCourseId(int id) {
        return session.getCurrentSession().createCriteria(Exam.class)
                .add(Restrictions.eq("course", 
                        session.getCurrentSession().get(Course.class, id))).list();        
    }

}
