
package com.aes.controller;

import com.aes.model.CourseCategory;
import com.aes.service.CourseCategoryService;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 20, 2015 5:21:19 PM
 * 
 */

@Controller
public class CourseCategoryController {
    
    @Autowired
    private CourseCategoryService categoryService;
    
    @RequestMapping(value="post/category", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addCategory (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject ();
        try {
            CourseCategory category = new CourseCategory();
            category.setCategory((String)data.get("category"));
            category.setCreatedBy((String)data.get("admin"));
            category.setEditedBy((String)data.get("admin"));
            categoryService.add(category);
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/category", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteCategory(@RequestBody JSONObject data){
        
        JSONObject json = new JSONObject();
        try {
            categoryService.delete(categoryService.get(Integer.parseInt((String)data.get("id"))));
            json.put("error", "");
            System.out.println("Delete Category: " + json.toString());
        } catch (Exception e) {
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/categories" , method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getCategories () {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<CourseCategory> list = categoryService.getAll();       
            json.put("categories", mapper.writeValueAsString(list));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="put/category", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateCategory (@RequestBody JSONObject data) {        
        JSONObject json = new JSONObject();        
        try {
            CourseCategory category = categoryService.get(Integer.parseInt((String)data.get("id")));
            category.setCategory((String)data.get("name"));
            category.setEditedBy((String)data.get("admin"));
            categoryService.update(category);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;        
    }

}
