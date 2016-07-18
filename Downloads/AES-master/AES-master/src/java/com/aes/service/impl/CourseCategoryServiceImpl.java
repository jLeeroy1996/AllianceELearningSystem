
package com.aes.service.impl;

import com.aes.dao.CourseCategoryDao;
import com.aes.model.CourseCategory;
import com.aes.service.CourseCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 9:52:59 PM
 * 
 */

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryDao dao;
    
    @Transactional
    @Override
    public void add(CourseCategory category) {        
        dao.add(category);
    }

    @Transactional
    @Override
    public void update(CourseCategory category) {
        dao.update(category);
    }

    @Transactional
    @Override
    public void delete(CourseCategory category) {
        dao.delete(category);
    }

    @Transactional
    @Override
    public CourseCategory get(int id) {
        return dao.get(id);
    }

    @Transactional
    @Override
    public List getAll() {
        return dao.getAll();
    }

}
