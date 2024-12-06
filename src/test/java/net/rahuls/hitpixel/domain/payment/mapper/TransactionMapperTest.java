package net.rahuls.hitpixel.domain.payment.mapper;

import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.literals.PaymentMethod;
import net.rahuls.hitpixel.core.literals.TransactionStatus;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

    @Spy
    private TransactionMapper transactionMapper = new TransactionMapperImpl();

    @Test
    void nullTransaction_toTransactionDto() {
        assertNull(transactionMapper.toTransactionDto(null));
    }

    @Test
    void nullTransactionDto_toTransaction() {
        assertNull(transactionMapper.toTransaction(null));
    }

    @Test
    void emptyTransactionDto_toTransaction() {
        Transaction transaction = transactionMapper.toTransaction(new TransactionDto());

        assertNotNull(transaction);
        assertNull(transaction.getId());
        assertNull(transaction.getUser());
        assertNull(transaction.getAmount());
        assertNull(transaction.getCurrency());
        assertNull(transaction.getPaymentMethod());
        assertNull(transaction.getStatus());
        assertNull(transaction.getCreatedAt());
    }

    @Test
    void validTransaction_toTransactionDto() {

        Transaction transaction = createTransaction(1L, "INR");

        TransactionDto transactionDto = transactionMapper.toTransactionDto(transaction);

        assertEquals(1L, transactionDto.getId());
        assertEquals(new BigDecimal("1234.5"), transactionDto.getAmount());
        assertEquals(Currency.getInstance("INR").getCurrencyCode(), transactionDto.getCurrency());
        assertEquals(PaymentMethod.PAYPAL.getName(), transactionDto.getPaymentMethod());
        assertEquals(TransactionStatus.PENDING, transactionDto.getStatus());
        assertNotNull(transactionDto.getCreatedAt());
    }

    @Test
    void nullCurrency_And_nullUserId_toTransactionDto() {

        Transaction transaction = createTransaction(null, null);

        TransactionDto transactionDto = transactionMapper.toTransactionDto(transaction);

        assertEquals(1L, transactionDto.getId());
        assertEquals(new BigDecimal("1234.5"), transactionDto.getAmount());
        assertNull(transactionDto.getCurrency());
        assertEquals(PaymentMethod.PAYPAL.getName(), transactionDto.getPaymentMethod());
        assertEquals(TransactionStatus.PENDING, transactionDto.getStatus());
        assertNotNull(transactionDto.getCreatedAt());
    }

    private static Transaction createTransaction(Long userId, String currency) {
        User user = null;
        if (userId != null) {
            user = new User();
            user.setId(userId);
        }

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUser(user);
        transaction.setAmount(new BigDecimal("1234.5"));
        if (currency != null) {
            transaction.setCurrency(Currency.getInstance(currency));
        }
        transaction.setPaymentMethod(PaymentMethod.PAYPAL);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        return transaction;
    }

    @Test
    void validTransactionDto_toTransaction() {
        TransactionDto transactionDto = createTransactionDto("INR");

        Transaction transaction = transactionMapper.toTransaction(transactionDto);

        assertEquals(1L, transaction.getId());
        assertNull(transaction.getUser());
        assertEquals(new BigDecimal("1234.5"), transaction.getAmount());
        assertEquals("INR", transaction.getCurrency().getCurrencyCode());
        assertEquals(PaymentMethod.CREDIT_CARD, transaction.getPaymentMethod());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertNotNull(transaction.getCreatedAt());
    }

    @Test
    void nullCurrency_And_nullUserId_toTransaction() {
        TransactionDto transactionDto = createTransactionDto(null);

        Transaction transaction = transactionMapper.toTransaction(transactionDto);

        assertEquals(1L, transaction.getId());
        assertNull(transaction.getUser());
        assertEquals(new BigDecimal("1234.5"), transaction.getAmount());
        assertNull(transaction.getCurrency());
        assertEquals(PaymentMethod.CREDIT_CARD, transaction.getPaymentMethod());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertNotNull(transaction.getCreatedAt());
    }

    private TransactionDto createTransactionDto(String currency) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setAmount(new BigDecimal("1234.5"));
        transactionDto.setCurrency(currency);
        transactionDto.setPaymentMethod("Credit Card");
        transactionDto.setStatus(TransactionStatus.PENDING);
        transactionDto.setCreatedAt(LocalDateTime.now());
        return transactionDto;
    }

}