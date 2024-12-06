package net.rahuls.hitpixel.api.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.api.RequestPayload;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.api.dto.TransactionHistoryDto;
import net.rahuls.hitpixel.api.validator.TransactionId;
import net.rahuls.hitpixel.core.ActionHandlerFactory;
import net.rahuls.hitpixel.core.actions.TransactionAction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentResource {

    private final ActionHandlerFactory actionHandlerFactory;

    @PostMapping
    public ResponsePayload<TransactionDto> addTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        var payload = new RequestPayload<>(TransactionAction.ADD_TRANSACTION, transactionDto);
        return actionHandlerFactory.handle(payload);
    }

    @GetMapping("/{transactionId}/status")
    public ResponsePayload<TransactionHistoryDto> transactionDetails(@PathVariable @TransactionId Long transactionId) {
        var payload = new RequestPayload<>(TransactionAction.TRANSACTION_DETAILS, transactionId);
        return actionHandlerFactory.handle(payload);
    }

    @GetMapping("/history")
    public ResponsePayload<List<TransactionDto>> history(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") @PositiveOrZero(message = "Page No cannot be negative") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Positive(message = "Page Size cannot be negative or zero") Integer pageSize) {
        var payload = new RequestPayload<>(TransactionAction.TRANSACTION_HISTORY, new TransactionHistoryDto(pageNo, pageSize));
        return actionHandlerFactory.handle(payload);
    }

    @PostMapping("/{transactionId}/refund")
    public ResponsePayload<TransactionDto> refund(@PathVariable @TransactionId Long transactionId) {
        var payload = new RequestPayload<>(TransactionAction.REFUND, transactionId);
        return actionHandlerFactory.handle(payload);
    }

}
