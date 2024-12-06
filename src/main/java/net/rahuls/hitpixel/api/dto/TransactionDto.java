package net.rahuls.hitpixel.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.rahuls.hitpixel.api.validator.PaymentMethod;
import net.rahuls.hitpixel.core.literals.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TransactionDto {

    private Long id;

    @Positive(message = "Amount is not valid.")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required.")
    private String currency;

    @NotBlank(message = "Payment method is required.")
    @PaymentMethod
    private String paymentMethod;

    private TransactionStatus status;

    private LocalDateTime createdAt;
}