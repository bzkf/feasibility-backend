package de.numcodex.feasibility_gui_backend.query.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StructuredQueryValidator.class)
public @interface StructuredQueryValidation {
    String message() default "Structured query is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
