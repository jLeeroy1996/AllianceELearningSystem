
package com.aes.controller;

import com.aes.model.BusinessUnit;
import com.aes.service.BusinessUnitService;
import java.io.IOException;
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
 * Date Created: Sep 20, 2015 5:17:03 PM
 * 
 */

@Controller
public class BusinessUnitController {
    
    @Autowired
    private BusinessUnitService buService;
    
    @RequestMapping(value="post/bu", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addBusinessUnit (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject ();
        try {
            BusinessUnit bu = new BusinessUnit();
            bu.setBusinessUnit((String)data.get("bu"));
            bu.setCreatedBy((String)data.get("admin"));
            bu.setEditedBy((String)data.get("admin"));
            buService.add(bu);
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="delete/bu", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteBusinessUnit(@RequestBody JSONObject data){        
        JSONObject json = new JSONObject();
        try {
            buService.delete(buService.get(Integer.parseInt((String)data.get("id"))));
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", "Server Error: " + e.getMessage());
        }
        return json;
    }
    
    @RequestMapping(value="get/bus", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAllBusinessUnits () throws IOException  {                         
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<BusinessUnit> list = buService.getAll();
            json.put("bus", mapper.writeValueAsString(list));           
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }                                   
        return json;
    } 
    
    @RequestMapping(value="put/bu", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateBusinessUnit (@RequestBody JSONObject data) {        
        JSONObject json = new JSONObject();        
        try {            
            BusinessUnit bu = buService.get(Integer.parseInt((String)data.get("id")));
            bu.setBusinessUnit((String)data.get("name"));
            bu.setEditedBy((String)data.get("admin"));
            buService.update(bu);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;        
    }

}
