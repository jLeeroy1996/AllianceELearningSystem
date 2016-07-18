
package com.aes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 26, 2015 7:13:09 PM
 * 
 */

@Controller
public class ExamPageController {
    
    @RequestMapping(value="/take-exam", method=RequestMethod.GET)
    private String takeExam(@RequestParam("id") int id,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("examId", id);
        return "exam_player";
    }

}
