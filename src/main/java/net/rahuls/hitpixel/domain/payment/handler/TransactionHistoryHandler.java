package net.rahuls.hitpixel.domain.payment.handler;

import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.api.dto.TransactionHistoryDto;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.TransactionAction;
import net.rahuls.hitpixel.core.security.SecurityUtils;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.domain.payment.mapper.TransactionMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryHandler implements RequestHandler<TransactionHistoryDto, TransactionHistoryDto> {

    private final TransactionRepository transactionRepository;
    private final SecurityUtils securityUtils;
    private final TransactionMapper transactionMapper;

    public TransactionHistoryHandler(TransactionRepository transactionRepository, SecurityUtils securityUtils,
                                     TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.securityUtils = securityUtils;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Action getActionType() {
        return TransactionAction.TRANSACTION_HISTORY;
    }

    @Override
    public TransactionHistoryDto handle(TransactionHistoryDto transactionHistoryDto) {
        User loggedInUser = securityUtils.getLoggedInUser();
        int pageNo = transactionHistoryDto.pageNo() - 1;
        int pageSize = transactionHistoryDto.pageSize();

        Pageable page = Pageable.ofSize(pageSize).withPage(pageNo);
        List<Transaction> result = transactionRepository.findAllByUser(loggedInUser, page);

        List<TransactionDto> transactionDtos = result.stream().map(transactionMapper::toTransactionDto).toList();
        return new TransactionHistoryDto(transactionDtos, pageNo + 1, pageSize);
    }
}
