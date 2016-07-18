
package com.aes.controller;

import com.aes.model.User;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 13, 2015 7:49:03 AM
 * 
 */

@Controller
public class TraineeHomeController {

    @RequestMapping(value="/trainee", method=RequestMethod.GET)
    private String viewTraineeHome () {        
        return "trainee";
    }
    
}
