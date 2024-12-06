package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodValidator implements ConstraintValidator<PaymentMethod, String> {

    @Override
    public boolean isValid(String paymentMethodName, ConstraintValidatorContext constraintValidatorContext) {
        if (paymentMethodName == null) {
            return false;
        }
        return net.rahuls.hitpixel.core.literals.PaymentMethod.fromName(paymentMethodName) != null;
    }
}
