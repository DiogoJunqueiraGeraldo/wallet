package com.wallet.wallets_command_service.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class WalletSnapshotKey {
    @PrimaryKeyColumn(name = "wallet_id", ordinal = 0, type = PARTITIONED)
    private UUID walletId;

    @PrimaryKeyColumn(name = "moment", ordinal = 1, type = CLUSTERED, ordering = Ordering.ASCENDING)
    private Instant moment;
}
