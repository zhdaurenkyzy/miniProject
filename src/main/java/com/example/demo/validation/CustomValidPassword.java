package com.example.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomPasswordValidator.class)
public @interface CustomValidPassword {
    String message() default "Password must contains one digit from 0-9, one lowercase, " +
            "one uppercase characters, one special symbols in the list @#$%, length at least 6 characters" +
            " and maximum of 20";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
