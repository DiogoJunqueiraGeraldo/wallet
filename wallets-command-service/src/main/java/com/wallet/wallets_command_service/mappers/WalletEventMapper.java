package com.wallet.wallets_command_service.mappers;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.entities.keys.WalletEventKey;

public class WalletEventMapper {
    public static WalletEventEntity toEntity(PostTransactionDTO dto) {
        return new WalletEventEntity(
                new WalletEventKey(dto.getWalletId(), dto.getMoment()),
                dto.getEventType().toString(),
                dto.getValue()
        );
    }
}
