package com.tekarch.FundtransferMS.Services;

import com.tekarch.FundtransferMS.Models.FundTransfer;



public interface FundTransferService {

    /**
     * Initiates a fund transfer between two accounts.
     *
     * @param senderAccountId   The ID of the sender's account.
     * @param receiverAccountId The ID of the receiver's account.
     * @param amount            The amount to transfer.
     * @return The created FundTransfer entity with status "pending".
     */
    FundTransfer initiateTransfer(FundTransfer fundTransfer);

    /**
     * Completes a pending fund transfer by processing the transaction.
     *
     * @param transferId The ID of the fund transfer to complete.
     * @return true if the transfer was successfully completed, false otherwise.
     */
    boolean completeTransfer(Long transferId);
}
