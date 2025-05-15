package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.entities.WalletSnapshotEntity;
import com.wallet.wallets_command_service.errors.LockingTimeoutException;
import com.wallet.wallets_command_service.errors.NotEnoughBalanceException;
import com.wallet.wallets_command_service.mappers.WalletEventMapper;
import com.wallet.wallets_command_service.repositories.WalletEventRepository;
import com.wallet.wallets_command_service.repositories.WalletSnapshotRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletWithdrawService implements IWalletPostingService {
    private final WalletEventRepository eventRepository;
    private final WalletSnapshotRepository snapshotRepository;
    private final DistributedLockService distributedLockService;

    public WalletWithdrawService(WalletEventRepository eventRepository,
                                 WalletSnapshotRepository snapshotRepository,
                                 DistributedLockService distributedLockService) {
        this.eventRepository = eventRepository;
        this.snapshotRepository = snapshotRepository;
        this.distributedLockService = distributedLockService;
    }

    @Override
    public PostTransactionDTO post(PostTransactionDTO dto) {
        var lockToken = distributedLockService.tryLock("wallet", dto.getWalletId());
        if(lockToken.isEmpty()) {
            throw new LockingTimeoutException("Timeout: couldn't proceed with the withdraw, try again later.");
        }

        try {
            if(!hasBalance(dto)) {
                throw new NotEnoughBalanceException("Insufficient funds");
            }

            WalletEventEntity eventEntity = WalletEventMapper.toEntity(dto);
            eventRepository.save(eventEntity);
        } finally {
            distributedLockService.releaseLock("wallet", dto.getWalletId(), lockToken.get());
        }

        return dto;
    }

    public boolean hasBalance(PostTransactionDTO dto) {
        BigDecimal withdrawValue = dto.getValue().abs();
        BigDecimal balance = getBalance(dto);
        return balance.compareTo(withdrawValue) >= 0;
    }

    public BigDecimal getBalance(PostTransactionDTO dto) {
        WalletSnapshotEntity latestSnapshot = snapshotRepository
                .findLatestByKeyWalletId(dto.getWalletId())
                .orElseGet(() -> WalletSnapshotEntity.empty(dto.getWalletId()));

        List<WalletEventEntity> events = eventRepository.findAllByKeyWalletIdAndKeyMomentAfter(
                latestSnapshot.getKey().getWalletId(),
                latestSnapshot.getKey().getMoment()
        );

        return events.stream()
                .map(WalletEventEntity::getValue)
                .reduce(latestSnapshot.getValue(), BigDecimal::add);
    }
}
