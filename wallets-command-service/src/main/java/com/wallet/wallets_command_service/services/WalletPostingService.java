package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.entities.WalletEventType;
import com.wallet.wallets_command_service.errors.ZeroValueTransactionException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class WalletPostingService {
    private final WalletDepositService depositService;
    private final WalletWithdrawService withdrawService;

    public WalletPostingService(WalletDepositService depositService,
                                WalletWithdrawService withdrawService) {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
    }

    public PostTransactionDTO process(PostTransactionDTO dto) {
        dto.setMoment(Instant.now());
        setWalletEventType(dto);
        return switch (dto.getEventType()) {
            case DEPOSIT -> depositService.post(dto);
            case WITHDRAW -> withdrawService.post(dto);
            default -> throw new ZeroValueTransactionException("Invalid transaction amount");
        };
    }

    private void setWalletEventType(PostTransactionDTO dto) {
        if (dto.getValue().signum() < 0) dto.setEventType(WalletEventType.WITHDRAW);
        else if (dto.getValue().signum() > 0) dto.setEventType(WalletEventType.DEPOSIT);
        else dto.setEventType(WalletEventType.DENIED);
    }
}
