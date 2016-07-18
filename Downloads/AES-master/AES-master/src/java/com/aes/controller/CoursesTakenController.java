
package com.aes.controller;

import com.aes.dto.CourseDto;
import com.aes.model.CoursesTaken;
import com.aes.model.User;
import com.aes.service.ChapterService;
import com.aes.service.CourseService;
import com.aes.service.CoursesTakenService;
import com.aes.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
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
 * Date Created: Sep 20, 2015 5:11:46 PM
 * 
 */

@Controller
public class CoursesTakenController {

    @Autowired
    private CoursesTakenService coursesTakenService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ChapterService chapterService;
    
    @RequestMapping(value="assign/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject assignCourse (@RequestBody JSONObject data) throws IOException{        
        JSONObject json = new JSONObject();        
        try {
            int courseId = Integer.parseInt((String)data.get("courseId"));
            String username = (String)data.get("username");
            if (coursesTakenService.isAlreadyAssigned(username, courseId)){
                json.put("error", "User is already assigned.");
            }  else {
                try {
                    User loggedUser = (User)userService.get((String)data.get("admin"));
                    CoursesTaken taken = new CoursesTaken();
                    taken.setActive(true);
                    taken.setCourse(courseService.get(courseId));
                    taken.setUser(userService.get(username));
                    taken.setCreatedBy(loggedUser.getUserName());
                    taken.setEditedBy(loggedUser.getUserName());
                    coursesTakenService.add(taken);                    
                    json.put("error", "");
                } catch (Exception e) {
                    e.printStackTrace();
                    json.put("error", e.getMessage());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }      
    
    @RequestMapping(value="assign/batch/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject batchAssign(@RequestBody JSONObject data) throws IOException{        
        JSONObject json = new JSONObject();        
        ObjectMapper mapper = new ObjectMapper();
        try {
            int courseId = Integer.parseInt((String)data.get("course"));
            String admin = (String)data.get("admin");
            String[] array = mapper.readValue(data.get("trainees").toString(), String[].class);
            for (int i = 0; i < array.length; i++){
                if (!coursesTakenService.isAlreadyAssigned(array[i], courseId)){
                    CoursesTaken taken = new CoursesTaken();
                    taken.setActive(true);
                    taken.setCourse(courseService.get(courseId));
                    taken.setUser(userService.get(array[i]));
                    taken.setCreatedBy(admin);
                    taken.setEditedBy(admin);
                    coursesTakenService.add(taken);
                }                
            }            
            json.put("error", "");
        } catch (Exception e){
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    } 
    
    @RequestMapping(value="unassign/batch/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject batchUnassign(@RequestBody JSONObject data) throws IOException{        
        JSONObject json = new JSONObject();        
        ObjectMapper mapper = new ObjectMapper();
        try {
            int courseId = Integer.parseInt((String)data.get("course"));           
            String[] array = mapper.readValue(data.get("trainees").toString(), String[].class);
            for (int i = 0; i < array.length; i++){                
                coursesTakenService.unassign(array[i], courseId);
            }            
            json.put("error", "");
        } catch (Exception e){
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/assigned_course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteAssignedCourse (@RequestBody JSONObject data) {        
        JSONObject json = new JSONObject();       
        try {
            coursesTakenService.delete(coursesTakenService.get(Integer.parseInt((String)data.get("assignmentId"))));            
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/user/courses/{username}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAllUserCourses (@PathVariable(value="username") String username) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        JSONObject json = new JSONObject();        
        List<CoursesTaken> list = coursesTakenService.getAllUserCourses(username);
        String takenList = "";
        try{
            if (!list.isEmpty())
                takenList = mapper.writeValueAsString(list);            
            json.put("courses", takenList);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
       
    @RequestMapping(value="get/user/assigned_courses/{username}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAssignedCourses (@PathVariable(value="username") String username) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        JSONObject json = new JSONObject();        
        List<CoursesTaken> list = coursesTakenService.getCurrentCoursesByUser(username);
        String takenList = "";
        try{
            if (!list.isEmpty())
                takenList = mapper.writeValueAsString(list);            
            json.put("courses", takenList);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="get/detailed_courses/{username}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getDetailedAssignedCourses (@PathVariable(value="username") String username) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();                
        List<CoursesTaken> list = coursesTakenService.getCurrentCoursesByUser(username);
        List<CourseDto> dtoList = new ArrayList<CourseDto>();        
        String takenList = "";
        try{
            for (int i = 0; i < list.size(); i++) {
                CourseDto dto = new CourseDto();
                CoursesTaken obj = list.get(i);
                System.out.println("Course Id: " + obj.getCourse().getId());
                dto.convert(obj.getCourse());
                dto.setChapters(chapterService.getByCourseId(obj.getCourse().getId()));
                dtoList.add(dto);
            }
            if (!dtoList.isEmpty())
                takenList = mapper.writeValueAsString(dtoList);            
            json.put("courses", takenList);
            json.put("error", "");
            System.out.println(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="get/finished_courses/{username}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getFinishedCourses (@PathVariable(value="username") String username) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();        
        List<CoursesTaken> list = coursesTakenService.getPastCoursesByUser(username);
        List<CourseDto> dtoList = new ArrayList<CourseDto>();        
        String takenList = "";
        try{
            for (int i = 0; i < list.size(); i++) {
                CourseDto dto = new CourseDto();
                CoursesTaken obj = list.get(i);
                dto.convert(obj.getCourse());
                dto.setChapters(chapterService.getByCourseId(obj.getCourse().getId()));
                dtoList.add(dto);
            }
            if (!dtoList.isEmpty())
                takenList = mapper.writeValueAsString(dtoList);            
            json.put("courses", takenList);
            json.put("error", "");
            System.out.println(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="get/course/trainees/{courseId}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAssignedTrainees (@PathVariable(value="courseId") int id) {        
        JSONObject json = new JSONObject();        
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<User> list = coursesTakenService.getAssignedUsers(id);
            List<User> newList = new ArrayList<User>();
            for (User user: list){
                newList.add(userService.get(user.getUserName()));
            }
            json.put("trainees", mapper.writeValueAsString(newList));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
}
