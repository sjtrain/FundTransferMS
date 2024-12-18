package com.tekarch.FundtransferMS.Controller;


import com.tekarch.FundtransferMS.DTO.FundTransferDTO;
import com.tekarch.FundtransferMS.Models.FundTransfer;
import com.tekarch.FundtransferMS.Services.FundTransferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import java.util.List;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/fund-transfer")
public class FundTransferController {

    @Autowired
    private final FundTransferService fundTransferService;
    private static final Logger logger = LogManager.getLogger(FundTransferController.class);

    // 1. Initiate a Fund Transfer
    @PostMapping("/fund-transfer")
    public ResponseEntity<FundTransfer> initiateFundTransfer(@Valid @RequestBody FundTransferDTO fundTransferDTO) {
        // Create a new FundTransfer entity
        FundTransfer fundTransfer = new FundTransfer();

        // Set the fields from the DTO to the entity
        fundTransfer.setSenderAccountId(fundTransferDTO.getSenderAccountId());
        fundTransfer.setReceiverAccountId(fundTransferDTO.getReceiverAccountId());
        fundTransfer.setAmount(fundTransferDTO.getAmount());
        fundTransfer.setStatus("pending"); // Default status

        // Pass the entity object to the service layer for processing
        FundTransfer createdFundTransfer = fundTransferService.initiateTransfer(fundTransfer);

        // Return the created FundTransfer object with HTTP 201 (Created)
        return new ResponseEntity<>(createdFundTransfer, HttpStatus.CREATED);
    }


    // 2. Complete a Fund Transfer (PUT /fund-transfer/{transfer-id})
    @PutMapping("/{transferId}")
    public ResponseEntity<String> completeFundTransfer(@PathVariable("transferId") Long transferId) {
        boolean isTransferCompleted = fundTransferService.completeTransfer(transferId);

        if (isTransferCompleted) {
            return new ResponseEntity<>("Fund transfer completed successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to complete fund transfer. Transfer ID not found or already completed.",
                    HttpStatus.BAD_REQUEST);
        }
    }
}

