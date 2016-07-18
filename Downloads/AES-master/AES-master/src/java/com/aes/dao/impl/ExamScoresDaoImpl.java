
package com.aes.dao.impl;

import com.aes.dao.ExamScoresDao;
import com.aes.misc.ORMUtil;
import com.aes.model.Exam;
import com.aes.model.ExamScores;
import com.aes.model.User;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 6:45:47 AM
 * 
 */

@Repository
public class ExamScoresDaoImpl implements ExamScoresDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public int add(ExamScores score) {
        return (int) session.getCurrentSession().save(score);
    }

    @Override
    public void update(ExamScores score) {
        session.getCurrentSession().update(score);        
    }

    @Override
    public void delete(ExamScores score) {
        session.getCurrentSession().delete(score);
    }

    @Override
    public ExamScores get(int id) {
       return (ExamScores)session.getCurrentSession().get(ExamScores.class, id);        
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(ExamScores.class).list();
    }

    @Override
    public List getByUserId(String username) {        
        return session.getCurrentSession().createCriteria(ExamScores.class)
                .add(Restrictions.eq("user", 
                        session.getCurrentSession().get(User.class, username))).addOrder(Order.desc("dateTaken")).list();                     
    }    

    @Override
    public List getByExamId(int id) {
        Query query = session.getCurrentSession().createQuery("From ExamScores where exam.id=:examId order by user.name, timesTaken");
        query.setParameter("examId", id);
        return query.list();        
    }

    @Override
    public int getNumberOfLastTaken(int id, String username) {
        Query query = session.getCurrentSession().createQuery("From ExamScores where user.userName=:username and exam.id=:examId"
                + " order by timesTaken DESC");
        query.setParameter("username", username);
        query.setParameter("examId", id);
        List<ExamScores> list = query.list();
        if (list.isEmpty()){
            return 0;
        } else {
            return list.get(0).getTimesTaken();
        }
    }

}
