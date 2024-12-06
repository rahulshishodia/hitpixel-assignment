package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PaymentMethodValidatorTest {

    @InjectMocks
    private PaymentMethodValidator paymentMethodValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void testIsValid_whenPaymentMethodNameIsNull() {
        assertFalse(paymentMethodValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void testIsValid_whenPaymentMethodNameIsInvalid() {
        assertFalse(paymentMethodValidator.isValid("INVALID_PAYMENT_METHOD", constraintValidatorContext));
    }

    @Test
    void testIsValid_whenPaymentMethodNameIsValid() {
        assertTrue(paymentMethodValidator.isValid("Credit Card", constraintValidatorContext));
    }
}
