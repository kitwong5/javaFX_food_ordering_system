package application.view.controller;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
 


public class ValidateInputController {
	
	public static boolean isFloat(String txt) {
		try {  
		    Double.parseDouble(txt);  	
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	public static boolean isInt(String txt) {
		try {    
			if (Integer.parseInt(txt) > 0) {
				return true;
			} else {
				return false;
			}
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}

	public static boolean isInt0(String txt) {
		try {    
			if (Integer.parseInt(txt) >= 0) {
				return true;
			} else {
				return false;
			}
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}

	
	public static boolean isWitinLegth(String txt, Integer length_limit) {    
		if (txt.length() > length_limit) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isAtLeastLegth(String txt, Integer length_limit) {    
		if (txt.length() >= length_limit) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEqualLegth(String txt, Integer length_limit) {    
		if (txt.length() == length_limit) {
			return true;
		} else {
			return false;
		}
	}

	
	public static boolean isValidCardNumber(String txt) {
        String regex = "\\d{16}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txt);
        return matcher.matches();
    }
	
	//Reference: geeksforgeeks(2014). Check if Email Address is Valid or not in Java. 
	//Retrieved May 10, 2024 from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
    public static boolean isEMail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
    
}
