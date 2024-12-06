package net.rahuls.hitpixel.domain.payment.mapper;

import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.data.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(target = "paymentMethod", expression = "java(transaction.getPaymentMethod().getName())")
    TransactionDto toTransactionDto(Transaction transaction);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "paymentMethod", expression = "java(net.rahuls.hitpixel.core.literals.PaymentMethod.fromName(transactionDto.getPaymentMethod()))")
    Transaction toTransaction(TransactionDto transactionDto);
}
