package com.tekarch.FundtransferMS.DTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FundTransferDTO {

    private Long transferId;

    @NotNull(message = "Sender Account ID is mandatory")
    @Positive(message = "Sender Account ID must be positive")
    private Long senderAccountId;

    @NotNull(message = "Receiver Account ID is mandatory")
    @Positive(message = "Receiver Account ID must be positive")
    private Long receiverAccountId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
    @DecimalMax(value = "1000000.00", inclusive = true, message = "Amount must not exceed 1,000,000.00")
    @Digits(integer = 13, fraction = 2, message = "Amount must have up to 15 digits with 2 decimal places")
    private BigDecimal amount;

    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status = "pending";

    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;

        /*// Default Constructor
        public FundTransferDTO() {
        }

        // Parameterized Constructor
        public FundTransferDTO(Long transferId, Long senderAccountId, Long receiverAccountId, BigDecimal amount,
                               String status, LocalDateTime initiatedAt, LocalDateTime completedAt) {
            this.transferId = transferId;
            this.senderAccountId = senderAccountId;
            this.receiverAccountId = receiverAccountId;
            this.amount = amount;
            this.status = status;
            this.initiatedAt = initiatedAt;
            this.completedAt = completedAt;
        }*/

    // Getters and Setters
    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(LocalDateTime initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

       /* @Override
        public String toString() {
            return "FundTransferDTO{" +
                    "transferId=" + transferId +
                    ", senderAccountId=" + senderAccountId +
                    ", receiverAccountId=" + receiverAccountId +
                    ", amount=" + amount +
                    ", status='" + status + '\'' +
                    ", initiatedAt=" + initiatedAt +
                    ", completedAt=" + completedAt +
                    '}';
        }*/
}



