
package com.aes.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 6, 2015 6:05:29 AM
 * 
 */

@Controller
public class LogoutController {

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    private String logout (HttpServletRequest request) throws IOException  {
        HttpSession session=request.getSession();
        session.removeAttribute("loggedUser");
        session.invalidate();
        return "login";
    }
    
}
