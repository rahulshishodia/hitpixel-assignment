package net.rahuls.hitpixel.domain.payment.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.rahuls.hitpixel.core.literals.TransactionStatus;

@Converter(autoApply = true)
class TransactionStatusConverter implements AttributeConverter<TransactionStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransactionStatus status) {
        return status == null ? null : status.getId();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(Integer id) {
        return id == null ? null : TransactionStatus.fromId(id);
    }

}
