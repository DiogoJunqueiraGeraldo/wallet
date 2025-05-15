package com.wallet.wallets_command_service.repositories;

import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.entities.keys.WalletEventKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Repository
public interface WalletEventRepository extends CassandraRepository<WalletEventEntity, WalletEventKey> {
    List<WalletEventEntity> findAllByKeyWalletIdAndKeyMomentAfter(UUID walletId, Instant moment);
}
