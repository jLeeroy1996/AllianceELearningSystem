
package com.aes.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 6, 2015 5:56:27 AM
 * 
 */

@Controller
public class AdminHomeController {
    
    @RequestMapping(value="/admin", method=RequestMethod.GET)
    private String viewAddUser () throws IOException  {        
        return "admin";
    }

}
