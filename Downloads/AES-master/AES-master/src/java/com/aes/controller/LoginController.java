
package com.aes.controller;


import com.aes.misc.DatabaseDefaults;
import com.aes.model.User;
import com.aes.service.UserService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 12:36:51 PM
 * 
 */

@Controller
public class LoginController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value="/home", method=RequestMethod.POST)
    private String login(@RequestParam("username") String username, 
            @RequestParam("password") String password, HttpServletResponse response, 
            HttpServletRequest request, Map<String, Object> map) throws IOException {        
        try {            
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            if (userService.checkCredentials(user)){
                user = userService.getUserDetails(username);
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                if (user.getUserType().getId() == DatabaseDefaults.ADMIN){                    
                    return "admin";                
                } else if (user.getUserType().getId() == DatabaseDefaults.TRAINEE){                                        
                    return "trainee";
                } else if (user.getUserType().getId() == DatabaseDefaults.TRAINER){
                    return "trainer";
                }  
            }           
        } catch (Exception e){                        
            map.put("error", e.getMessage());
            return "login";
        }
        map.put("error", "Invalid credentials. Please try again.");
        return "login";
    }   
    
}
