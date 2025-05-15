package com.wallet.wallets_command_service.controllers;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.services.WalletPostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletPostingService walletPostingService;

    public WalletController(WalletPostingService walletPostingService) {
        this.walletPostingService = walletPostingService;
    }

    @PostMapping
    public ResponseEntity<TransactionPostedResponse> post(
            @RequestBody @Valid PostTransactionRequest request
    ) {
        PostTransactionDTO requestDto = PostTransactionDTO.of(request);
        PostTransactionDTO resultDto = walletPostingService.process(requestDto);
        TransactionPostedResponse response = TransactionPostedResponse.of(resultDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
