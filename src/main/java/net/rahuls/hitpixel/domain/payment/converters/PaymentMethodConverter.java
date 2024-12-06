package net.rahuls.hitpixel.domain.payment.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.rahuls.hitpixel.core.literals.PaymentMethod;

@Converter(autoApply = true)
class PaymentMethodConverter implements AttributeConverter<PaymentMethod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentMethod paymentMethod) {
        return paymentMethod == null ? null : paymentMethod.getId();
    }

    @Override
    public PaymentMethod convertToEntityAttribute(Integer id) {
        return id == null ? null : PaymentMethod.fromId(id);
    }

}
