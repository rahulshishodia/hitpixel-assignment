package net.rahuls.hitpixel.core.literals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentMethodTest {

    @Test
    void testFromId_whenIdIsNull() {
        assertNull(PaymentMethod.fromId(null));
    }

    @Test
    void testFromId_whenIdMatchesValidPaymentMethod() {
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.fromId(1));
        assertEquals(PaymentMethod.NET_BANKING, PaymentMethod.fromId(2));
        assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromId(3));
        assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromId(4));
        assertEquals(PaymentMethod.AMAZON_PAY, PaymentMethod.fromId(5));
        assertEquals(PaymentMethod.APPLE_PAY, PaymentMethod.fromId(6));
        assertEquals(PaymentMethod.ALIPAY, PaymentMethod.fromId(7));
    }

    @Test
    void testFromId_whenIdDoesNotMatchAnyPaymentMethod() {
        assertNull(PaymentMethod.fromId(999));
    }

    @Test
    void testFromName_whenNameIsNull() {
        assertNull(PaymentMethod.fromName(null));
    }

    @Test
    void testFromName_whenNameMatchesValidPaymentMethod() {
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.fromName("PayPal"));
        assertEquals(PaymentMethod.NET_BANKING, PaymentMethod.fromName("Net Banking"));
        assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromName("Credit Card"));
        assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromName("Debit Card"));
        assertEquals(PaymentMethod.AMAZON_PAY, PaymentMethod.fromName("Amazon Pay"));
        assertEquals(PaymentMethod.APPLE_PAY, PaymentMethod.fromName("Apple Pay"));
        assertEquals(PaymentMethod.ALIPAY, PaymentMethod.fromName("Ali Pay"));
    }

    @Test
    void testFromName_whenNameIsCaseInsensitive() {
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.fromName("paypal"));
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.fromName("PAYPAL"));
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.fromName("PaYpAl"));
    }

    @Test
    void testFromName_whenNameDoesNotMatchAnyPaymentMethod() {
        assertNull(PaymentMethod.fromName("Invalid Payment Method"));
    }
}
