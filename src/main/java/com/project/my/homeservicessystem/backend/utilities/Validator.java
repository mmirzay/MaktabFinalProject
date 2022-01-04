package com.project.my.homeservicessystem.backend.utilities;

import java.util.regex.Pattern;

public class Validator {
    private static EmailValidator emailValidator = new EmailValidator();

    private static class EmailValidator {
        private static Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        private static boolean validate(String email) {
            return pattern.matcher(email).matches();
        }
    }

    public static boolean validateEmail(String email) {
        return EmailValidator.validate(email);
    }
}
