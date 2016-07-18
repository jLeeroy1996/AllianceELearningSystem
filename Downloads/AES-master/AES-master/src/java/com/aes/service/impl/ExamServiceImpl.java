
package com.aes.service.impl;

import com.aes.dao.ExamDao;
import com.aes.model.Exam;
import com.aes.service.ExamService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 4, 2015 10:47:42 PM
 * 
 */

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao dao;
    
    @Transactional
    @Override
    public void add(Exam exam) {
        dao.add(exam);
    }

    @Transactional
    @Override
    public void update(Exam exam) {
        dao.update(exam);
    }

    @Transactional
    @Override
    public void delete(Exam exam) {
        dao.delete(exam);
    }

    @Transactional
    @Override
    public Exam get(int id) {
        Exam exam =  dao.get(id);
        Hibernate.initialize(exam.getCourse());        
        return exam;
    }

    @Transactional
    @Override
    public List getAll() {
        List<Exam> exams = dao.getAll();
        for (Exam obj: exams){
            Hibernate.initialize(obj.getCourse());
        }
        return exams;
    }

    @Transactional
    @Override
    public List getAllByCourseId(int id) {
        List<Exam> exams = dao.getAllByCourseId(id);
        for (Exam obj: exams){
            Hibernate.initialize(obj.getCourse());
        }
        return exams;
    }

}
