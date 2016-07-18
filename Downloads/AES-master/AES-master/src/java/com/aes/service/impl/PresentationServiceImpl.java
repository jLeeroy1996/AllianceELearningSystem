
package com.aes.service.impl;

import com.aes.dao.PresentationDao;
import com.aes.model.Presentation;
import com.aes.service.PresentationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 8:13:53 AM
 * 
 */

@Service
public class PresentationServiceImpl implements PresentationService {

    @Autowired
    PresentationDao dao;
    
    @Transactional
    @Override
    public void add(Presentation presentation) {
        dao.add(presentation);
    }

    @Transactional
    @Override
    public void update(Presentation presentation) {
        dao.update(presentation);
    }

    @Transactional
    @Override
    public void delete(Presentation presentation) {
        dao.delete(presentation);
    }

    @Transactional
    @Override
    public Presentation get(int id) {
        return dao.get(id);
    }

    @Transactional
    @Override
    public List getAll() {
        return dao.getAll();
    }

    @Transactional
    @Override
    public List getByChapterId(int id) {
        return dao.getByChapterId(id);
    }

    @Transactional
    @Override
    public boolean isExisting(String name) {
        return dao.isExisting(name);
    }

}
