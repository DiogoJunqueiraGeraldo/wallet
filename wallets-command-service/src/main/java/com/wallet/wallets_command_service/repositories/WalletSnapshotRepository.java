package com.wallet.wallets_command_service.repositories;

import com.wallet.wallets_command_service.entities.WalletSnapshotEntity;
import com.wallet.wallets_command_service.entities.keys.WalletSnapshotKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletSnapshotRepository extends CassandraRepository<WalletSnapshotEntity, WalletSnapshotKey> {
    Optional<WalletSnapshotEntity> findLatestByKeyWalletId(UUID walletId);
}
