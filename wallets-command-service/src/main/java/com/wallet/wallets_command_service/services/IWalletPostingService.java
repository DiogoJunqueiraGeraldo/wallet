package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;

public interface IWalletPostingService {
    PostTransactionDTO post(PostTransactionDTO dto);
}
