package net.rahuls.hitpixel.core.literals;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionStatus {

    PENDING(10),
    SUCCESS(20),
    FAILURE(30),
    REFUNDED(40),
    ERROR(99),;


    private final int id;

    public static TransactionStatus fromId(int id) {
        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.id == id) {
                return transactionStatus;
            }
        }
        return null;
    }

}
