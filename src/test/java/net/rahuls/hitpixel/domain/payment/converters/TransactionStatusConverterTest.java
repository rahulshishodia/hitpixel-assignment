package net.rahuls.hitpixel.domain.payment.converters;

import net.rahuls.hitpixel.core.literals.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionStatusConverterTest {

    TransactionStatusConverter transactionStatusConverter = new TransactionStatusConverter();

    @Test
    void nullInput_convertToDatabaseColumn() {
        Integer value = transactionStatusConverter.convertToDatabaseColumn(null);
        assertNull(value);
    }

    @Test
    void nullInput_convertToEntityAttribute() {
        TransactionStatus transactionStatus = transactionStatusConverter.convertToEntityAttribute(null);
        assertNull(transactionStatus);
    }

    @Test
    void convertToDatabaseColumn() {
        TransactionStatus transactionStatus = TransactionStatus.PENDING;
        assertEquals(transactionStatus.getId(), transactionStatusConverter.convertToDatabaseColumn(transactionStatus));
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(TransactionStatus.PENDING.getId(), transactionStatusConverter.convertToEntityAttribute(10).getId());
    }

}