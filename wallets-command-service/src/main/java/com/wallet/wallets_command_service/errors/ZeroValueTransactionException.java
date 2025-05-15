package com.wallet.wallets_command_service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ZeroValueTransactionException extends RuntimeException {
    public ZeroValueTransactionException(String message) {
        super(message);
    }
}
