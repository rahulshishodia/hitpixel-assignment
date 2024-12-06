package net.rahuls.hitpixel.domain.payment.converters;

import net.rahuls.hitpixel.core.literals.PaymentMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentMethodConverterTest {

    PaymentMethodConverter paymentMethodConverter = new PaymentMethodConverter();

    @Test
    void nullInput_convertToDatabaseColumn() {
        Integer value = paymentMethodConverter.convertToDatabaseColumn(null);
        assertNull(value);
    }

    @Test
    void nullInput_convertToEntityAttribute() {
        PaymentMethod paymentMethod = paymentMethodConverter.convertToEntityAttribute(null);
        assertNull(paymentMethod);
    }

    @Test
    void convertToDatabaseColumn() {
        PaymentMethod paymentMethod = PaymentMethod.PAYPAL;
        assertEquals(paymentMethod.getId(), paymentMethodConverter.convertToDatabaseColumn(paymentMethod));
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(PaymentMethod.PAYPAL.getId(), paymentMethodConverter.convertToEntityAttribute(1).getId());
    }
}