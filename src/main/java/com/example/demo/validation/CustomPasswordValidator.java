package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomPasswordValidator implements ConstraintValidator<CustomValidPassword, String> {
   private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

   @Override
   public void initialize(CustomValidPassword constraintAnnotation) {

   }

   @Override
   public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
      Matcher matcher = pattern.matcher(s);
      return matcher.matches();
   }
}
