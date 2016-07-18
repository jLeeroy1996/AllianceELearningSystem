
package com.aes.service.impl;

import com.aes.dao.ExamScoresDao;
import com.aes.model.ExamScores;
import com.aes.service.ExamScoresService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 6:48:19 AM
 * 
 */

@Service
public class ExamScoresServiceImpl implements ExamScoresService {

    @Autowired
    ExamScoresDao dao;
    
    @Transactional
    @Override
    public int add(ExamScores score) {
        return dao.add(score);
    }

    @Transactional
    @Override
    public void update(ExamScores score) {
        dao.update(score);
    }

    @Transactional
    @Override
    public void delete(ExamScores score) {
        dao.delete(score);
    }

    @Transactional
    @Override
    public ExamScores get(int id) {
        ExamScores score = dao.get(id);
        Hibernate.initialize(score.getUser());        
        return score;
    }

    @Transactional
    @Override
    public List getAll() {
        return dao.getAll();
    }

    @Transactional
    @Override
    public List getByUserId(String username) {
        List<ExamScores> list = dao.getByUserId(username);
        for (ExamScores obj: list){
            Hibernate.initialize(obj.getUser());
            Hibernate.initialize(obj.getExam());
            Hibernate.initialize(obj.getExam().getCourse());
        }        
        return list;
    }

    @Transactional
    @Override
    public List getByExamId(int id) {
        List<ExamScores> list = dao.getByExamId(id);
        for (ExamScores obj : list){
            Hibernate.initialize(obj.getUser());
            Hibernate.initialize(obj.getExam());
        }        
        return list;
    }

    @Transactional
    @Override
    public int getNumberOfLastTaken(int id, String username) {
        return dao.getNumberOfLastTaken(id, username);
    }
    
}
