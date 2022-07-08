package com.techelevator.tenmo.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class TransactionDTO {
    private String receiverUserName;
    private BigDecimal amount;


    public TransactionDTO(String receiverUserName, BigDecimal amount) {
        this.receiverUserName = receiverUserName;
        this.amount = amount;
    }

   public TransactionDTO(){};

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setUserId(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
