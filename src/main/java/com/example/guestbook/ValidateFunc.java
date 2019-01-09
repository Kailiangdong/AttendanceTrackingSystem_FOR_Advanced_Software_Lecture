package com.example.guestbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFunc {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[A-Za-z]{0,16}$",
            Pattern.CASE_INSENSITIVE);        

    public static boolean validateEmail(String emailStr) {
        if(emailStr == null) return false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePwd(String pwdStr) {
        if(pwdStr == null) return false;
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(pwdStr);
        return matcher.find();
    }

    public static boolean validateName(String nameStr) {
        if(nameStr == null) return false;
        Matcher matcher = VALID_NAME_REGEX.matcher(nameStr);
        return matcher.find();
    }
}