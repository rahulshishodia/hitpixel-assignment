package net.rahuls.hitpixel.api.dto;

import java.util.List;


public record TransactionHistoryDto(List<TransactionDto> transactions, int pageNo, int pageSize) {

    public TransactionHistoryDto(int pageNo, int pageSize) {
        this(List.of(), pageNo, pageSize);
    }
}
