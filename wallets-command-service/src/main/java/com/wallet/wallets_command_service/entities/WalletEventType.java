package com.wallet.wallets_command_service.entities;

import java.util.Arrays;

public enum WalletEventType {
    WITHDRAW("withdraw"),
    DEPOSIT("deposit"),
    DENIED("denied");

    private final String value;
    WalletEventType(String value) {
        this.value = value;
    }

    public static WalletEventType of(String eventType) {
        return Arrays.stream(WalletEventType.values())
                .filter(walletEventType -> walletEventType.value.equals(eventType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return value;
    }
}
