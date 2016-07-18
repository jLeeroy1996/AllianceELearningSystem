
package com.aes.controller;

import com.aes.misc.ORMUtil;
import com.aes.model.UserType;
import com.aes.service.UserTypeService;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 20, 2015 5:30:38 PM
 * 
 */

@Controller
public class UserTypeController {
    
    @Autowired
    UserTypeService userTypeService;
    
    @RequestMapping(value="get/user_types", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAllUserTypes () {                         
        ObjectMapper mapper = new ObjectMapper();         
        JSONObject json = new JSONObject();
        try {
            List<UserType> list = userTypeService.getAll();
            List<UserType> types = new ArrayList<UserType>();
            for (UserType type: list){
                types.add(ORMUtil.initializeAndUnproxy(type));
            }
            json.put("types", mapper.writeValueAsString(types));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }                                         
        return json;
    }

}
