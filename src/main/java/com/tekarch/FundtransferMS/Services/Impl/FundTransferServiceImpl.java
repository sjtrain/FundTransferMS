package com.tekarch.FundtransferMS.Services.Impl;

import com.tekarch.FundtransferMS.DTO.FundTransferDTO;
import com.tekarch.FundtransferMS.Models.Account;
import com.tekarch.FundtransferMS.Models.FundTransfer;
import com.tekarch.FundtransferMS.Repositories.AccountRepository;
import com.tekarch.FundtransferMS.Repositories.FundTransferRepository;
import com.tekarch.FundtransferMS.Services.FundTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

import java.util.Collections;
import java.util.Optional;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    @Autowired
    private final FundTransferRepository fundTransferRepository;

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE_URL = "http://localhost:8081/accounts/{accountId}";

    // Constructor-based injection
    public FundTransferServiceImpl(FundTransferRepository fundTransferRepository, AccountRepository accountRepository) {
        this.fundTransferRepository = fundTransferRepository;
        this.accountRepository = accountRepository;
    }

    // 1. Initiate a Fund Transfer
    @Override
    public FundTransfer initiateTransfer(FundTransfer fundTransfer) {

        Long senderId = fundTransfer.getSenderAccountId();
        Long receiverId = fundTransfer.getReceiverAccountId();

        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Sender or Receiver account does not exist.");
        }
        return fundTransferRepository.save(fundTransfer);
    }


    // 2. Complete a Fund Transfer
    @Override
    public boolean completeTransfer(Long transferId) {



        // Check if sender has sufficient balance
       // if (senderId.getBalance().compareTo(fundTransfer.getAmount()) < 0) {
           // throw new IllegalArgumentException("Insufficient balance in sender's account.");
       // }

        // Lookup the fund transfer
        FundTransfer transfer = fundTransferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer ID not found."));

        if ("completed".equalsIgnoreCase(transfer.getStatus())) {
            throw new IllegalArgumentException("Transfer is already completed.");
        }

        // Fetch sender and receiver accounts
        Account sender = getAccountDetails(transfer.getSenderAccountId());
        Account receiver = getAccountDetails(transfer.getReceiverAccountId());

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid sender or receiver account.");
        }

        // Validate sender's balance again
        if (sender.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in sender's account.");
        }

        // Deduct amount from sender and add to receiver
        sender.setBalance(sender.getBalance().subtract(transfer.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transfer.getAmount()));

        // Update accounts in the database
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Update transfer status
        transfer.setStatus("completed");
        fundTransferRepository.save(transfer);
        return true;
    }

    // Common method to fetch account details using REST API
    private Account getAccountDetails(Long accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Account> response = restTemplate.exchange(
                ACCOUNT_SERVICE_URL,
                HttpMethod.GET,
                requestEntity,
                Account.class,
                accountId
        );
        return response.getBody();
    }


}
