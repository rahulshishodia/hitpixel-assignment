package net.rahuls.hitpixel.core.literals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionStatusTest {

    @Test
    void testFromId_whenIdMatchesValidTransactionStatus() {
        assertEquals(TransactionStatus.PENDING, TransactionStatus.fromId(10));
        assertEquals(TransactionStatus.SUCCESS, TransactionStatus.fromId(20));
        assertEquals(TransactionStatus.FAILURE, TransactionStatus.fromId(30));
        assertEquals(TransactionStatus.ERROR, TransactionStatus.fromId(99));
    }

    @Test
    void testFromId_whenIdDoesNotMatchAnyTransactionStatus() {
        assertNull(TransactionStatus.fromId(0));
        assertNull(TransactionStatus.fromId(100));
        assertNull(TransactionStatus.fromId(50));
    }

    @Test
    void testFromId_whenIdIsNegative() {
        assertNull(TransactionStatus.fromId(-1));
    }
}
