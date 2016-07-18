
package com.aes.dao.impl;

import com.aes.dao.CourseCategoryDao;
import com.aes.model.CourseCategory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 9:47:41 PM
 * 
 */

@Repository
public class CourseCategoryDaoImpl implements CourseCategoryDao {

    @Autowired
    private SessionFactory session;
    
    @Override
    public void add(CourseCategory category) {
        session.getCurrentSession().save(category);
    }

    @Override
    public void update(CourseCategory category) {
        category.setDateEdited(null);
        session.getCurrentSession().update(category);
    }

    @Override
    public void delete(CourseCategory category) {
        session.getCurrentSession().delete(category);
    }

    @Override
    public CourseCategory get(int id) {
        return (CourseCategory) session.getCurrentSession().get(CourseCategory.class, id);
    }

    @Override
    public List getAll() {
         return session.getCurrentSession().createCriteria(CourseCategory.class)
                 .addOrder(Order.asc("category")).list();
    }

}
