
package com.aes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 13, 2015 9:05:52 AM
 * 
 */

@Controller
public class TrainerHomeController {

    @RequestMapping(value="/trainer", method=RequestMethod.GET)
    private String viewTrainerHome (){
        return "trainer";
    }
    
}
