package net.rahuls.hitpixel.domain.payment.handler;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.TransactionAction;
import net.rahuls.hitpixel.core.literals.TransactionStatus;
import net.rahuls.hitpixel.core.security.SecurityUtils;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.domain.payment.mapper.TransactionMapper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateTransactionHandler implements RequestHandler<TransactionDto, TransactionDto> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final SecurityUtils securityUtils;

    @Override
    public Action getActionType() {
        return TransactionAction.ADD_TRANSACTION;
    }

    @Retryable(retryFor = OptimisticLockException.class, maxAttempts=10, backoff=@Backoff(delay=100, maxDelay=500))
    @Transactional
    @Override
    public TransactionDto handle(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.toTransaction(transactionDto);

        User loggedInUser = securityUtils.getLoggedInUser();
        transaction.setUser(loggedInUser);

        transaction.setStatus(TransactionStatus.PENDING);

        transaction = transactionRepository.save(transaction);

        log.info("Created transaction: {}", transaction.getId());

        return transactionMapper.toTransactionDto(transaction);
    }


}
