
package com.aes.controller;

import com.aes.model.User;
import com.aes.service.CourseService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 27, 2015 9:24:14 AM
 * 
 */

@Controller
public class ExamGeneratorController {
    
    @Autowired
    CourseService courseService;
    
    @RequestMapping(value="/make-exam", method=RequestMethod.GET)
    private String takeExam(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)request.getSession().getAttribute("loggedUser");
        session.setAttribute("user", user.getUserType().getUserType());
        session.setAttribute("courses", courseService.getAll());
        return "exam_generator";
    }

}
