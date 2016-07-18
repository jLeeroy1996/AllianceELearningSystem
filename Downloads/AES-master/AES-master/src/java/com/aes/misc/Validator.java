
package com.aes.misc;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 5, 2015 8:35:24 PM
 * 
 */

public class Validator {
    
    public static boolean isValidEmail (String email){
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;        
    }

}
