
package com.aes.controller;

import com.aes.misc.PasswordUtil;
import com.aes.misc.Validator;
import com.aes.model.User;
import com.aes.service.BusinessUnitService;
import com.aes.service.UserService;
import com.aes.service.UserTypeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * Date Created: Sep 20, 2015 4:56:44 PM
 * 
 */

@Controller
public class UserController {
    
    @Autowired
    BusinessUnitService buService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserTypeService userTypeService;    
    
    @RequestMapping(value="post/user", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject addUser (@RequestBody JSONObject data) throws IOException  {        
        JSONObject json = new JSONObject();        
        String username = (String)data.get("username");
        Pattern noSpecialChar = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern whiteSpace = Pattern.compile("\\s");
        Matcher m2 = whiteSpace.matcher(username);
        Matcher m1 = noSpecialChar.matcher(username);        
        
        List<String> errorList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        if (userService.isExisting(username))                      
            errorList.add("Username is already in use.");
        if (username.length()<5)
            errorList.add("Username is too short.");
        if (m1.find() || m2.find())
            errorList.add("Username should not contain any special characters."); 
        if (!Validator.isValidEmail((String)data.get("email")))
            errorList.add("Please enter a valid email.");
        if (errorList.isEmpty()){
            try {
                User user = new User ();
                user.setUserName((String)data.get("username"));
                user.setName((String)data.get("name"));
                user.setPosition((String)data.get("position"));
                user.setEmail((String)data.get("email")); 
                User loggedUser = (User)userService.get((String)data.get("admin"));
                user.setCreatedBy(loggedUser.getUserName());
                user.setEditedBy(loggedUser.getUserName());
                user.setBusinessUnit(buService.get(Integer.valueOf((String)data.get("bu"))));
                user.setUserType(userTypeService.get(Integer.valueOf((String)data.get("type"))));
                user.setActive(true);
                userService.add(user);
                json.put("error", mapper.writeValueAsString(errorList));                
            } catch (Exception e){
                e.printStackTrace(); 
                errorList.add(e.getMessage());
                json.put("error", mapper.writeValueAsString(errorList));                
            }
        } else {
            json.put("error", mapper.writeValueAsString(errorList));
        }
        return json;
    }
    
    @RequestMapping(value="delete/user", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject deleteUser (@RequestBody JSONObject data) throws IOException  {         
        JSONObject json = new JSONObject();        
        try {
            User user = userService.get((String)data.get("username"));
            userService.delete(user);
            json.put("error", "");
        } catch (Exception e) {
            json.put("error", "SQL Error: " + e.getMessage());
        }       
        return json;
    }
    
    @RequestMapping(value="get/logged_user", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getLoggedUser (HttpServletRequest request) throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();         
        JSONObject json = new JSONObject();        
        try {
            User user = (User)request.getSession().getAttribute("loggedUser");            
            json.put("user", mapper.writeValueAsString(userService.get(user.getUserName())));            
            json.put("error", "");
            return json;
        } catch (Exception e) {
            e.printStackTrace();            
            json.put("error", e.getMessage());
        } 
        return json;
    }
    
    @RequestMapping(value="get/trainers", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAllTrainers () throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();  
        JSONObject json = new JSONObject();
        try {
            json.put("trainers", mapper.writeValueAsString(userService.getAllTrainers()));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }                                    
        return json;
    }
    
    @RequestMapping(value="get/user/{username}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getUser (@PathVariable(value="username") String username) throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();         
        JSONObject json = new JSONObject();        
        try {         
            json.put("user", mapper.writeValueAsString(userService.get(username)));
            json.put("error", "");   
        } catch (Exception e) {
            e.printStackTrace();            
            json.put("error", e.getMessage());
        } 
        return json;
    }
    
    @RequestMapping(value="get/users", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getAllUsers () throws IOException  {                         
        ObjectMapper mapper = new ObjectMapper();  
        JSONObject json = new JSONObject();
        try {
            json.put("users", mapper.writeValueAsString(userService.getAll()));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }                                    
        return json;
    }
    
    @RequestMapping(value="password/reset", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject resetPassword (@RequestBody JSONObject data) throws IOException  {         
        JSONObject json = new JSONObject();        
        try {
            User user = userService.get((String)data.get("username"));
            userService.resetPassword(user);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "SQL Error: " + e.getMessage());
        }        
        return json;
        
    }
    
    @RequestMapping(value="user/search/{username}", method=RequestMethod.GET)
    @ResponseBody
    public JSONObject searchUser (@PathVariable(value="username") String name) throws IOException  {            
        JSONObject json = new JSONObject();        
        ObjectMapper mapper = new ObjectMapper();
        try {
            json.put("users", mapper.writeValueAsString(userService.searchByName(name)));
            json.put("error","");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        } 
        return json;
    }
    
    @RequestMapping(value="put/user", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateUser (@RequestBody JSONObject data) throws IOException  { 
        JSONObject json = new JSONObject();        
        String username = (String)data.get("username");
        Pattern noSpecialChar = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);        
        
        List<String> errorList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        if (!Validator.isValidEmail((String)data.get("email")))
            errorList.add("Please enter a valid email.");
        if (errorList.isEmpty()){
            try {
                User user = userService.getUserDetails((String)data.get("username"));
                user.setName((String)data.get("name"));
                user.setPosition((String)data.get("position"));
                user.setEmail((String)data.get("email")); 
                User loggedUser = (User)userService.get((String)data.get("admin"));
                user.setEditedBy(loggedUser.getUserName());
                user.setDateEdited(null);
                user.setBusinessUnit(buService.get(Integer.parseInt((String)data.get("bu"))));
                user.setUserType(userTypeService.get(Integer.parseInt((String)data.get("type"))));
                userService.update(user);
                json.put("error", mapper.writeValueAsString(errorList));                
            } catch (Exception e){
                e.printStackTrace(); 
                errorList.add(e.getMessage());
                json.put("error", mapper.writeValueAsString(errorList));                
            }
        } else {
            json.put("error", mapper.writeValueAsString(errorList));
        }
        return json;
    }
    
    @RequestMapping(value="put/profile", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject updateUserProfile (@RequestBody JSONObject data) throws IOException{        
        
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        String email = (String)data.get("email");
        String name = (String)data.get("name");
        String username = (String)data.get("username");
        
        if (Validator.isValidEmail(email)){
            try {
                User user = userService.get(username);
                user.setName(name);
                user.setEmail(email);
                user.setEditedBy(user.getUserName());
                user.setDateEdited(null);
                userService.update(user);
                json.put("user", mapper.writeValueAsBytes(userService.get(username)));
                json.put("error", "");
            } catch (Exception e) {
                e.printStackTrace();
                json.put("error", "Please contact the admin. Error Message:" + e.getMessage());
            }
        } else {
            json.put("error", "Email is invalid. Please check your entry.");
        }
        return json;
    }
    
    @RequestMapping(value="put/user/password", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject changePassword(@RequestBody JSONObject data){
        
        JSONObject json = new JSONObject();
        List<String> errorList = new ArrayList<String>();
        
        User user = userService.get((String)data.get("username"));
        String password = (String)data.get("password");
        String newPassword1 = (String)data.get("newPassword1");
        String newPassword2 = (String)data.get("newPassword2");
        Pattern whiteSpace = Pattern.compile("\\s");
        Matcher matcher = whiteSpace.matcher(newPassword1);
        
        if (!newPassword1.equals(newPassword2))
            errorList.add("New passwords doesn't match.");
        if (!user.getPassword().equals(PasswordUtil.getSecurePassword(password)))
            errorList.add("Old password is incorrect.");
        if (password.equals(newPassword1))
            errorList.add("New password was used previously.");        
        if (newPassword1.equals("") || newPassword2.equals(""))
            errorList.add("Password should not be blank.");
        if (newPassword1.contains(" "))
            errorList.add("Password should not contain any space.");
        if (newPassword1.length() < 5)
            errorList.add("Password should contain at least 5 characters.");
        if (errorList.isEmpty()){
            try {
                user.setPassword(PasswordUtil.getSecurePassword(newPassword1));
                user.setEditedBy((String)data.get("username"));
                user.setDateEdited(null);
                userService.update(user);
            } catch (Exception e) {
                errorList.add("Server Error: " + e.getMessage());
            }
        }         
        json.put("error", errorList);
        return json;
    }
    
}
