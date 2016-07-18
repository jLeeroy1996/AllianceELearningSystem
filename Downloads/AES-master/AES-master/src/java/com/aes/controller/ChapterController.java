
package com.aes.controller;

import com.aes.model.Chapter;
import com.aes.service.ChapterService;
import com.aes.service.CourseService;
import com.aes.service.UserService;
import java.io.IOException;
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
 * Date Created: Sep 20, 2015 5:25:48 PM
 * 
 */

@Controller
public class ChapterController {
    
    @Autowired
    private ChapterService chapterService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;
    
    @RequestMapping(value="post/chapter", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addChapter(@RequestBody JSONObject data) {
        JSONObject json = new JSONObject ();
        try {
            Chapter chapter = new Chapter();
            chapter.setContent((String) data.get("content"));
            chapter.setCourse(courseService.get(Integer.parseInt((String)data.get("course"))));
            chapter.setCreatedBy((String)data.get("admin"));
            chapter.setEditedBy((String)data.get("admin"));
            chapter.setNumber(Integer.parseInt((String)data.get("number")));
            chapter.setTitle((String)data.get("title"));            
            chapterService.add(chapter);
            chapter.getCourse().setDateEdited(null);
            chapter.getCourse().setEditedBy((String)data.get("admin"));
            courseService.update(chapter.getCourse());
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="put/chapter", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateChapter(@RequestBody JSONObject data) {
        JSONObject json = new JSONObject ();
        try {
            Chapter chapter = chapterService.get(Integer.parseInt((String)data.get("id")));
            chapter.setContent((String) data.get("content"));
            chapter.setEditedBy((String)data.get("admin"));
            chapter.setDateEdited(null);
            chapter.setNumber(Integer.parseInt((String)data.get("number")));
            chapter.setTitle((String)data.get("title"));            
            chapterService.update(chapter);            
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/chapters/{courseId}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getChaptersByCourse (@PathVariable(value="courseId") final int id) throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();  
        JSONObject json = new JSONObject();
        try {
            json.put("chapters", mapper.writeValueAsString(chapterService.getByCourseId(id)));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }                                    
        return json;
    }
    
    @RequestMapping(value="get/chapter/{chapterId}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getChapter (@PathVariable(value="chapterId") final int id) throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();  
        JSONObject json = new JSONObject();
        try {
            json.put("chapter", mapper.writeValueAsString(chapterService.get(id)));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }                                    
        return json;
    }
    
    @RequestMapping(value="delete/chapter", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteChapter(@RequestBody JSONObject data){        
        JSONObject json = new JSONObject();
        try {            
            chapterService.delete(chapterService.get(Integer.parseInt((String)data.get("id"))));            
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }

}
