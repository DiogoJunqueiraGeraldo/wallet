package com.wallet.wallets_command_service.entities;

import com.wallet.wallets_command_service.entities.keys.WalletEventKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(keyspace = "wallets_ks", value = "wallet_events")
public class WalletEventEntity {
    @PrimaryKey
    private WalletEventKey key;

    @Column("event_type")
    private String eventType;

    @Column("value")
    private BigDecimal value;
}
