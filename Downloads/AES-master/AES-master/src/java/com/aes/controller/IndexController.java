
package com.aes.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 9, 2015 10:46:43 AM
 * 
 */

@Controller
public class IndexController {

    @RequestMapping(value={"/index", "/"}, method=RequestMethod.GET)
    private String viewAddUser () throws IOException  {        
        return "login";
    }
    
}
