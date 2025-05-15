package com.wallet.wallets_command_service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class LockingTimeoutException extends RuntimeException {
    public LockingTimeoutException(String message) {
        super(message);
    }
}
