package net.rahuls.hitpixel.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaymentMethodValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentMethod {
    String message() default "Invalid Payment Method";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}