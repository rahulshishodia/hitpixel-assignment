package net.rahuls.hitpixel.domain.payment.handler;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.TransactionAction;
import net.rahuls.hitpixel.core.exceptions.PaymentException;
import net.rahuls.hitpixel.core.security.SecurityUtils;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.domain.payment.mapper.TransactionMapper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import static net.rahuls.hitpixel.core.literals.TransactionStatus.REFUNDED;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefundHandler implements RequestHandler<Long, TransactionDto> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final SecurityUtils securityUtils;

    @Override
    public Action getActionType() {
        return TransactionAction.REFUND;
    }

    @Retryable(retryFor = OptimisticLockException.class, maxAttempts=10, backoff=@Backoff(delay=100, maxDelay=500))
    @Transactional
    @Override
    public TransactionDto handle(Long transactionId) {
        User loggedInUser = securityUtils.getLoggedInUser();
        Transaction transaction = transactionRepository.findByIdAndUser(transactionId, loggedInUser)
                .orElseThrow(() -> new RuntimeException("Transaction not found in user account"));

        validate(transaction);

        transaction.setStatus(REFUNDED);
        transaction = transactionRepository.save(transaction);

        log.info("Transaction successfully refunded: {}", transaction.getId());

        return transactionMapper.toTransactionDto(transaction);
    }

    private static void validate(Transaction transaction) {
        if (REFUNDED.equals(transaction.getStatus())) {
            throw new PaymentException("Transaction already refunded");
        }
    }
}
