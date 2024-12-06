package net.rahuls.hitpixel.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionId {
    String message() default "Invalid Transaction ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}