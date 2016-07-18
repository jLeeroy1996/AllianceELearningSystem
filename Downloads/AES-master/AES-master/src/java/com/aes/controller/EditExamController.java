/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aes.controller;

import com.aes.model.User;
import com.aes.service.CourseService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author bryan
 */
@Controller
public class EditExamController {
    
    @Autowired
    CourseService courseService;
    
    @RequestMapping(value="/edit-exam", method=RequestMethod.GET)
    private String takeExam(@RequestParam("id") int id,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)request.getSession().getAttribute("loggedUser");
        session.setAttribute("user", user.getUserType().getUserType());
        session.setAttribute("courses", courseService.getAll());
        session.setAttribute("examId", id);
        return "exam_editor";
    }
    
}
