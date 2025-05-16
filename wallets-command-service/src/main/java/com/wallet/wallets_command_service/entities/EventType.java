package com.wallet.wallets_command_service.entities;

import java.math.BigDecimal;

public enum EventType {
    WITHDRAW("withdraw"),
    DEPOSIT("deposit"),
    DENIED("denied");

    private final String value;
    EventType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static EventType fromValue(BigDecimal value) {
        if (value.signum() < 0) return WITHDRAW;
        if (value.signum() > 0) return DEPOSIT;
        return DENIED;
    }
}
