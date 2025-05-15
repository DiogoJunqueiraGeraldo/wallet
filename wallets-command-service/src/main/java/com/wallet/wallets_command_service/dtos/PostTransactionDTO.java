package com.wallet.wallets_command_service.dtos;

import com.wallet.wallets_command_service.controllers.PostTransactionRequest;
import com.wallet.wallets_command_service.entities.WalletEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostTransactionDTO {
    UUID walletId;
    BigDecimal value;
    WalletEventType eventType;
    Instant moment;

    public static PostTransactionDTO of(PostTransactionRequest request) {
        return PostTransactionDTO.builder()
                .walletId(request.walletId())
                .value(request.value())
                .build();
    }
}
