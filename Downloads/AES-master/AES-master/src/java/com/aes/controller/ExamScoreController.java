
package com.aes.controller;

import com.aes.model.Exam;
import com.aes.model.ExamScores;
import com.aes.model.User;
import com.aes.service.CourseService;
import com.aes.service.CoursesTakenService;
import com.aes.service.ExamScoresService;
import com.aes.service.ExamService;
import com.aes.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Hibernate;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 26, 2015 10:40:40 AM
 * 
 */

@Controller
public class ExamScoreController {
    
    @Autowired
    private ExamScoresService examScoreService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ExamService examService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private CoursesTakenService coursesTakenService;
    
    @RequestMapping(value="get/user/scores/{username}" ,method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getScoresByUser (@PathVariable(value="username") String username) {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ExamScores> list = examScoreService.getByUserId(username);
            for (ExamScores obj : list) {
                obj.setUser(userService.get(obj.getUser().getUserName()));
                obj.setExam(examService.get(obj.getExam().getId()));
                obj.getExam().setCourse(courseService.get(obj.getExam().getCourse().getId()));
            }
            json.put("scores", mapper.writeValueAsString(list));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/exam/scores/{examId}" ,method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getScoresByExamId (@PathVariable(value="examId") int id) {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ExamScores> list = examScoreService.getByExamId(id);
            for (ExamScores obj : list) {
                obj.setUser(userService.get(obj.getUser().getUserName()));
                obj.setExam(examService.get(obj.getExam().getId()));
                obj.getExam().setCourse(courseService.get(obj.getExam().getCourse().getId()));
            }
            json.put("scores", mapper.writeValueAsString(list));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="post/score", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject recordExam (@RequestBody JSONObject data, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            User user = (User)request.getSession().getAttribute("loggedUser");
            ExamScores score  = new ExamScores();
            score.setUser(user);
            score.setExam(examService.get(Integer.parseInt((String)data.get("examId"))));
            score.setScore(Float.parseFloat((String)data.get("score")));
            score.setMaxScore(Float.parseFloat((String)data.get("maxScore")));
            score.setPassingPercentage(Integer.parseInt((String)data.get("passingRate")));
            score.setTimesTaken(examScoreService.getNumberOfLastTaken(Integer.parseInt((String)data.get("examId")), user.getUserName()) + 1);                        
            int scoreId = examScoreService.add(score);
            Hibernate.initialize(score.getExam().getCourse());
            List<Exam> exams = examService.getAllByCourseId(score.getExam().getCourse().getId());
            boolean flag = true;
            for (Exam exam: exams){
                if (examScoreService.getNumberOfLastTaken(exam.getId(), user.getUserName()) == 0)
                    flag = false;
            }
            if (flag == true)                
                coursesTakenService.setInactive(score.getExam().getCourse().getId(), user.getUserName());
            json.put("error", "");
            json.put("scoreId", scoreId);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="put/score", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateScore (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject();
        try {
            ExamScores score = examScoreService.get(Integer.parseInt((String)data.get("scoreId")));
            score.setScore(Integer.parseInt((String)data.get("score")));
            examScoreService.update(score);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/score", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteScore (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject();
        try {
            examScoreService.delete(examScoreService.get(Integer.parseInt((String)data.get("id"))));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
}
