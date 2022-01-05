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

    private static class PasswordValidator {
        private static final int MIN_LENGTH = 8;
        private static Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{" + MIN_LENGTH + ",}$");

        private static boolean validate(String password) {
            return pattern.matcher(password).matches();
        }
    }

    public static boolean validateEmail(String email) {
        return email != null && EmailValidator.validate(email);
    }

    public static boolean validatePassword(String password) {
        return password != null && PasswordValidator.validate(password);
    }
}
