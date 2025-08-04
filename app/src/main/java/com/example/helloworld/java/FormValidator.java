package com.example.helloworld.java;

import android.text.TextUtils;
import android.util.Patterns;

public class FormValidator {
    
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }
    
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
    
    public static String validateSignupForm(String firstName, String lastName, String email, String password, String confirmPassword) {
        // Allow all fields to be blank or have minimal content
        // Only validate if passwords are provided, they should match
        if ((password != null && !password.isEmpty()) || (confirmPassword != null && !confirmPassword.isEmpty())) {
            if (!doPasswordsMatch(password, confirmPassword)) {
                return "Passwords do not match";
            }
        }
        
        return "SUCCESS";
    }
} 