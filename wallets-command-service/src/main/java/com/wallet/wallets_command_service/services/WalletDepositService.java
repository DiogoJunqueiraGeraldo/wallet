package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.entities.WalletEventEntity;
import com.wallet.wallets_command_service.mappers.WalletEventMapper;
import com.wallet.wallets_command_service.repositories.WalletEventRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletDepositService implements IWalletPostingService {
    private final WalletEventRepository eventRepository;

    public WalletDepositService(WalletEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public PostTransactionDTO post(PostTransactionDTO dto) {
        WalletEventEntity eventEntity = WalletEventMapper.toEntity(dto);
        eventRepository.save(eventEntity);
        return dto;
    }
}
