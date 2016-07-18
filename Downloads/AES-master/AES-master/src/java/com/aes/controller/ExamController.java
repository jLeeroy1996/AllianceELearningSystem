
package com.aes.controller;

import com.aes.model.CoursesTaken;
import com.aes.model.Exam;
import com.aes.model.User;
import com.aes.service.CourseService;
import com.aes.service.CoursesTakenService;
import com.aes.service.ExamScoresService;
import com.aes.service.ExamService;
import com.aes.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
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
 * Date Created: Sep 26, 2015 11:24:17 AM
 * 
 */

@Controller
public class ExamController {
    
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
    
    @RequestMapping(value="get/exam/upcoming/{username}" ,method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getUpcomingExams (@PathVariable(value="username") String username) {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();        
        try {
            List<CoursesTaken> coursesTaken = coursesTakenService.getCurrentCoursesByUser(username);            
            List finalExamList = new ArrayList<Exam>();
            for (CoursesTaken course: coursesTaken){
                List<Exam> exams = examService.getAllByCourseId(course.getCourse().getId());
                for (Exam exam : exams){
                    if (examScoreService.getNumberOfLastTaken(exam.getId(), username) == 0){
                        exam.setCourse(courseService.get(exam.getCourse().getId()));
                        finalExamList.add(exam);
                    }                    
                }
            }
            json.put("exams", mapper.writeValueAsString(finalExamList));
            json.put("error", "");           
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/course/exams/{courseId}" , method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getCourseExams (@PathVariable(value="courseId")int courseId) {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();        
        try {
            List<Exam> exams = examService.getAllByCourseId(courseId);
            for (Exam exam: exams){
                exam.setCourse(courseService.get(exam.getCourse().getId()));
            }            
            json.put("exams", mapper.writeValueAsString(exams));
            json.put("error", "");           
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/exam" , method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteExam (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject();
        try {
            examService.delete(examService.get(Integer.parseInt((String)data.get("id"))));
            json.put("error", "");           
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/exam/{examId}" , method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getExam (@PathVariable(value="examId") int id) {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper ();
        try {
            Exam exam = examService.get(id);
            exam.setCourse(courseService.get(exam.getCourse().getId()));
            json.put("exam", mapper.writeValueAsString(exam.getExam()));
            json.put("passingRate", exam.getPassingPercentage());
            json.put("examId", exam.getId());
            json.put("error", "");            
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="post/exam" , method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addExam (@RequestBody JSONObject data, HttpServletRequest request) {
        JSONObject json = new JSONObject();        
        try {
            User user = (User)request.getSession().getAttribute("loggedUser");
            Exam exam = new Exam();
            exam.setCourse(courseService.get((Integer.parseInt((String)data.get("course")))));
            exam.setCreatedBy(user.getUserName());
            exam.setEditedBy(user.getUserName());
            exam.setDescription((String)data.get("description"));
            exam.setExam((String)data.get("exam"));
            exam.setTitle((String)data.get("title"));
            exam.setPassingPercentage(Integer.parseInt((String)data.get("passingRate")));
            examService.add(exam);            
            json.put("error", "");            
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="put/exam" , method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateExam (@RequestBody JSONObject data, HttpServletRequest request) {
        JSONObject json = new JSONObject();        
        try {
            User user = (User)request.getSession().getAttribute("loggedUser");
            Exam exam = examService.get(Integer.valueOf((String)data.get("id")));
            exam.setCourse(courseService.get((Integer.parseInt((String)data.get("course")))));
            exam.setEditedBy(user.getUserName());
            exam.setDateEdited(null);
            exam.setDescription((String)data.get("description"));
            exam.setExam((String)data.get("exam"));
            exam.setTitle((String)data.get("title"));
            exam.setPassingPercentage(Integer.parseInt((String)data.get("passingRate")));
            examService.update(exam);            
            json.put("error", "");            
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }
        return json;
    }

}
