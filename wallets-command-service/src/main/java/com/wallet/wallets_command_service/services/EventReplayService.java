package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.entities.WalletSnapshotEntity;
import com.wallet.wallets_command_service.repositories.EventRepository;
import com.wallet.wallets_command_service.repositories.WalletSnapshotRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class EventReplayService {
    private final EventRepository eventRepository;
    private final WalletSnapshotRepository snapshotRepository;

    public EventReplayService(EventRepository eventRepository, WalletSnapshotRepository snapshotRepository) {
        this.eventRepository = eventRepository;
        this.snapshotRepository = snapshotRepository;
    }

    public BigDecimal getBalance(UUID walletId) {
        WalletSnapshotEntity latestSnapshot = snapshotRepository
                .findLatestByKeyWalletId(walletId)
                .orElseGet(WalletSnapshotEntity::empty);

        List<WalletEventEntity> events = eventRepository.findAllByKeyWalletIdAndKeyMomentAfter(
                walletId,
                latestSnapshot.getKey().getMoment()
        );

        return events.stream()
                .map(WalletEventEntity::getValue)
                .reduce(latestSnapshot.getValue(), BigDecimal::add);
    }
}
