
package com.aes.controller;

import com.aes.model.Course;
import com.aes.service.CourseCategoryService;
import com.aes.service.CourseService;
import com.aes.service.CoursesTakenService;
import com.aes.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
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
 * Date Created: Sep 20, 2015 5:05:38 PM
 * 
 */

@Controller
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseCategoryService categoryService;
    
    @Autowired
    private CoursesTakenService coursesTakenService;
    
    @RequestMapping(value="post/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addCourse (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject ();
        try {
            Course course = new Course();
            course.setCourseCategory(categoryService.get(Integer.parseInt((String)data.get("categoryId"))));
            course.setCourseTitle((String)data.get("title"));
            course.setDescription((String)data.get("description"));
            course.setUser(userService.get((String)data.get("trainer")));
            course.setCreatedBy((String)data.get("admin"));
            course.setEditedBy((String)data.get("admin"));            
            courseService.add(course);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteCourse(@RequestBody JSONObject data){
        
        JSONObject json = new JSONObject();
        try {
            courseService.delete(courseService.get(Integer.parseInt((String)data.get("id"))));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;                
    }
    
    @RequestMapping(value="get/course/{courseId}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getCourse (@PathVariable (value="courseId") int courseId){
        JSONObject json = new JSONObject();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {            
            json.put("course", ow.writeValueAsString(courseService.get(courseId)));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="get/courses", method=RequestMethod.GET) 
    @ResponseBody
    private JSONObject  getCourses () {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();
        try{
            json.put("courses", mapper.writeValueAsString(courseService.getAll()));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="put/course", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateCourse (@RequestBody JSONObject data) {        
        JSONObject json = new JSONObject();        
        try {
            Course course = courseService.get(Integer.parseInt((String)data.get("id")));
            course.setEditedBy((String)data.get("admin"));
            course.setDateEdited(null);
            course.setCourseCategory(categoryService.get(Integer.parseInt((String)data.get("category"))));
            course.setUser(userService.get((String)data.get("trainer")));
            course.setCourseTitle((String)data.get("title"));
            course.setDescription((String)data.get("description"));
            courseService.update(course);
            json.put("error", "");            
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;        
    } 
    
}
