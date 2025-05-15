package com.wallet.wallets_command_service.entities;

import com.wallet.wallets_command_service.entities.keys.WalletSnapshotKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(keyspace = "wallets_ks", value = "wallet_snapshot")
public class WalletSnapshotEntity {
    @PrimaryKey
    private WalletSnapshotKey key;

    @Column("value")
    private BigDecimal value;

    public static WalletSnapshotEntity empty(UUID walletId) {
        return new WalletSnapshotEntity(
                new WalletSnapshotKey(walletId, Instant.EPOCH),
                BigDecimal.ZERO
        );
    }
}
