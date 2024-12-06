package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidatorContext;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {

    @InjectMocks
    private TransactionValidator transactionValidator;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void testIsValid_whenTransactionIdIsNull() {
        assertFalse(transactionValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void testIsValid_whenTransactionIdDoesNotExist() {
        Long nonExistingTransactionId = 123L;

        when(transactionRepository.existsById(nonExistingTransactionId)).thenReturn(false);

        assertFalse(transactionValidator.isValid(nonExistingTransactionId, constraintValidatorContext));
    }

    @Test
    void testIsValid_whenTransactionIdExists() {
        Long existingTransactionId = 123L;

        when(transactionRepository.existsById(existingTransactionId)).thenReturn(true);

        assertTrue(transactionValidator.isValid(existingTransactionId, constraintValidatorContext));
    }
}
