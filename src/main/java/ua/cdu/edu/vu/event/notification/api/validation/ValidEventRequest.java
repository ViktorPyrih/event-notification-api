package ua.cdu.edu.vu.event.notification.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventRequestValidator.class)
public @interface ValidEventRequest {

    String message() default "reminders cannot be in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
