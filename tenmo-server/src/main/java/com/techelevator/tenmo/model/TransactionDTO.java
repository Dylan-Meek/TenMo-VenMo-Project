package com.techelevator.tenmo.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class TransactionDTO {
    private Long toUserId;
    private BigDecimal amount;


    public TransactionDTO(Long toUserId, BigDecimal amount) {
        this.toUserId = toUserId;
        this.amount = amount;
    }

   public TransactionDTO(){};

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
