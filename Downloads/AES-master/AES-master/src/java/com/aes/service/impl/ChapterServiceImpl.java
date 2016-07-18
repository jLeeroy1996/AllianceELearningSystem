/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.service.impl;

import com.aes.dao.ChapterDao;
import com.aes.misc.ORMUtil;
import com.aes.model.Chapter;
import com.aes.service.ChapterService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bryan
 */

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;
    
    @Override
    @Transactional
    public void add(Chapter chapter) {
        chapterDao.add(chapter);
    }

    @Override
    @Transactional
    public void update(Chapter chapter) {
        chapterDao.update(chapter);
    }

    @Override
    @Transactional
    public void delete(Chapter chapter) {
        chapterDao.delete(chapter);
    }

    @Override
    @Transactional
    public Chapter get(int id) {
        Chapter chapter =chapterDao.get(id);
        Hibernate.initialize(chapter.getPresentations());
        chapter.setPresentations(ORMUtil.initializeAndUnproxy(chapter.getPresentations()));
        return chapter;
    }

    @Override
    @Transactional
    public List getAll() {
        List<Chapter> chapters = chapterDao.getAll();
        for (Chapter chapter: chapters){
            Hibernate.initialize(chapter.getPresentations());
            chapter.setPresentations(ORMUtil.initializeAndUnproxy(chapter.getPresentations()));
        }
        return chapters;
    }

    @Override
    @Transactional
    public List getByCourseId(int id) {
        List<Chapter> chapters = chapterDao.getByCourseId(id);
        for (Chapter chapter : chapters) {       
            Hibernate.initialize(chapter.getPresentations());
            chapter.setPresentations(chapter.getPresentations());
        }
        return chapters; 
    }
    
}
