package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionValidator implements ConstraintValidator<TransactionId, Long> {

    private final TransactionRepository transactionRepository;

    @Override
    public boolean isValid(Long transactionId, ConstraintValidatorContext constraintValidatorContext) {
        if (transactionId == null) {
            return false;
        }
        return transactionRepository.existsById(transactionId);
    }
}
