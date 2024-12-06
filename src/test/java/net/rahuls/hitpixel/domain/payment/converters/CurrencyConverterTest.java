package net.rahuls.hitpixel.domain.payment.converters;

import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CurrencyConverterTest {

    CurrencyConverter currencyConverter = new CurrencyConverter();

    @Test
    void nullInput_convertToDatabaseColumn() {
        String value = currencyConverter.convertToDatabaseColumn(null);
        assertNull(value);
    }

    @Test
    void nullInput_convertToEntityAttribute() {
        Currency currency = currencyConverter.convertToEntityAttribute(null);
        assertNull(currency);
    }

    @Test
    void convertToDatabaseColumn() {
        Currency currency = Currency.getInstance("INR");
        assertEquals(currency.getCurrencyCode(), currencyConverter.convertToDatabaseColumn(currency));
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(Currency.getInstance("INR"), currencyConverter.convertToEntityAttribute("INR"));
    }
}