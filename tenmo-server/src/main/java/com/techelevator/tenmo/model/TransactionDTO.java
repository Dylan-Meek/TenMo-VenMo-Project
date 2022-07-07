package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {
    private int toUserId;
    private BigDecimal amount;

    public TransactionDTO(int toUserId, BigDecimal amount) {
        this.toUserId = toUserId;
        this.amount = amount;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
