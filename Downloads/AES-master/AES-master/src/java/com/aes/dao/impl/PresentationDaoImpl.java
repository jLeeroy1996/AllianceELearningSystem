
package com.aes.dao.impl;

import com.aes.dao.PresentationDao;
import com.aes.model.Chapter;
import com.aes.model.Presentation;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 8:10:16 AM
 * 
 */

@Repository
public class PresentationDaoImpl implements PresentationDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(Presentation presentation) {
        session.getCurrentSession().save(presentation);
    }

    @Override
    public void update(Presentation presentation) {
        session.getCurrentSession().update(presentation);
    }

    @Override
    public void delete(Presentation presentation) {
        session.getCurrentSession().delete(presentation);
    }

    @Override
    public Presentation get(int id) {
        return (Presentation)session.getCurrentSession().get(Presentation.class, id);
    }

    @Override
    public List getAll() {
        return session.getCurrentSession().createCriteria(Presentation.class).list();
    }

    @Override
    public List getByChapterId(int id) {
        return session.getCurrentSession().createCriteria(Presentation.class)
                .add(Restrictions.eq("chapter", 
                        session.getCurrentSession().get(Chapter.class, id))).list();
    }

    @Override
    public boolean isExisting(String name) {
        Query query = session.getCurrentSession().createQuery("From Presentation where "
                + "name=:value");
        query.setParameter("value", name);
        return !query.list().isEmpty();        
    }

}
