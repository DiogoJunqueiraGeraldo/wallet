package com.wallet.wallets_command_service.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionPostedResponse(
        UUID walletId,
        BigDecimal value,
        String eventType,
        Instant moment
) {
    public static TransactionPostedResponse of(PostTransactionDTO resultDto) {
        return new TransactionPostedResponse(
                resultDto.getWalletId(),
                resultDto.getValue(),
                resultDto.getEventType().toString(),
                resultDto.getMoment()
        );
    }
}
