package com.splitwise.clone.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface StrongPassword {

    String message() default
            "Password must contain lower,upper,digit and special character";

    Class <?>[] groups() default {};
    Class <? extends Payload>[] payload() default {};
}
