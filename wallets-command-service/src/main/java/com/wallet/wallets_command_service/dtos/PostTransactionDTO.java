package com.wallet.wallets_command_service.dtos;

import com.wallet.wallets_command_service.entities.EventType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class PostTransactionDTO {
    UUID walletId;
    BigDecimal value;
    EventType eventType;
    Instant moment;

    public PostTransactionDTO(UUID walletId, BigDecimal value) {
        this.walletId = walletId;
        this.value = value;
        this.eventType = EventType.fromValue(value);
        this.moment = Instant.now();
    }
}
