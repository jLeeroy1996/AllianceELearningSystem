/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.dao.impl;

import com.aes.dao.ChapterDao;
import com.aes.model.Chapter;
import com.aes.model.Course;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bryan
 */

@Repository
public class ChapterDaoImpl implements ChapterDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(Chapter chapter) {
        session.getCurrentSession().save(chapter);
    }

    @Override
    public void update(Chapter chapter) {
        chapter.setDateEdited(null);
        session.getCurrentSession().update(chapter);
    }

    @Override
    public void delete(Chapter chapter) {
        session.getCurrentSession().delete(chapter);
    }

    @Override
    public Chapter get(int id) {
        return (Chapter)session.getCurrentSession().get(Chapter.class, id);        
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(Chapter.class).list();        
    }

    @Override
    public List getByCourseId(int id) {
        return session.getCurrentSession().createCriteria(Chapter.class)
                .add(Restrictions.eq("course", 
                        session.getCurrentSession().get(Course.class, id)))
                .addOrder(Order.asc("number")).list();                       
    }
    
}
