package com.fernandocanabarro.desafio_fcamara_books.services.validations;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegistrationRequestDTOValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistrationRequestDTOValid {

    String message() default "Validation Error";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
