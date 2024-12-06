package net.rahuls.hitpixel.domain.payment.handler;

import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.TransactionAction;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.domain.payment.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransactionDetailsHandler implements RequestHandler<Long, TransactionDto> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Action getActionType() {
        return TransactionAction.TRANSACTION_DETAILS;
    }

    @Override
    public TransactionDto handle(Long transactionId) {
        Transaction transaction = transactionRepository.findTransactionById(transactionId);

        return transactionMapper.toTransactionDto(transaction);
    }
}
