package com.wallet.wallets_command_service.entities;

public enum WalletEventType {
    WITHDRAW("withdraw"),
    DEPOSIT("deposit"),
    DENIED("denied");

    private final String value;
    WalletEventType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
