package net.rahuls.hitpixel.domain.payment.handler;

import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.literals.TransactionStatus;
import net.rahuls.hitpixel.core.security.SecurityUtils;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.domain.payment.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionHandlerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private CreateTransactionHandler createTransactionHandler;

    private TransactionDto transactionDto;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        when(securityUtils.getLoggedInUser()).thenReturn(new User());

        transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setCurrency(Currency.getInstance("EUR"));
        transaction.setStatus(TransactionStatus.PENDING);

        when(transactionMapper.toTransaction(any(TransactionDto.class))).thenReturn(transaction);

        transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(100));
        transactionDto.setCurrency("EUR");
        transactionDto.setStatus(TransactionStatus.PENDING);

        when(transactionMapper.toTransactionDto(any(Transaction.class))).thenReturn(transactionDto);
    }

    @Test
    void testHandle_whenTransactionIsCreatedSuccessfully() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDto result = createTransactionHandler.handle(transactionDto);

        assertEquals(TransactionStatus.PENDING, result.getStatus());
        assertNotNull(result);
        verify(transactionRepository, times(1)).save(transaction);
        verify(transactionMapper, times(1)).toTransactionDto(transaction);
    }

    @Test
    void testHandle_whenTransactionStatusIsPending() {
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        TransactionDto result = createTransactionHandler.handle(transactionDto);

        assertEquals(TransactionStatus.PENDING, result.getStatus(), "Transaction status should be PENDING");
    }
}
