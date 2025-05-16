package com.wallet.wallets_command_service.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record PostTransactionRequest(
        @NotNull UUID walletId,
        @NotNull BigDecimal value
) {
    public PostTransactionRequest {
        if(value.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Value must be greater or less than zero");
        }
    }
}
