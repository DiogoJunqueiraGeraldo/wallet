package com.wallet.wallets_command_service.controllers;

import com.wallet.wallets_command_service.dtos.PostTransactionDTO;
import com.wallet.wallets_command_service.dtos.PostTransactionRequest;
import com.wallet.wallets_command_service.dtos.TransactionPostedResponse;
import com.wallet.wallets_command_service.services.PostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final PostingService walletPostingService;

    public WalletController(PostingService walletPostingService) {
        this.walletPostingService = walletPostingService;
    }

    @PostMapping
    public ResponseEntity<TransactionPostedResponse> post(
            @RequestBody @Valid PostTransactionRequest request
    ) {
        PostTransactionDTO requestDto = new PostTransactionDTO(request.walletId(), request.value());
        PostTransactionDTO resultDto = walletPostingService.process(requestDto);
        TransactionPostedResponse response = TransactionPostedResponse.of(resultDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
