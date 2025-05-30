package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.errors.NotEnoughBalanceException;
import com.wallet.wallets_command_service.errors.ZeroValueTransactionException;
import com.wallet.wallets_command_service.mappers.WalletEventMapper;
import com.wallet.wallets_command_service.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PostingService {
    private final BalanceHydrationService balanceHydrationService;
    private final DistributedLockService distributedLockService;

    private final EventRepository eventRepository;

    public PostingService(BalanceHydrationService balanceHydrationService,
                          DistributedLockService distributedLockService,
                          EventRepository eventRepository
    ) {
        this.balanceHydrationService = balanceHydrationService;
        this.distributedLockService = distributedLockService;
        this.eventRepository = eventRepository;
    }

    public PostTransactionDTO process(PostTransactionDTO dto) {
        return switch (dto.getEventType()) {
            case DEPOSIT -> post(dto);
            case WITHDRAW -> withdraw(dto);
            default -> throw new ZeroValueTransactionException("Invalid transaction amount");
        };
    }

    public PostTransactionDTO post(PostTransactionDTO dto) {
        WalletEventEntity eventEntity = WalletEventMapper.toEntity(dto);
        eventRepository.save(eventEntity);

        return dto;
    }

    private PostTransactionDTO withdraw(PostTransactionDTO dto) {
        var lockToken = distributedLockService.tryLock("wallet", dto.getWalletId());

        try {
            BigDecimal balance = balanceHydrationService.getBalance(dto.getWalletId());
            BigDecimal withdrawValue = dto.getValue().abs();
            if(balance.compareTo(withdrawValue) < 0)
                throw new NotEnoughBalanceException("Insufficient funds");

            post(dto);
        } finally {
            distributedLockService.releaseLock("wallet", dto.getWalletId(), lockToken);
        }

        return dto;
    }
}
